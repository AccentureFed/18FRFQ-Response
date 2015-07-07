package com.afs.nlp.utils

import grails.test.mixin.TestFor
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

    void "test find upc numbers 7"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('UPC- 0 61500 00527 8  \n16 oz cans (24 p/c)')

        then:
        upcNumbers == ['061500005278']
    }

    void "test find upc numbers 8"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('Marketside brand Classic Iceberg Salad, 6 oz, 12 oz, 24 oz bags, UPCs: 81131-32893, 81131-32894, 81131-32895; Product is a salad item; bagged in clear polyethylene film (foodservice) and polypropylene/polyethylene (retail). Refrigerate and consume within Best By date. Product is processed and packaged by River Ranch Fresh Foods, LLC Salinas, CA')

        then:
        upcNumbers == [
            '8113132893',
            '8113132894',
            '8113132895'
        ]
    }

    void "test find upc numbers 9"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('UPCs: 0 41260 35692 6, 10041260367899  \n2 pack (48 p/c)')

        then:
        upcNumbers == [
            '041260356926',
            '10041260367899'
        ]
    }

    void "test find upc numbers 10"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('Whole Foods Market 9 inch Vegan Pumpkin Pie (32 oz) and 1/2 Vegan Pumpkin Pie (15 oz), packaged in both clear, clamshell packaging and brown, Kraft paper boxes. Distributed by Whole Foods Market. UPCs 24917101099 and 24917300699.')

        then:
        upcNumbers == ['24917101099', '24917300699']
    }

    void "test find upc numbers 11"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('Products bearing a \"best buy\" or \"expiration date\" earlier than 09/24/12 ONLY:\n\nUPCs - 074806001585')

        then:
        upcNumbers == ['074806001585']
    }

    void "test find upc numbers 12"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('The products are not identified with lot numbers. The jugs are labeled with UPCs:\n710372330024\n710372330017')

        then:
        upcNumbers == [
            '710372330024',
            '710372330017'
        ]
    }

    void "test find upc numbers 13"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('Sunland Organic Valencia Roasted and Blanched Granulated Peanuts, 30 lb UPCs 41333 & 41334.')

        then:
        upcNumbers == ['41333', '41334']
    }

    void "test find upc numbers 14"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('Sysco brand Shredded Iceberg (1/4\"), (1/8\") 4 x 5 lb bag, UPCs: n/a; Product is a salad item; bagged in clear polyethylene film (foodservice) and polypropylene/polyethylene (retail). Refrigerate and consume within Best By date. Product is processed and packaged by River Ranch Fresh Foods, LLC')

        then:
        upcNumbers == []
    }

    void "test find upc numbers 15"() {
        given:
        def barcodeService = new BarcodeNormalizationService()

        when:
        def upcNumbers = barcodeService.getUPCBarcodeNumbers('All lots and UPCs\nBest by date 2013')

        then:
        upcNumbers == []
    }
}
