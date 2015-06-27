package com.afs.food.recall

import grails.converters.JSON

import java.text.SimpleDateFormat

import com.afs.jigsaw.fda.food.api.*

class FoodRecallController {

    def foodRecallService

    /**
     * TODO: REMOVE -- FOR TESTING PURPOSES ONLY
     */
    def countRecallsNoState() {
        render foodRecallService.getCountOfRecallsWithNoStates()
    }

    /**
     * TODO: REMOVE -- FOR TESTING PURPOSES ONLY
     */
    def distributionRecallsNoState() {
        render foodRecallService.getDistributionPatternOfRecallsWithNoStates()
    }

    /**
     * Used to return a count of recalls for each severity for the given params
     *
     *<p>
     * params include:<br />
     * <strong>state</strong> - Optional. String. If not present (or and invalid state), then no state specific searching will be performed. Can be any name or abbreviation
     * of a state<br />
     * <strong>startDate</strong> - Optional. DateString. Format of yyyyMMdd<br />
     * <strong>endDate</strong> - Optional. DateString. Format of yyyyMMdd<br />
     *</p>
     *
     * @return - The count of each severity for the desired state/date range, ex:
     * {"stateCode":"null","results":[{"severity":"high","count":3757},{"severity":"low","count":269},{"severity":"medium","count":3779}]}
     */
    def count() {
        def state = params.stateCode ? State.fromString(params.stateCode) : null
        def startDate = params.startDate ? new SimpleDateFormat(FoodRecallService.DATE_FORMAT).parse(params.startDate) : null
        def endDate = params.endDate ? new SimpleDateFormat(FoodRecallService.DATE_FORMAT).parse(params.endDate) : null

        def severityCounts = foodRecallService.getCountsByState(state, startDate, endDate)
        def resultsList = severityCounts.collect { it ->
            [severity: it[0], count: it[1]]
        }
        render ([stateCode: params.stateCode, results: resultsList] as JSON)
    }

    /**
     * Used to render a list of recalls with pagination and enriched values.  This response format includes the
     * FDA format with an additional normalized_distribution_pattern field as a json array of formal state abbreviations.
     *<p>
     * params include:<br />
     * <strong>state</strong> - Optional. String. If not present (or and invalid state), then no state specific searching will be performed. Can be any name or abbreviation of a state<br />
     * <strong>limit</strong> - Optional.  Numeric.  The number of results to return. 10 is default<br />
     * <strong>skip</strong> - Optional.  Numeric.  Allows pagination.  default is 0 (start at beginning of list)<br />
     * <strong>startDate</strong> - Optional. DateString. Format of yyyyMMdd<br />
     * <strong>endDate</strong> - Optional. DateString. Format of yyyyMMdd<br />
     * </p>
     *
     * Only if both dates are provided will a date specific search be used.  If either is not provided, then no date constraint will be added to the search.
     *
     * @return a list of recalls for the given parameters
     */
    def recalls() {
        def limit = params.limit ? params.int('limit') : 10
        def skip = params.skip ? params.int('skip')  : 0
        def state = params.stateCode ? State.fromString(params.stateCode) : null
        def upc = params.upc ? UpcBarcode.buildBarcode(params.upc) : null
        def startDate = params.startDate ? new SimpleDateFormat(FoodRecallService.DATE_FORMAT).parse(params.startDate) : null
        def endDate = params.endDate ? new SimpleDateFormat(FoodRecallService.DATE_FORMAT).parse(params.endDate) : null

        render foodRecallService.getPageByState(state, limit,  skip, startDate, endDate, upc)
    }
}
