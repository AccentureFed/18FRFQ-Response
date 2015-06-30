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
    	def newAlert = params.appAlert ? params.appAlert : null
    	if (newAlert) {
    		def result = appSettingsService.updateAppAlert(newAlert)
    	}
    }
    
     /**
     * GET call that returns the current settings of the application. No input parameters.
     * @return The application settings.
     */
    def getSettings() {
    	def alert = appSettingsService.getAppAlert()
    	if (alert){
    		render (contentType: "application/json"){ appAlert: alert }
    	} else {
    		render (contentType: "application/json"){ appAlert: "" }
    	}
    }
}