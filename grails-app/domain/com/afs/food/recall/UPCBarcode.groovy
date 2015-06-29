package com.afs.food.recall

/**
 * Represents a UPC Barcode number as seen on product packaging.
 */
class UPCBarcode {

    /**
     * Represents a UPC Number of a product
     */
    String upcNumber

    static constraints = {
        upcNumber blank: false, nullable: false
    }
}
