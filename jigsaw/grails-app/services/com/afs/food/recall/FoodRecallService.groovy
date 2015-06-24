package com.afs.food.recall

import grails.transaction.Transactional

import java.text.DateFormat;
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.codehaus.groovy.grails.web.json.JSONObject

import com.afs.jigsaw.fda.food.api.*

class FoodRecallService {

    /**
     * The maximum amount of results to return from the FDA Food service.<br /><br />
     *
     * Note: The API will not support returning more than 100, do not set above 100
     */
    private static final def MAX_RESULTS = 100
    private static final def BASE_URL = "https://api.fda.gov/food/enforcement.json?limit=${MAX_RESULTS}"
	protected static final def BASE_COUNT_URL = "https://api.fda.gov/food/enforcement.json?"
	
	private static final def SEARCH_PREFIX = "search=(";
	private static final def COUNT_BY_SEVERITY = "&count=classification.exact";
	private static final def DATE_SEARCH_PREFIX = "+AND+report_date:[";
	private static final def LIMIT_PREFIX = "&limit=";
	private static final def SKIP_PREFIX="&skip=";
	
    def stateNormalizationService
	LocalDate lastNotified = LocalDate.now().minusMonths(2)

	
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
	 * Determines if there has been an update since the last notification
	 * 
	 * @return true if we need to send new notifications
	 */
	def needUpdate() {
		def json = new JSONObject(new URL("https://api.fda.gov/food/enforcement.json").getText())
		LocalDate lastUpdate = LocalDate.parse(json.meta.last_updated, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
		return (lastUpdate > lastNotified)
	}
	
	/**
	 * Send new notifications since the last notification date (in recall_initiation_date)
	 * @return
	 */
	def sendNotifications(){
		println("Last notified: ${lastNotified}")
		if(needUpdate()){
			println("Sending notifications")
			def json = new JSONObject(new URL("https://api.fda.gov/food/enforcement.json?search=recall_initiation_date:[${lastNotified}+TO+${LocalDate.now()}]&limit=100").getText())
			final Set<String> distributionStates = []
			json.results.each { result ->
				println("${result.recall_number} - ${result.recall_initiation_date}")
			}
			lastNotified = LocalDate.parse(json.meta.last_updated, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
		}
	}
	
	/**
	 * May be OBE - reads RSS feed
	 * @return
	 */
	def readRss(){
		def url = "http://www2c.cdc.gov/podcasts/createrss.asp?c=146"
		def rss = new XmlSlurper().parse(url)
		println rss.channel.title
		rss.channel.item.each { item->
			println "- ${item.title}" 
		}​
	}

	/**
	 * Returns the a detailed set of recalls for a specific state allowing pagination.  The data is passed back in a JSON
	 * {@link JSONObject}.<br /><br />
	 *
	 * There is no query feature in the FDA API to support state by state querying of the data set. It is possible to acheive this
	 * same behavior through specially structured search criteria which will use AND/OR syntax to include the state code or state
	 * name.  In cases where the state name is an exact substring of another state (i.e. Virginia / West Virginia), the AND
	 * syntax will be used to use Virginia AND NOT West Virginia. <br /><br />
	 *
	 *This response will have the same stru
	 * @return
	 */
	def getPageByState(State state, Integer limit, Integer skip, Date from, Date to) {
		def options = buildDateRange(from, to)
		options += buildOptions(limit, skip)
		try {
			def json = new JSONObject(new URL(buildCountUrl(state, options)).getText())
			json.results.each { result ->
				// try to find the states in the natural language value and add it to the result
				result.normalized_distribution_pattern = stateNormalizationService.getStates(result.distribution_pattern)*.getAbbreviation()
			}
			
			/*
			 * we also want to remove the meta from the JSON response, but we need the pagination informaiton, so we'll
			 * move that up int he JSON and then remove the meta
			 */
			json.numResults = json.meta.results.total;
			json.skip = json.meta.results.skip;
			json.limit = json.meta.results.limit;
			
			json.remove("meta");
			
			return json;
		} catch(Exception e) {
			//the response was not a valid set of responses.
			def json = new JSONObject()
			json.numResults = 0;
			json.skip = 0;
			json.limit = limit;
			json.error = e.getMessage()
			json.results = []
			return json
		}

	}
	
	/**
	 * builds the search string for date range querying. If either are null then there is no date range query
	 *
	 * @param skip The record to start at in the result set. Skip 50 with a limit of 25 means start at page 3 effectively.
	 * @param limit - The number of records to return in the "page"
	 * @return
	 */
	def buildDateRange(Date from, Date to) {
		//these functions are separated out to facilitate unit testng code coverage
		StringBuffer options = new StringBuffer("");
		if (from != null && to != null) {
			options.append(DATE_SEARCH_PREFIX).append(from.format("yyyyMMdd")).append("+TO+").append(to.format("yyyyMMdd")).append("]")
		}
		return options.toString();
	}
	
	/**
	 * builds the inputs for the pagination of the results handling nulls in a 
	 * meaningful way
	 * 
	 * @param skip The record to start at in the result set. Skip 50 with a limit of 25 means start at page 3 effectively.
	 * @param limit - The number of records to return in the "page"
	 * @return
	 */
	def buildOptions (Integer limit, Integer skip) {
		//these functions are separated out to facilitate unit testng code coverage
		StringBuffer options = new StringBuffer("");
		if (limit != null) {
			options.append(LIMIT_PREFIX).append(Math.min(limit, MAX_RESULTS));
			if (skip != null) {
				//the skip is only really useful for pagination if you have a specific limit to the number of results (a page size)
				options.append(SKIP_PREFIX) .append(skip);
			}
			options.append("");
		}
		return options.toString();		
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
	def getCountsByState(State state, Date from, Date to) {
		//these functions are separated out to facilitate unit testng code coverage
		def options = buildDateRange(from, to) + COUNT_BY_SEVERITY

		try {		
			def json = new JSONObject(new URL(buildCountUrl(state,options )).getText())
			return transformCountJson(state, json);
		} catch(Exception e) {
			//the response was not a valid set of responses.
			def json = new JSONObject()
			json.numResults = 0;
			json.error = e.getMessage()
			return json
		}

	}	
	
	
	/**
	 * Uses utilities available to build the url for fetching a singular states' count results
	 * @param state
	 * @return
	 */
	def buildCountUrl (State state, String options) {
		String searchCriteria = new StateSearchCriteriaUtils().generateCriteria(state);
		StringBuffer url = new StringBuffer();
		url.append(BASE_COUNT_URL).append(SEARCH_PREFIX).append(searchCriteria).append(")").append(options);
		
		return url.toString();
	}
	
	/**
	 * Transforms the JSON return from the FDA API to the spec
	 * @param json
	 * @return
	 */
	def transformCountJson(State state, JSONObject json) {
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
