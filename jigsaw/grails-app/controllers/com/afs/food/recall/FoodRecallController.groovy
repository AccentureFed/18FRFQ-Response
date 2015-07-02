package com.afs.food.recall

import grails.converters.JSON

import java.text.SimpleDateFormat

import org.codehaus.groovy.grails.web.json.JSONObject

import com.afs.jigsaw.fda.food.api.*

class FoodRecallController {

    def foodRecallService

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
        def startDate = params.startDate ? new SimpleDateFormat(FoodRecallService.INPUT_DATE_FORMAT).parse(params.startDate) : null
        def endDate = params.endDate ? new SimpleDateFormat(FoodRecallService.INPUT_DATE_FORMAT).parse(params.endDate) : null

        def severityCounts = foodRecallService.getCountsByState(state, startDate, endDate)
        def resultsList = severityCounts.collect { it ->
            [severity: it[0].toString().toLowerCase(), count: it[1]]
        }
        render ([stateCode: params.stateCode, results: resultsList] as JSON)
    }

    /**
     * Used to render a list of recalls with pagination and enriched values.  This response format includes the
     * FDA format with an additional normalized_distribution_pattern field as a json array of formal state abbreviations and normalized_barcodes field
     * as a json array or upc barcodes.
     *<p>
     * params include:<br />
     * <strong>state</strong> - Optional. String. If not present (or and invalid state), then no state specific searching will be performed. Can
     * be any name or abbreviation of a state<br />
     * <strong>searchText</strong> - Optional. Any free-form text to me matched with a UPC (exact match), Product Description (substring match),
     * Recalling Firm (substring match), or Reason for Recall (substring match). If not given, ignored.<br />
     * <strong>startDate</strong> - Optional. DateString. Format of yyyyMMdd<br />
     * <strong>endDate</strong> - Optional. DateString. Format of yyyyMMdd<br />
     * <strong>limit</strong> - Optional.  Numeric.  The number of results to return. 10 is default<br />
     * <strong>skip</strong> - Optional.  Numeric.  The page number to get. The first recall record will be from the offset of limit*skip.
     * Default is 0 (The page numbers are 0-based)<br />
     * </p>
     *
     * Only if both dates are provided will a date specific search be used.  If either is not provided, then no date constraint will be added to the search.
     *
     * @return a JSON object with a limit, skip, numResults (the total for your query), and results.  The results is an array of all the recalls requested.
     */
    def recalls() {
        def limit = params.limit ? params.int('limit') : 10
        limit = limit > 0 ? limit : 10 // ensure it is valid

        def skip = params.skip ? params.int('skip') : 0
        skip = skip >= 0 ? skip*limit : 0 //ensure it is valid

        def state = params.stateCode ? State.fromString(params.stateCode) : null
        def startDate = params.startDate ? new SimpleDateFormat(FoodRecallService.INPUT_DATE_FORMAT).parse(params.startDate) : null
        def endDate = params.endDate ? new SimpleDateFormat(FoodRecallService.INPUT_DATE_FORMAT).parse(params.endDate) : null

        def recalls = foodRecallService.getRecalls(state, params.searchText, startDate, endDate, limit, skip)
        def resultsJSON = recalls*.enrichedJSONPayload.collect { new JSONObject(it) }
        render ([limit: limit, skip: skip/limit, numResults: recalls.getTotalCount(), results: resultsJSON] as JSON)
    }
}
