package com.afs.food

import grails.converters.JSON

/**
 * Contains endpoints regarding the application information.
 */
class ApplicationController {

    private static final APP_VERSION_KEY = 'app.version'
    private static final APP_VERSION_BUILD_NUMBER_KEY = 'app.version.buildNumber'

    def grailsApplication

    /**
     * GET call that returns the version of the application running. No input parameters.
     * @return The version of the application.
     */
    def version() {
        def version = "${grailsApplication.metadata[APP_VERSION_KEY]}.${grailsApplication.metadata[APP_VERSION_BUILD_NUMBER_KEY] ?: 'dev_build'}"
        render ([version: version] as JSON)
    }
    
     /**
     * POST call that updates the application settomgs.
     *<p>
     * params include:<br />
     * <strong>appAlert</strong> - Required. String. The new home page alert banner<br />
     *</p>
     * @return N/A
     */
    def updateSettings() {
    	def newAlert = params.appAlert ? params.appAlert : null
    	if (newAlert != null) {
    		
    	}
    }
    
     /**
     * GET call that returns the current settings of the application. No input parameters.
     * @return The application settings.
     */
    def getSettings() {
    
    }
}
