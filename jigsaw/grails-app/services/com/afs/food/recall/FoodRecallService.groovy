package com.afs.food.recall

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

import com.afs.jigsaw.fda.food.api.*
import com.google.common.base.Preconditions

class FoodRecallService {

    /**
     * There is no data in the API before 2012
     */
    public static def START_YEAR = 2012

    /**
     * This is the format for the API
     */
    public static def DATE_FORMAT = 'yyyyMMdd'

    /**
     * Maps a Classification to a severity level (low, medium, high)
     */
    public static final Map<String, String> CLASSIFICATION_TO_SEVERITY = ['Class I': 'high', 'Class II': 'medium', 'Class III': 'low']

    /**
     * The maximum amount of results to return from the FDA Food service.<br /><br />
     *
     * Note: The API will not support returning more than 100, do not set above 100
     */
    private static final def MAX_RESULTS = 100

    private static final def BASE_URL = "https://api.fda.gov/food/enforcement.json"

    def stateNormalizationService
    private def lastNotified = LocalDate.now().minusMonths(2)

    /**
     * Fetches recalls from the API for the given year and offset. Results will be given at {@link #MAX_RESULTS} at a time.
     * @param year The year to get recalls for. Must be >= {@link #START_YEAR}
     * @param offset The offset to start at. Must be >=0.
     * @return A {@link JSONObject} representing the recalls. Each recall result will have an added JSON Array field called
     * 'normalized_distribution_pattern' that will contain all of the state codes each recall was distributed in.
     */
    def fetchRecallsFromApi(final int year, final int offset) {
        Preconditions.checkArgument(year >= START_YEAR, 'Year must be >= the START_YEAR (2012)')
        Preconditions.checkArgument(offset >=0, 'The offset must be >= 0')

        def json = new JSONObject(new URL("${BASE_URL}?limit=${MAX_RESULTS}&skip=${offset}&search=report_date:[${year}0101+TO+${year}1231]").getText())

        final Set<String> distributionStates = []
        json.results.each { result ->
            // try to find the states in the natural language value and add it to the result
            def distributionPattern = result.distribution_pattern
            if(distributionPattern.toLowerCase().contains('on site retail')) {
                // this was distributed at site in the state where it is made
                distributionPattern = "${result.distribution_pattern} ${result.state}"
            }
            result.normalized_distribution_pattern = stateNormalizationService.getStates(distributionPattern)*.getAbbreviation()
        }

        return json
    }

    /**
     * TODO: REMOVE -- FOR TESTING PURPOSES ONLY
     */
    def getCountOfRecallsWithNoStates() {
        return FoodRecall.withCriteria { isEmpty 'distributionStates' }.size()
    }

    /**
     * TODO: REMOVE -- FOR TESTING PURPOSES ONLY
     */
    def getDistributionPatternOfRecallsWithNoStates() {
        def array = new JSONArray(FoodRecall.withCriteria { isEmpty 'distributionStates' }*.originalPayload)
        def pattern = []
        array.each { pattern << new JSONObject(it).distribution_pattern }
        return pattern
    }

    /**
     * Gets the count of recalls grouped by severity for the given parameters.  All parameters are optional.
     * @param state The state to get counts for. If no state is given, then nationwide counts are returned.
     * @param start The start date to get counts from. If not given, then the counts will be from the beginning of time.
     * @param end The end date to get counts up to. If not given, then the counts will go until current.
     * @return A list of lists, each list represents the severity as the 1st element and count as the 2nd, ex: [['high', 3757], ['low', 269], ['medium', 3779]]
     */
    def getCountsByState(final State state, final Date start, final Date end) {
        return FoodRecall.withCriteria {
            projections {
                groupProperty('severity')
                countDistinct 'recallNumber'
            }

            if(start) {
                ge('reportDate', start)
            }

            if(end) {
                le('reportDate', end)
            }

            if(state) {
                distributionStates { 'in'('state', state) }
            }
        }
    }

    /**
     * Gets the recalls for the given search criteria.  All fields search fields are optional (state, upc, and start/end dates).
     * Fields that are given are 'AND'ed together.  The results will be ordered by the {@link FoodRecall#reportDate} descending field.
     * @param state The state to get recalls for. If not given, all recalls will be given.
     * @param upc The upc barcode to get results for. If not given, ignored.
     * @param start The start date to get recalls from. If not given, then the recalls will be from the beginning of time.
     * @param end The end date to get recalls up to. If not given, then the recalls will go until current.
     * @param max The max amount of recalls to get at once. Must be > 0
     * @param offset The offset to get the results starting from. Must be >= 0
     * @return A list of {@link FoodRecall} recalls that matched the given query, ordered by {@link FoodRecall#reportDate} descending.
     */
    def getRecalls(final State state, final String upc, final Date start, final Date end, final int max, final int offset) {
        Preconditions.checkArgument(max > 0, 'The max to get must be > 0')
        Preconditions.checkArgument(offset >=0, 'The offset must be >= 0')

        def crit = FoodRecall.createCriteria()
        return crit.list(max: max, offset: offset) {
            if(start) {
                ge('reportDate', start)
            }

            if(end) {
                le('reportDate', end)
            }

            if(state) {
                distributionStates { 'in'('state', state) }
            }

            order('reportDate', 'desc')
            order('recallNumber', 'desc')
        }
    }







    // TODO -------------- ANYTHING UNDER HERE SUBJECT TO REMOVAL ---------------------

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
    def sendNotifications() {
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
    def readRss() {
        def url = "http://www2c.cdc.gov/podcasts/createrss.asp?c=146"
        def rss = new XmlSlurper().parse(url)
        println rss.channel.title
        rss.channel.item.each { item-> println "- ${item.title}" }​
    }

    /**
     * Builds criteria for the FDA API URL for using the manufacturer portion
     * of the upc to identify recalls specific to that manufacturer
     * @param upc - can be null
     * @return returns an empty string if null otherwise a partial string for the "search" parameter
     */
    def buildManufacturerOnlyCriteria(UpcBarcode upc) {
        if (upc == null) {
            return ""
        }
        return new StringBuffer("+AND+(product_description:")
                .append(upc.getManufacturer())
                .append("+code_info:")
                .append(upc.getManufacturer())
                .append(")")
                .toString()
    }

    def buildManufacturerAndProductCriteria(UpcBarcode upc) {
        if (upc == null) {
            return ""
        }
        String val = '"' + upc.getEncoding() + '+'+ upc.getManufacturer() + '+' + upc.getProduct() + '+' + upc.getCheckDigit() + '"'
        return new StringBuffer("+AND+(product_description:").append(val)
                .append("+code_info:").append(val)
                .append("+product_description:").append(upc.toString())
                .append("+code_info:").append(upc.toString())
                .append(")")
                .toString()

    }
}
