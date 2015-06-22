package com.afs.nlp.utils

import grails.test.mixin.*
import spock.lang.Specification

import com.afs.jigsaw.fda.food.api.State

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(StateNormalizationService)
class StateNormalizationServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test state natural language query"() {
        given:
        def stateService = new StateNormalizationService()

        when:
        def states = stateService.getStates("Distributed to ID, MN, and SC.  Foreign distribution to Sweden. No military/govt/VA consignees.")

        then:
        Set<State> expected = [
            State.IDAHO,
            State.MINNESOTA,
            State.SOUTH_CAROLINA
        ]
        states == expected
    }

    void "test state natural language query 2"() {
        given:
        def stateService = new StateNormalizationService()

        when:
        def states = stateService.getStates("U.S. Nationwide")

        then:
        Set<State> expected = []
        expected.addAll(State.values())
        states == expected
    }

    void "test state natural language query 3"() {
        given:
        def stateService = new StateNormalizationService()

        when:
        def states = stateService.getStates("Product is picked up at Middle East Bakery by independent drivers that supply markets in their territory.  The headquarters representative for each firm that potentially received product was notified as a result the downstream customers (retail stores) were notified by their corporate offices.  \n\nThe Joseph s brand name products were distributed in markets in Vermont, New Hampshire, Maine, Massachusetts and Connecticut, and in the following stores: Market Basket, Stop & Shop, Shaw s, Hannaford and Big Y.  The Trader Joe s products were distributed only in Trader Joe's stores in Connecticut, Delaware, Maine, Maryland, Massachusetts, New Hampshire, New Jersey, New York, Pennsylvania, Rhode Island, Vermont, Virginia, and Washington D.C.")

        then:
        Set<State> expected = [
            State.NEW_HAMPSHIRE,
            State.NEW_JERSEY,
            State.NEW_YORK,
            State.RHODE_ISLAND,
            State.DISTRICT_OF_COLUMBIA,
            State.VERMONT,
            State.MAINE,
            State.MASSACHUSETTS,
            State.CONNECTICUT,
            State.DELAWARE,
            State.MARYLAND,
            State.PENNSYLVANIA,
            State.VIRGINIA
        ]
        states == expected
    }
}
