package com.afs.food.recall

import grails.test.mixin.*
import spock.lang.Specification

import com.afs.jigsaw.fda.food.api.*
/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(FoodRecallService)
class FoodRecallServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void buildManufacturerOnlyCriteriaNull() {
        given:
        def upc =  null

        when :
        def options = service.buildManufacturerOnlyCriteria(upc)

        then:
        options ==""
    }

    void buildManufacturerOnlyCriteria() {
        given:
        def upc =  UpcBarcode.buildBarcode("122222333334")

        when :
        def options = service.buildManufacturerOnlyCriteria(upc)

        then:
        options =="+AND+(product_description:22222+code_info:22222)"
    }

    void buildManufacturerAndProductCriteriaNull() {
        given:
        def upc =  null

        when :
        def options = service.buildManufacturerAndProductCriteria(upc)

        then:
        options ==""
    }

    void buildManufacturerAndProductCriteriaCriteria() {
        given:
        def upc =  UpcBarcode.buildBarcode("122222333334")

        when :
        def options = service.buildManufacturerAndProductCriteria(upc)

        then:
        options =='+AND+(product_description:"1+22222+33333+4"+code_info:"1+22222+33333+4"+product_description:122222333334+code_info:122222333334)'
    }
}
