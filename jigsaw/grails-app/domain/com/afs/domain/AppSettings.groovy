package com.afs.domain

/**
 * Represents the site settings  
 * 
 * @author josh baker
 *
 */
class AppSettings {

    static constraints = {
		appAlert blank:true, unique: true
    }
	
	String appAlert
	DateTime lastUpdated


	public String getAppAlert() {
		return appAlert;
	}

	public void setAppAlert(String newAlert) {
		this.appAlert = newAlert;
	}
	
	public getLastUpdated() {
		return this.lastUpdated;
	}
	
}