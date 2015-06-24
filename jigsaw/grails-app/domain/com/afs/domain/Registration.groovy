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
		emailAddress email:true, blank:false, unique: true
		stateList blank:false
		classification blank:false
    }
	
	/**
	 * A valid email address for the user
	 */
	String emailAddress;
	
	/**
	 * a comma separated list of states that the user is interested in recieving
	 * notifications for.
	 */
	String stateList; 
	
	Classification classification;


	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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
