import grails.util.Environment
import java.lang.management.ManagementFactory
import javax.servlet.*
import jigsaw.CacheUpdateJob


import org.grails.plugins.metrics.groovy.HealthChecks
import org.grails.plugins.metrics.groovy.Metrics

import com.codahale.metrics.jvm.BufferPoolMetricSet
import com.codahale.metrics.jvm.GarbageCollectorMetricSet
import com.codahale.metrics.jvm.MemoryUsageGaugeSet
import com.codahale.metrics.jvm.ThreadStatesGaugeSet
import com.codahale.metrics.jvm.FileDescriptorRatioGauge
import com.codahale.metrics.servlet.InstrumentedFilter

import com.afs.food.recall.FoodRecall
import com.afs.food.recall.FoodRecallService
import com.afs.food.recall.RecallState
import com.afs.jigsaw.fda.food.api.State

class BootStrap {

    def init = { servletContext ->

        if (Environment.current != Environment.TEST) {
            // load all recalls, enrich the data and cache them locally
            def max = 100
            def dateFormatter = new SimpleDateFormat(FoodRecallService.DATE_FORMAT)

            def start = System.nanoTime()
            
            /**
            *Adding Metrics
            */
	    	log.debug("Registering Http Instrumented Filter")
	    	servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE, Metrics.getRegistry())
	    	FilterRegistration.Dynamic metricsFilter = servletContext.addFilter('webappMetricsFilter', new InstrumentedFilter())
	    	metricsFilter.addMappingForUrlPatterns(null, true, '/*')
	    	metricsFilter.setAsyncSupported(true)
	        log.debug("Registering JVM gauges")
			Metrics.getRegistry().register("jvm.buffers", new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()))
			Metrics.getRegistry().register("jvm.gc", new GarbageCollectorMetricSet())
			Metrics.getRegistry().register("jvm.memory", new MemoryUsageGaugeSet())
			Metrics.getRegistry().register("jvm.threads", new ThreadStatesGaugeSet())
			Metrics.getRegistry().register("jvm.files", new FileDescriptorRatioGauge())
			log.info("Setting up Database healthcheck")
		    HealthChecks.register("database",new DatabaseHealthCheck())
		    log.info("Setting up Storage healthcheck")
		    HealthChecks.register("filestorage",new StorageHealthCheck())

            /**
             * We can only pull 5,000 items at a time, so pull by year to get all recalls
             */
            def currYear = LocalDate.now().year
            for(int year = FoodRecallService.START_YEAR; year <= currYear; year++) {
                // get first batch
                def skip = 0
                def json

                def total = foodRecallService.fetchRecallsFromApi(year, max, skip).meta.results.total.toInteger()

                // cache the enriched data to the database
                while(skip < total) {
                    // get next batch
                    json = foodRecallService.fetchRecallsFromApi(year, max, skip)

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

        CacheUpdateJob.triggerNow()
    }

    def destroy = {
    }
}
