import grails.util.Environment

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.concurrent.TimeUnit

import com.afs.food.recall.FoodRecall
import com.afs.food.recall.FoodRecallService
import com.afs.food.recall.RecallState
import com.afs.jigsaw.fda.food.api.State

class BootStrap {

    def foodRecallService
    def sessionFactory

    def init = { servletContext ->

        if (Environment.current != Environment.TEST) {
            // load all recalls, enrich the data and cache them locally
            def dateFormatter = new SimpleDateFormat(FoodRecallService.DATE_FORMAT)

            def start = System.nanoTime()

            /**
             * We can only pull 5,000 items at a time, so pull by year to get all recalls
             */
            def currYear = LocalDate.now().year
            for(int year = FoodRecallService.START_YEAR; year <= currYear; year++) {
                // get first batch
                def skip = 0
                def json

                def total = foodRecallService.fetchRecallsFromApi(year, skip).meta.results.total.toInteger()

                // cache the enriched data to the database
                while(skip < total) {
                    // get next batch
                    json = foodRecallService.fetchRecallsFromApi(year, skip)

                    json.results.each { result ->
                        def foodRecall = new FoodRecall()
                        foodRecall.originalPayload = result.toString()
                        foodRecall.reportDate = dateFormatter.parse(result.report_date)
                        foodRecall.recallNumber = result.recall_number
                        foodRecall.severity = FoodRecallService.CLASSIFICATION_TO_SEVERITY[result.classification]

                        result.normalized_distribution_pattern.each { stateAbbreviation ->
                            def state = State.fromString(stateAbbreviation)
                            def recallState = RecallState.findByState(state) ?: new RecallState()
                            recallState.state = state

                            foodRecall.addToDistributionStates(recallState)
                        }

                        if(!foodRecall.save()) {
                            log.warn("Error saving food recall, most likely a duplicate recall number of ${result.recall_number}")
                        }
                    }

                    skip += json.results.size()

                    // flush after every 100
                    sessionFactory.getCurrentSession().flush()
                }
            }

            def end = System.nanoTime()
            log.debug("Took ${TimeUnit.NANOSECONDS.toSeconds(end-start)} seconds to cache ${FoodRecall.count()} recalls")
        }

    }

    def destroy = {
    }
}
