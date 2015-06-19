package com.afs.food.recall

import grails.transaction.Transactional

import org.codehaus.groovy.grails.web.json.JSONObject

import com.afs.jigsaw.fda.food.api.State
import com.google.common.collect.Lists

@Transactional
class FoodRecallService {

    private static final def MAX_RESULTS = 100
    private static final def BASE_URL = "https://api.fda.gov/food/enforcement.json?limit=${MAX_RESULTS}"

    private static final def MULTI_WORDED_STATES = []
    static {
        State.values().each { state ->
            if(state.getFullName().contains(' ')) {
                MULTI_WORDED_STATES << state
            }
        }
    }

    def getAllRecalls() {
        def json = new JSONObject(new URL("${BASE_URL}").getText())
        def totalResults = json.meta.results.total

        final Set<String> distributionStates = []
        json.results.each { result ->
            distributionStates.clear()

            def pattern = result.distribution_pattern
            if(pattern.contains('all 50 U.S. States') || pattern.contains('nationwide')) {
                // add all 50 states and bail out
                distributionStates.addAll(State.values()*.getAbbreviation())
            } else {

                MULTI_WORDED_STATES.each { multiWordSate ->
                    multiWordSate.getAllFullNames().each {  fullName ->
                        if(pattern.contains(fullName)) {
                            distributionStates << multiWordSate.getAbbreviation()
                            pattern = pattern.replace(fullName, '')
                        }
                    }
                }

                pattern.tokenize().each { distribution_item ->
                    def state = State.fromString(distribution_item.replace(',', '').replace('.',''))
                    if(state) {
                        // found a state
                        distributionStates << state.getAbbreviation()
                    }
                }
            }
            result.normalized_distribution_pattern = Lists.newArrayList(distributionStates)
        }
    }
}
