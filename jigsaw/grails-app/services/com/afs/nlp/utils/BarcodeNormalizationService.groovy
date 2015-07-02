package com.afs.nlp.utils

import java.util.regex.Pattern

import com.google.common.base.Preconditions
import com.google.common.collect.Lists

class BarcodeNormalizationService {

    private static final Pattern UPC_PATTERN = ~/(?i)UPC(:|-)?(\sCode:?)?(\s\#)?(\sNumber:?)?\s([0-9- \t]+[0-9]+)/
    private static final Pattern UPCS_PATTERN = ~/(?i)UPCs(:|-)?(\sCode:?)?(\s\#)?(\sNumber:?)? ([0-9- \t,&(and)]+[0-9]+)/
    private static final Pattern UPCS_PATTERN2 = ~/(?i)UPCs(:|-)?(\sCode:?)?(\s\#)?(\sNumber:?)?\n([0-9-\s]+[0-9]+)/

    /**
     * Finds all of the UPC Barcode numbers inside of a natural language string.
     * @param naturalLanguageString The string to find upc numbers from. Must not be null.
     * @return A list of all the upc barcode numbers.
     */
    def getUPCBarcodeNumbers(def naturalLanguageString) {
        Preconditions.checkNotNull(naturalLanguageString)
        def matcher = UPC_PATTERN.matcher(naturalLanguageString)
        def multiMatcher = UPCS_PATTERN.matcher(naturalLanguageString)
        def multiMatcher2 = UPCS_PATTERN2.matcher(naturalLanguageString)

        Set<String> upcBarcodes = []
        while (matcher.find()) {
            upcBarcodes << matcher.group(5).replace(' ', '').replace('-', '')
        }
        while (multiMatcher.find()) {
            multiMatcher.group(5).replace(' ', '').replace('-', '').split(",|&|and").each { upcBarcodes << it }
        }

        while (multiMatcher2.find()) {
            multiMatcher2.group(5).replace(' ', '').replace('-', '').split("\n").each { upcBarcodes << it }
        }
        return Lists.newArrayList(upcBarcodes)
    }
}
