package com.afs.food.recall

import grails.test.mixin.*

import java.text.DateFormat
import java.text.SimpleDateFormat

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
        def url = service.buildCountUrl(State.ALABAMA, "")

        then:
        url == service.BASE_COUNT_URL + "search=(" + stateUtils.generateCriteria(State.ALABAMA) + ")"
    }	

	void "buildCountUrlWithOptions"() {
		given:
		def stateUtils = new StateSearchCriteriaUtils()
		def service = new FoodRecallService();
		
		when:
		def url = service.buildCountUrl(State.ALABAMA, "withoptions")

		then:
		url == service.BASE_COUNT_URL + "search=(" + stateUtils.generateCriteria(State.ALABAMA) + ")withoptions"
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
	
	
	void "buildOptionsWithLimit"() {
		given:
		def limit = new Integer(10);
		def skip = null;
		def service = new FoodRecallService();
		
		when :
		def options = service.buildOptions(limit, skip);
		
		then:
		options == "&limit=10"
	}
	
	
	void "buildOptionsWithLimitOverMax"() {
		given:
		def limit = new Integer(101);
		def skip = null;
		def service = new FoodRecallService();
		
		when :
		def options = service.buildOptions(limit, skip);
		
		then:
		options == "&limit=100"
	}

	void "buildOptionsWithNeither"() {
		given:
		def limit = null;
		def skip = null;
		def service = new FoodRecallService();
		
		when :
		def options = service.buildOptions(limit, skip);
		
		then:
		options == ""
	}
	
	void "buildOptionsWithSkipAndLimit"() {
		given:
		def limit = new Integer(101);
		def skip = new Integer(20);
		def service = new FoodRecallService();
		
		when :
		def options = service.buildOptions(limit, skip);
		
		then:
		options == "&limit=100&skip=20"
	}
	
	void "buildOptionsWithSkipOnly"() {
		given:
		def limit = null;
		def skip = new Integer(20);
		def service = new FoodRecallService();
		
		when :
		def options = service.buildOptions(limit, skip);
		
		then:
		options == ""
	}

	void "buildDateRangeOneNull"() {
		given:
		def startDate = new SimpleDateFormat("yyyyMMdd").parse("20150501")
		def endDate = new SimpleDateFormat("yyyyMMdd").parse("20150601")
		def service = new FoodRecallService();
		
		when :
		def options = service.buildDateRange(startDate, endDate);
		
		then:
		options == "+AND+report_date:[20150501+TO+20150601]"
	}
	
	void "buildDateRangeNoFrom"() {
		given:
		def startDate = null
		def endDate = new SimpleDateFormat("yyyyMMdd").parse("20150601")
		def service = new FoodRecallService();
		
		when :
		def options = service.buildDateRange(startDate, endDate);
		
		then:
		options ==""
	}
	
	void "buildDateRangeNoTo"() {
		given:
		def startDate =  new SimpleDateFormat("yyyyMMdd").parse("20150601")
		def endDate = null
		def service = new FoodRecallService();
		
		when :
		def options = service.buildDateRange(startDate, endDate);
		
		then:
		options ==""
	}
}
