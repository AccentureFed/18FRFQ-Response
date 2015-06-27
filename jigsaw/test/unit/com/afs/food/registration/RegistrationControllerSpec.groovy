package com.afs.food.registration

import grails.test.mixin.*
import spock.lang.Specification

import com.afs.domain.Registration

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(RegistrationController)
@Mock([Registration])
class RegistrationControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "allInputsProvided"() {
        given:
        controller.registrationService=Mock(RegistrationService)

        when :
        params.severity = 'high'
        params.stateCode = ['VA']
        params.email ='email@email.com'
        controller.registerAlerts()

        then:
        response.text.contains('"classification":{"enumType":"com.afs.jigsaw.fda.food.api.Severity","name":"HIGH"}')
        response.text.contains('"emailAddress":"email@email.com"')
        response.text.contains('"stateList":"VA"')
        1*controller.registrationService.registerEmail(_)
    }

    void "severityCaseInsensitive"() {
        given:
        controller.registrationService=Mock(RegistrationService)

        when :
        params.severity = 'MeDiUm'
        params.stateCode = ['VA']
        params.email ='email@email.com'
        controller.registerAlerts()

        then:
        response.text.contains('"classification":{"enumType":"com.afs.jigsaw.fda.food.api.Severity","name":"MEDIUM"}')
        response.text.contains('"emailAddress":"email@email.com"')
        response.text.contains('"stateList":"VA"')
        1*controller.registrationService.registerEmail(_)
    }

    void "multipleStates"() {
        given:
        controller.registrationService=Mock(RegistrationService)

        when :
        params.severity = 'high'
        params.stateCode = ['VA', 'Delaware', 'nh']
        params.email ='email@email.com'
        controller.registerAlerts()

        then:
        response.text.contains('"classification":{"enumType":"com.afs.jigsaw.fda.food.api.Severity","name":"HIGH"}')
        response.text.contains('"emailAddress":"email@email.com"')
        response.text.contains('"stateList":"VA,DE,NH"')
        1*controller.registrationService.registerEmail(_)
    }
    void "missingEmail"() {
        given:
        controller.registrationService=Mock(RegistrationService)

        when :
        params.severity = 'high'
        params.stateCode = ['VA']
        controller.registerAlerts()

        then:
        response.text== '{"Missing Required Information":"true"}'
        0*controller.registrationService.registerEmail(_)
    }


    void "missingSeverity"() {
        given:
        controller.registrationService=Mock(RegistrationService)

        when :
        params.email = 'email'
        params.stateCode = ['VA']
        controller.registerAlerts()

        then:
        response.text== '{"Missing Required Information":"true"}'
        0*controller.registrationService.registerEmail(_)
    }

    void "missingState"() {
        given:
        controller.registrationService=Mock(RegistrationService)

        when :
        params.severity = 'high'
        params.email = 'email'
        controller.registerAlerts()

        then:
        response.text== '{"Missing Required Information":"true"}'
        0*controller.registrationService.registerEmail(_)
    }

    void "invalidSeverity"() {
        given:
        controller.registrationService=Mock(RegistrationService)

        when :
        params.severity = 'hhh'
        params.stateCode = ['VA']
        controller.registerAlerts()

        then:
        response.text== '{"Missing Required Information":"true"}'
        0*controller.registrationService.registerEmail(_)
    }
}
