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
}
