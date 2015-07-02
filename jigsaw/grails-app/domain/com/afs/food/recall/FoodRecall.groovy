package com.afs.food.recall

import com.afs.jigsaw.fda.food.api.Severity

/**
 * Represents a food recall item with fields pulled out for data encrichment purposes.  The JSON Payload is also enriched with our custom data fields.
 */
class FoodRecall {

    /**
     * The json payload as seen in the API (with our normalized states and normalized barcodes)
     */
    String enrichedJSONPayload

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
    Severity severity

    /**
     * A product description of the food item. Used for keyword search enrichment purposes.
     */
    String productDescription

    /**
     * The recalling firm of the food item. Used for keyword search enrichment purposes.
     */
    String recallingFirm

    /**
     * The reason for the recall. Used for keyword search enrichment purposes.
     */
    String reasonForRecall

    /**
     * Contains our enriched distribution pattern
     */
    static hasMany = [distributionStates: RecallState, barcodes: UPCBarcode]

    static mapping = {
        enrichedJSONPayload type: 'text'
        productDescription type: 'text'
        reasonForRecall type: 'text'
    }

    static constraints = {
        enrichedJSONPayload blank: false, nullable: false
        recallNumber unique: true, blank: false, nullable: false
        reportDate nullable: false
        severity blank: false, nullable: false
        productDescription blank: false, nullable: false
        recallingFirm blank: false, nullable: false
        reasonForRecall blank: false, nullable: false
    }
}
