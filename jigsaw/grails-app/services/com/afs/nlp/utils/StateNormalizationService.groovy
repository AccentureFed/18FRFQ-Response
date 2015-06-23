package com.afs.nlp.utils

import com.afs.jigsaw.fda.food.api.State
import com.google.common.base.Preconditions

class StateNormalizationService {

    /**
     * @Transactional breaks unit testing, set it here...no loss in behavior
     * http://stackoverflow.com/questions/18999343/how-do-you-test-service-or-controller-methods-in-grails-2-3
     */
    static transactional = true

    /*
     *  this creates are "nlp phrases" list
     *  states with multi words will be treated as a single phrase
     */
    private static final def MULTI_WORDED_STATES = []
    static {
        State.values().each { state ->
            if(state.getFullName().contains(' ')) {
                MULTI_WORDED_STATES << state
            }
        }
    }

    /**
     * Gets a Set of {@link State} objects that are mentioned in the given string.
     * @param naturalLanguageString A string of natural text to find states in. Must not be null.
     * @return A Set of {@link State} objects that are mentioned in the given string. Never null. May be an empty Set.
     */
    def getStates(def naturalLanguageString) {
        Preconditions.checkNotNull(naturalLanguageString)

        final Set<State> distributionStates = []

        // check for an 'all' indicator
        if(naturalLanguageString.toLowerCase().contains('all 50 u.s. states') || naturalLanguageString.toLowerCase().contains('nationwide')) {
            // add all states and bail out
            distributionStates.addAll(State.values())
        } else {

            // phrase matcher...find states that should be treated as together/phrases
            MULTI_WORDED_STATES.each { multiWordSate ->
                multiWordSate.getAllFullNames().each {  fullName ->
                    if(naturalLanguageString.contains(fullName)) {
                        distributionStates << multiWordSate

                        // remove the already found state from the original string
                        naturalLanguageString = naturalLanguageString.replace(fullName, '')
                    }
                }
            }

            // tokenize the string and find matches for each word, removing list/end-of-sentence punctuation
            naturalLanguageString.tokenize().each { word ->
                def state = State.fromString(word.replace(',', '').replace('.',''))
                if(state) {
                    // found a state
                    distributionStates << state
                }
            }
        }
        return distributionStates
    }
}
