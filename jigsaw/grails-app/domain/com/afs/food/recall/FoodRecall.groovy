package com.afs.food.recall

import com.google.common.collect.Lists

class FoodRecall {

    /**
     * The json payload as seen in the API (with our normalized states)
     */
    String originalPayload

    /**
     * Storing for sorting purposes, more enriching
     */
    Date reportDate

    /**
     * Used for filtering out duplicate recalls that is in the API
     */
    String recallNumber

    /**
     * Used for grouping the counts by severity
     */
    String severity

    /**
     * Contains our enriched distribution pattern
     */
    static hasMany = [distributionStates: RecallState]

    static mapping = { originalPayload type: 'text' }

    static constraints = {
        originalPayload blank: false, nullable: false
        recallNumber unique: true, blank: false, nullable: false
        reportDate nullable: false
        severity blank: false, nullable: false, inList: Lists.newArrayList(FoodRecallService.CLASSIFICATION_TO_SEVERITY.values())
    }
}
