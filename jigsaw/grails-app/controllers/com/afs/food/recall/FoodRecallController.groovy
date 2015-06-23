package com.afs.food.recall

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

	def getAll() {
		def limit = params.limit == null ? 10 : params.limit.toInteger();
		def skip = params.skip == null ? 0 : params.skip.toInteger();
		render foodRecallService.getPageByState(State.fromString(params.stateCode), limit,  skip);
	}

}
