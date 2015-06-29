package com.afs.nlp.utils

import grails.test.mixin.*
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BarcodeNormalizationService)
class BarcodeNormalizationServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test find upc numbers 1"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('Sell By: (up to and including) 04/01/15; UPC: 55708 90899;\n\nSell By: (up to and including) 04/01/15; UPC: 56839 60917;')

        then:
        upcNumbers == ['5570890899', '5683960917']
    }

    void "test find upc numbers 2"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('Black & White Mini Cookies, Net Wt. 2 oz. (56 g), SKU 408785, UPC code 833282000495, Manufactured for: Starbucks Coffee Company, Seattle, WA --- The product comes in a clear film package, each containing two cookies.  Each case contains 12 - 2 oz. packages (units).')

        then:
        upcNumbers == ['833282000495']
    }

    void "test find upc numbers 3"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('Best By date 2/4/2016 and UPC code 7695862059-1')

        then:
        upcNumbers == ['76958620591']
    }

    void "test find upc numbers 4"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('Raw Pine Nuts sold in the following brands and package sizes:  \n-Good Sense  3 oz. (85g) bag  UPC 30243-86681,  \n-Good Sense 4 oz. (113g) bag, UPC 30243-86680,  \n-Good Sense 4 oz. (113g) tub UPC 30243-50799,  \n-Good Sense 15 oz. (425g) bag UPC 30243-86687,  \n-Good Sense 5 lbs. box UPC 30243-02860, \n-Good Sense 4 oz. bags may have been sold as a floor display, UPC 30243 86683 ,  and  \n-Weis 4 oz. (113g) tub UPC 30243-72557')

        then:
        upcNumbers == [
            '3024386681',
            '3024386680',
            '3024350799',
            '3024386687',
            '3024302860',
            '3024386683',
            '3024372557'
        ]
    }

    void "test find upc numbers 5"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('BRIDGE CITY Baking gluten free Apple Crumble Pie.  This product is frozen ready to bake.  Net Wt 24oz\n\nUPC 0 91037 54453 7\n\nLabel is orange and maroon with black, white and orange lettering.')

        then:
        upcNumbers == ['091037544537']
    }

    void "test find upc numbers 6"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('Hanna ford Gourmet Cookie Platter (36 oz. package),  Includes oatmeal raisin cookies\nUPC # 04126874662')

        then:
        upcNumbers == ['04126874662']
    }
}
