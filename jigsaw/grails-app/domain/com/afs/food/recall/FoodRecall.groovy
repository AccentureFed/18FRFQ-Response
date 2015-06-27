package com.afs.food.recall

import com.google.common.collect.Lists

class FoodRecall {

    /**
     * The json payload as seen in the API (with our normalized states)
     */
    String originalPayload

    /**
     * Date that the FDA issued the enforcement report for the product recall.<br /><br />
     * Storing for sorting purposes, more enriching
     */
    Date reportDate

    /**
     * A numerical designation assigned by FDA to a specific recall event used for tracking purposes.<br /><br />
     * Used for filtering out duplicate recalls that is in the API
     */
    String recallNumber

    /**
     * Numerical designation (low, medium, or high) that is assigned by FDA to a particular product recall that indicates the relative degree of health hazard.<br /><br />
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
