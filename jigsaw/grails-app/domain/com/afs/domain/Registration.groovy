package com.afs.domain

import com.afs.jigsaw.fda.food.api.Classification;

/**
 * Represents a user's registration for notifications
 * This will consist of an email address, a severity threshhold, and a list of states
 * that the user is interested in.  
 * 
 * A registration will represent a mom's desire to be notified about recalls in her
 * region or state that might affect her.  
 * 
 * @author peterdailey
 *
 */
class Registration {

    static constraints = {
    }
	
	/**
	 * A valid email address for the user
	 */
	String emailAdress;
	
	/**
	 * a comma separated list of states that the user is interested in recieving
	 * notifications for.
	 */
	String stateList; 
	
	Classification classification;

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	public String getStateList() {
		return stateList;
	}

	public void setStateList(String stateList) {
		this.stateList = stateList;
	}

	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}
	
}
