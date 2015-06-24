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
	
	def mailService
	
    def serviceMethod() {

    }

	/**
	 * Stores the registration for later use. The expectation is that the registration object
	 * 
	 * @param reg - the user to register
	 * @return null if no registration provided.  The same registration instance otherwise
	 */
	def registerEmail(Registration reg) {
		if (reg == null) {
			return null;
		}
		mailService.sendMail {
			   to reg.getEmailAddress()
			   from "accenturefedjigsaw@gmail.com"
			   subject "Welcome"
			   body 'Thanks for Registering!'
			}
		
		reg.save();	
		
	}	
}
