package com.afs.food.recall

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
		
		render foodRecallService.getCountsByState(com.afs.jigsaw.fda.food.api.State.fromString(params.stateCode));
		
	}

}
