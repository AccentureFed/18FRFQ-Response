package com.afs.nlp.utils

import com.afs.jigsaw.fda.food.api.State
import com.google.common.base.Preconditions
import com.google.common.collect.Sets

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
        def lowerCase = naturalLanguageString.toLowerCase()
        if(lowerCase.contains('all 50 u.s. states') || lowerCase.contains('nationwide') || lowerCase.contains('released for distribution in us')
        || lowerCase.contains('nationally') || lowerCase.contains('throughout the us') || lowerCase.contains('throughout the united states')
        || lowerCase.contains('internet') || lowerCase.contains('worldwide') || lowerCase.contains('nation wide') || lowerCase.contains('united states')
        || lowerCase.contains('natiowide') /*'natiowide is a common miss-spelling in our dataset*/ || lowerCase.contains('all 50 states')
        || lowerCase.contains('u.s.') || lowerCase.contains('online sales') || naturalLanguageString.contains('US')) {
            // add all states and bail out
            distributionStates.addAll(State.values())
        }

        if(lowerCase.contains('continental us')) {
            def continentalStates = Sets.newHashSet(State.values())
            continentalStates.remove(State.HAWAII)
            continentalStates.remove(State.ALASKA)
            distributionStates.addAll(continentalStates)
        }

        // phrase matcher...find states that should be treated as together/phrases
        MULTI_WORDED_STATES.each { multiWordSate ->
            multiWordSate.getAllFullNames().each {  fullName ->
                if(naturalLanguageString.contains(fullName)) {
                    distributionStates << multiWordSate

                    // remove the already found state from the original string
                    naturalLanguageString = naturalLanguageString.replace(fullName, '')
                } else if(lowerCase.contains(fullName.toLowerCase())) {
                    distributionStates << multiWordSate

                    // remove the already found state from the original string
                    naturalLanguageString = naturalLanguageString.replace(fullName.toLowerCase(), '')
                }
            }
        }

        // tokenize the string and find matches for each word, removing list/end-of-sentence punctuation
        naturalLanguageString.replace('military/govt/VA', '').replace(',', ' ').replace('/', ' ').replace('(', '').replace(')', '').replace('.','').tokenize().each { word ->
            def state = State.fromString(word)
            if(state) {
                // found a state
                distributionStates << state
            }
        }
        return distributionStates
    }
}
