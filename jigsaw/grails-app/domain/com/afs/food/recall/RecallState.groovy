package com.afs.food.recall

import com.afs.jigsaw.fda.food.api.State

class RecallState {

    State state

    static belongsTo = FoodRecall
    static hasMany = [foodRecalls: FoodRecall]

    static constraints = {
        state unique: true, nullable: false
    }
}
