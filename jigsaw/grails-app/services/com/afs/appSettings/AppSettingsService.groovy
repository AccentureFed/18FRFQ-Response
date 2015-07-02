package com.afs.appSettings
import grails.transaction.Transactional

import com.afs.ApplicationMetadata


class AppSettingsService {

    private static final def APP_BANNER_KEY = 'appBanner'

    /**
     * Updates the app banner setting
     * @param appAlert the new app banner text to use
     * @return boolean True if the update succeeded; False otherwise
     */
    @Transactional
    def updateAppAlert(def String appAlert) {
        def banner = ApplicationMetadata.findByMetadataKey(APP_BANNER_KEY) ?: new ApplicationMetadata()
        banner.metadataKey = APP_BANNER_KEY
        banner.metadataValue = (appAlert == null) ? '' : appAlert
        if(!banner.save()) {
            log.error("Error saving the app banner update: ${banner.errors}")
            return false
        }
        return true
    }


    /**
     * Gets the App Banner setting
     * @return The app banner string
     */
    @Transactional(readOnly = true)
    def getAppAlert() {
        def banner = ApplicationMetadata.findByMetadataKey(APP_BANNER_KEY) ?: new ApplicationMetadata()
        return banner.metadataValue
    }
}
