package com.afs.food.recall

import grails.test.mixin.TestFor

import org.codehaus.groovy.grails.web.json.JSONObject

import spock.lang.Specification

import com.afs.jigsaw.fda.food.api.*
/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(FoodRecallService)
class FoodRecallServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "buildCountUrl"() {
        given:
        def stateUtils = new StateSearchCriteriaUtils()
		def service = new FoodRecallService();
		
        when:
        def url = service.buildCountUrl(State.ALABAMA)

        then:
        url == service.BASE_COUNT_URL + "search=" + stateUtils.generateCriteria(State.ALABAMA) + "&count=classification.exact"
    }	

	void "transformCountJson"() {
		given:
		def fdaJson = "{ "+
					  "\"meta\": {"+
					    "\"disclaimer\": \"openFDA is...\", "+
						"\"license\": \"http://open.fda.gov/license\","+
						"\"last_updated\": \"2015-05-31\""+
						"}, "+
						"\"results\": [ "+
					   "{ \"term\": \"Class I\", \"count\": 11}, "+
					   "{ \"term\": \"Class II\", \"count\": 22}, "+
					   "{ \"term\": \"Class III\", \"count\": 33}, "+
					  "]}";
		def state = State.ALABAMA;
		
		def ourJson = "{\"stateCode\":\"HI\",\"results\":[{\"severity\":\"high\",\"count\":11},{\"severity\":\"medium\",\"count\":22},{\"severity\":\"low\",\"count\":33}]}";
		
		when :
		def transformedJson = service.transformCountJson(State.HAWAII, new JSONObject(fdaJson));
		
		then:
		new JSONObject(ourJson).equals(transformedJson);
	}

	void "transformCountJsonWithExtraInfo"() {
		given:
		def fdaJson = "{ "+
					  "\"meta\": {"+
						"\"disclaimer\": \"openFDA is...\", "+
						"\"license\": \"http://open.fda.gov/license\","+
						"\"last_updated\": \"2015-05-31\""+
						"}, "+
						"\"ignored\": { info:\"info\"},"+
						"\"results\": [ "+
					   "{ \"term\": \"Class IV\", \"count\": 11}, "+
					   "{ \"term\": \"Class II\", \"count\": 22}, "+
					   "{ \"term\": \"Class III\", \"count\": 33}, "+
					  "]}";
		def state = State.ALABAMA;
		
		def ourJson = "{\"stateCode\":\"HI\",\"results\":[{\"severity\":\"medium\",\"count\":22},{\"severity\":\"low\",\"count\":33}]}";
		
		when :
		def transformedJson = service.transformCountJson(State.HAWAII, new JSONObject(fdaJson));
		
		then:
		new JSONObject(ourJson).equals(transformedJson);
	}
	
}
