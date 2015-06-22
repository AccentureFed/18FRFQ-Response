package com.afs.food.recall

import grails.transaction.Transactional

import org.codehaus.groovy.grails.web.json.JSONObject

import com.afs.jigsaw.fda.food.api.StateSearchCriteriaUtils


class FoodRecallService {

    /**
     * The maximum amount of results to return from the FDA Food service.<br /><br />
     *
     * Note: The API will not support returning more than 100, do not set above 100
     */
    private static final def MAX_RESULTS = 100
    private static final def BASE_URL = "https://api.fda.gov/food/enforcement.json?limit=${MAX_RESULTS}"
	protected static final def BASE_COUNT_URL = "https://api.fda.gov/food/enforcement.json?"
	
    def stateNormalizationService

	
	/*
	 *  this creates are "nlp phrases" list
	 *  states with multi words will be treated as a single phrase
	 */
	private static final Map CLASSIFICATION_TO_SEVERITY= new HashMap<String,String>();
	static {
	CLASSIFICATION_TO_SEVERITY.put("Class I", "high");
	CLASSIFICATION_TO_SEVERITY.put("Class II", "medium");
	CLASSIFICATION_TO_SEVERITY.put("Class III", "low");
	}
	
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


	/**
	 * Returns the aggregated counts by classification for a specific state.  The data is passed back in a JSON
	 * {@link JSONObject}.<br /><br />
	 * 
	 * There is no query feature in the FDA API to support state by state querying of the data set. It is possible to acheive this 
	 * same behavior through specially structured search criteria which will use AND/OR syntax to include the state code or state
	 * name.  In cases where the state name is an exact substring of another state (i.e. Virginia / West Virginia), the AND
	 * syntax will be used to use Virginia AND NOT West Virginia. <br /><br /> 
	 * 
	 * The resulting JSON will have the "classification"  of Class I, Class II, Class III
	 *  removed in place of "severity" of high medium and low instead. <br /><br />
	 * 
	 *   
	 * @return
	 */
	def getCountsByState(com.afs.jigsaw.fda.food.api.State state) {
		//these functions are separated out to facilitate unit testng code coverage
		def json = new JSONObject(new URL(buildCountUrl(state)).getText())
		return transformCountJson(state, json);
		

	}	
	
	/**
	 * Uses utilities available
	 * @param state
	 * @return
	 */
	def buildCountUrl (com.afs.jigsaw.fda.food.api.State state) {
		String searchCriteria = new StateSearchCriteriaUtils().generateCriteria(state);
		StringBuffer url = new StringBuffer();
		url.append(BASE_COUNT_URL).append("search=").append(searchCriteria).append("&count=classification.exact");
		
		return url.toString();
	}
	
	/**
	 * Transforms the JSON return from the FDA API to the spec
	 * @param json
	 * @return
	 */
	def transformCountJson(com.afs.jigsaw.fda.food.api.State state, JSONObject json) {
		def translatedJson = new JSONObject();
		translatedJson.stateCode = state.getAbbreviation()
		
		json.results.each { result ->
			// try to find the states in the natural language value and add it to the result
			def entry= new JSONObject();
			if (CLASSIFICATION_TO_SEVERITY.containsKey(result.term)) {
				/**
				 * error handling for a changing set of values in the underlying API...
				 */
				entry.severity = CLASSIFICATION_TO_SEVERITY.getAt(result.term);
				entry.count = result.count
				translatedJson.accumulate("results", entry);
			}
		}
		return translatedJson
	}
}
