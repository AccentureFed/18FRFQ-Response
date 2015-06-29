package com.afs.nlp.utils

import java.util.regex.Pattern

import com.google.common.base.Preconditions

class BarcodeNormalizationService {

    private static final Pattern UPC_PATTERN = ~/(?i)UPC(:)?(\sCode:?)?(\sNumber:?)?\s([0-9-\s]+[0-9]+)/

    /**
     * Finds all of the UPC Barcode numbers inside of a natural language string.
     * @param naturalLanguageString The string to find upc numbers from. Must not be null.
     * @return A list of all the upc barcode numbers.
     */
    def getUPCBarcodeNumbers(def naturalLanguageString) {
        Preconditions.checkNotNull(naturalLanguageString)
        def matcher = UPC_PATTERN.matcher(naturalLanguageString)

        def upcBarcodes = []
        while (matcher.find()) {
            upcBarcodes << matcher.group(4).replace(' ', '').replace('-', '')
        }
        return upcBarcodes
    }
}
