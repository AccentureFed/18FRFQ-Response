package com.afs.food.recall

import java.text.SimpleDateFormat

import com.afs.jigsaw.fda.food.api.*

class FoodRecallController {

    def foodRecallService

    def recalls() {
        render foodRecallService.getRecalls() 
    }
	
	def readRSS() {
		render foodRecallService.readRss()
	}
	
	def sendNotifications() {
		render foodRecallService.sendNotifications()
	}

	def count() {
		
		render foodRecallService.getCountsByState(State.fromString(params.stateCode));
		
	}

	/**
	 * Used to render a list of recalls with pagination and enriched values.  This response format includes the 
	 * FDA format with an addditional normalized_distribution_pattern field as a CSV of formal state names
	 * 
	 * params include:
	 * state - optional.  If optional, then no state specific searching will be performed. Can be any name or abbreviation of a state
	 * limit - optional.  Numeric.  The number of results to return. 10 is defualt
	 * skip - optional.  Numeric.  Allows pagination.  default is 0 (start at beginning of list)
	 * startDate - optional. Date in yyyyMMdd format
	 * endDate - optinoal. Date in yyyyMMdd format
	 * 
	 * Only if both dates are provided will a date specific search be used.  If either is not provided, then no date constraint will be added to the search.
	 * 
	 * @return
	 */
	def getAll() {
		def limit = params.limit == null ? 10 : params.limit.toInteger();
		def skip = params.skip == null ? 0 : params.skip.toInteger();
		def state = params.stateCode == null ? null : State.fromString(params.stateCode)
		def startDate
		def endDate
		try {
			startDate = new SimpleDateFormat("yyyyMMdd").parse( params.startDate)
			endDate = new SimpleDateFormat("yyyyMMdd").parse( params.endDate)
		}catch(Exception e) {
			//invalid dates
			startDate = null
			endDate = null
		}
		render foodRecallService.getPageByState(state, limit,  skip, startDate, endDate);
	}

}
