package com.afs.appSettings

import grails.converters.JSON

/**
 * Contains endpoints regarding the application settings.
 */
class AppSettingsController {

    static allowedMethods = [updateSettings:"POST", getSettings:"GET"]

    def appSettingsService

    /**
     * POST call that updates the application settings
     *<p>
     * params include:<br />
     * <strong>appAlert</strong> - Required. String. The new home page alert banner<br />
     *</p>
     * @return N/A
     */
    def updateSettings() {
        if (params.appAlert) {
            if(!appSettingsService.updateAppAlert(params.appAlert)) {
                // return 501 if it fails
                render(status: 501)
                return
            }
        }
        render(status: 200)
    }

    /**
     * GET call that returns the current settings of the application. No input parameters.
     * @return The application alert.
     */
    def getSettings() {
        def alert = appSettingsService.getAppAlert()
        render ([appAlert: alert] as JSON)
    }
}