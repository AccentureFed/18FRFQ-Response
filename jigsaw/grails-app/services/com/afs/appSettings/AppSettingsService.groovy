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
    	def newAlert
		if (appAlert){
			newAlert = getAppAlert()
			if (newAlert)
			{
				newAlert.setAppAlert(appAlert)
				return newAlert.save(flush: true)
			}
		}
		return null 
    }


    /**
     * Gets the AppSettings
     * @return singleton AppSettings row
     */
    @Transactional(readOnly = true)
    def getAppAlert() {
		def appAlert = AppSettings.first()
		if (!appAlert){
			appAlert = new AppSettings(appSettings: "")
			appAlert.save();
		}
		return appAlert
    }

}
