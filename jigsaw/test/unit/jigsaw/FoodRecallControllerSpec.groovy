package jigsaw

import grails.test.mixin.*

import java.text.SimpleDateFormat

import spock.lang.Specification

import com.afs.food.recall.FoodRecallController
import com.afs.food.recall.FoodRecallService
import com.afs.jigsaw.fda.food.api.State

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(FoodRecallController)
class FoodRecallControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "getAllNoPagination"() {
		given:
		controller.foodRecallService=Mock(FoodRecallService)
		
		when :
//		params.limit = 0
//		params.skip = 0
		params.stateCode ='VA'
//		params.startDate = ''
//		params.endDate = ''
		
		controller.getAll();
		
		then:
		1*controller.foodRecallService.getPageByState(State.VIRGINIA, 10, 0, null, null)
    }
	
	void "getAllWithPagination"() {
		given:
		controller.foodRecallService=Mock(FoodRecallService)
		
		when :
		params.limit = 22
		params.skip = 15
		params.stateCode ='VA'
//		params.startDate = ''
//		params.endDate = ''
		
		controller.getAll();
		
		then:
		1*controller.foodRecallService.getPageByState(State.VIRGINIA, 22, 15, null, null)
	}
	
	void "getAllNoState"() {
		given:
		controller.foodRecallService=Mock(FoodRecallService)
		
		when :
		params.limit = 10
//		params.skip = 15
//		params.stateCode ='VA'
//		params.startDate = ''
//		params.endDate = ''
		
		controller.getAll();
		
		then:
		1*controller.foodRecallService.getPageByState(null, 10, 0, null, null)
	}
	
	void "getAllWithInvalidStartDate"() {
		given:
		controller.foodRecallService=Mock(FoodRecallService)
		def format = "yyyyMMdd"
		when :
		params.limit = 10
//		params.skip = 15
		params.stateCode ='VA'
		params.startDate = 'ssss'
		params.endDate = '20150601'
		controller.getAll();
		
		then:
		1*controller.foodRecallService.getPageByState(State.VIRGINIA, 10, 0, null, null)
	}
	
	
	void "getAllInvalidEndDate"() {
		given:
		controller.foodRecallService=Mock(FoodRecallService)
		def format = "yyyyMMdd"
		when :
		params.limit = 10
//		params.skip = 15
		params.stateCode ='VA'
		params.startDate = '20150501'
		params.endDate = 'saa'
		controller.getAll();
		
		then:
		1*controller.foodRecallService.getPageByState(State.VIRGINIA, 10, 0, null, null)
	}
	
	void "getAllWithDates"() {
		given:
		controller.foodRecallService=Mock(FoodRecallService)
		def format = "yyyyMMdd"
		when :
		params.limit = 10
//		params.skip = 15
		params.stateCode ='VA'
		params.startDate = '20150501'
		params.endDate = '20150601'
		
		controller.getAll();
		
		then:
		1*controller.foodRecallService.getPageByState(State.VIRGINIA, 10, 0, new SimpleDateFormat(format).parse("20150501"), new SimpleDateFormat(format).parse("20150601"))
	}
	
	
	void "countWithInvalidStartDate"() {
		given:
		controller.foodRecallService=Mock(FoodRecallService)
		def format = "yyyyMMdd"
		when :
		params.stateCode ='VA'
		params.startDate = 'ssss'
		params.endDate = '20150601'
		controller.count();
		
		then:
		1*controller.foodRecallService.getCountsByState(State.VIRGINIA, null, null)
	}
	
	
	void "countInvalidEndDate"() {
		given:
		controller.foodRecallService=Mock(FoodRecallService)
		def format = "yyyyMMdd"
		when :
		params.stateCode ='VA'
		params.startDate = '20150501'
		params.endDate = 'saa'
		controller.count();
		
		then:
		1*controller.foodRecallService.getCountsByState(State.VIRGINIA, null, null)
	}
	
	void "countWithDates"() {
		given:
		controller.foodRecallService=Mock(FoodRecallService)
		def format = "yyyyMMdd"
		when :
		params.stateCode ='VA'
		params.startDate = '20150501'
		params.endDate = '20150601'
		
		controller.count();
		
		then:
		1*controller.foodRecallService.getCountsByState(State.VIRGINIA, new SimpleDateFormat(format).parse("20150501"), new SimpleDateFormat(format).parse("20150601"))
	}
	
	
	void "countNoState"() {
		given:
		controller.foodRecallService=Mock(FoodRecallService)
		when :
//		params.stateCode = n
		
		controller.count();
		
		then:
		1*controller.foodRecallService.getCountsByState(null,_,_)
	}
}
