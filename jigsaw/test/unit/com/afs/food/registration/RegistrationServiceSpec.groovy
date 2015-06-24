
package com.afs.food.registration

import grails.plugin.mail.MailService
import grails.test.mixin.*
import spock.lang.Specification

import com.afs.domain.Registration

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(RegistrationService)
@Mock([Registration])
class RegistrationServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "registerNull"() {
		given:
		service.mailService = Mock(MailService)
		when :
		def response = service.registerEmail(null);
		
		then:
		response == null
		0*service.mailService.sendEmail(_)
    }
	
	void "registerUser"() {
		given:
		service.mailService = Mock(MailService)
		
		when :
		def response = service.registerEmail(new Registration(emailAddress:'email' ));
		
		then:
		1*service.mailService.sendMail(_)
	}


	void "registerBadEmail"() {
		given:
		def serviceMock = mockFor(MailService)
		serviceMock.demand.sendMail  { throw new IllegalArgumentException() }
		service.mailService = serviceMock.createMock()
		
		when :
		service.registerEmail(new Registration(emailAddress:'email' ));
		
		then:
		thrown(IllegalArgumentException)
		
	}
}

