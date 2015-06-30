package com.afs.domain

/**
 * Represents the site settings  
 * 
 */
class AppSettings {

    static constraints = {
		appAlert blank:true, unique: true, nullable: false
    }
	
	String appAlert


	public String getAppAlert() {
		return appAlert
	}

	public void setAppAlert(String appAlert) {
		this.appAlert = appAlert
	}
	
}