package com.afs.appSettings
import com.afs.domain.AppSettings

import grails.transaction.Transactional


class AppSettingsService {


    /**
     * Updates singleton AppSettings row
     * @param appAlert the new appAlert to save
     * @return boolean
     */
    @Transactional
    def updateAppAlert(String appAlert) {
    	def returnResult = false
    	def newAlert = getAppAlert()
		newAlert.setAppAlert(appAlert)
		def result = newAlert.save(flush: true)
		if (result) {
			returnResult = true
		}
		return returnResult
    }


    /**
     * Gets the AppSettings
     * @return singleton AppSettings row
     */
    @Transactional(readOnly = true)
    def getAppAlert() {
		def appAlert = AppSettings.first()
		if (appAlert == null){
			appAlert = new AppSettings(appSettings: "")
			appAlert.save(flush:true)
		}
		return appAlert
    }

}
