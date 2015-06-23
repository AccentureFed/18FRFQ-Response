package com.afs.food.registration

import com.afs.domain.Registration;

import grails.transaction.Transactional


/**
 * This is a transactional service providing logic related to registering a user for 
 * email notifications.
 * 
 * 
 * @author peterdailey
 *
 */
@Transactional
class RegistrationService {

    def serviceMethod() {

    }

	/**
	 * Stores the registration for later use. The expectation is that the registration object
	 * 
	 * @param reg
	 * @return
	 */
	def registerEmail(Registration reg) {
		
		reg.save();	
	}	
}
