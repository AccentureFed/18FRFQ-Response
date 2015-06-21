package com.afs.food.recall

import grails.transaction.Transactional

import org.codehaus.groovy.grails.web.json.JSONObject

@Transactional
class FoodRecallService {

    /**
     * The maximum amount of results to return from the FDA Food service.<br /><br />
     *
     * Note: The API will not support returning more than 100, do not set above 100
     */
    private static final def MAX_RESULTS = 100
    private static final def BASE_URL = "https://api.fda.gov/food/enforcement.json?limit=${MAX_RESULTS}"

    def stateNormalizationService

    /**
     * Returns the first {@link #MAX_RESULTS} recalls from the FDA Service API.  The data is passed back as a {@link JSONObject}.<br /><br />
     *
     * Each return will have an added JSON Array field called 'normalized_distribution_pattern' that will contain all of the state codes each recall was distributed in.
     * @return A {@link JSONObject} representing the recalls.
     */
    def getRecalls() {
        def json = new JSONObject(new URL("${BASE_URL}").getText())

        final Set<String> distributionStates = []
        json.results.each { result ->
            // try to find the states in the natural language value and add it to the result
            result.normalized_distribution_pattern = stateNormalizationService.getStates(result.distribution_pattern)*.getAbbreviation()
        }

        return json
    }
}
