package com.afs.food.registration

import grails.converters.JSON

import com.afs.domain.Registration
import com.afs.jigsaw.fda.food.api.Classification
import com.afs.jigsaw.fda.food.api.State

class RegistrationController {

    def index() { }

	/**
	 * A request to register a user for notifications.  This request contains the following 
	 * parameters:
	 * 
	 * email = string required.  
	 * severity = string required.  Will be one of HIGH, MEDIUM, LOW ...case insensitive.
	 * stateCode = string at least one required.  These will be validated from the State enum (as words and abbreviations).  
	 * 
	 * @return - A simple OK message
	 */
	def registerAlerts() {
		def email = params.email
		def severity = params.severity == null ? null : Classification.valueOf(params.severity.toUpperCase())
		def states = new ArrayList<String>();
		
		params.stateCode.each  { val ->
			State st =State.fromString(val) 
			if (st != null) {
				states.add( st.getAbbreviation())
			}
		}
		
		Registration reg = new Registration();
		reg.setClassification(severity);
		reg.setStateList(states.join(","));
		reg.setEmailAdress(email)
		
		render reg as JSON;
	}	
}
