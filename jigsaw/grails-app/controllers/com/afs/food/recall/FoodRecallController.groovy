package com.afs.food.recall

class FoodRecallController {

    def foodRecallService

    def recalls() {
        render foodRecallService.getRecalls()
    }
}
