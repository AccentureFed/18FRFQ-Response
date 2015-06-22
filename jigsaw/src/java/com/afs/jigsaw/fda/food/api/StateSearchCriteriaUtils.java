package com.afs.jigsaw.fda.food.api;

import java.util.ArrayList;
import java.util.Collection;

public class StateSearchCriteriaUtils {

	/**
	 * This method translates a specific state into the search criteria needed to request all of the
	 * recalls for the state from the FDA food recall API.  This requires using search terms since there
	 * is no state specific field in the API.<br /><br />
	 * 
	 *  This will handle nationwide recalls using specific key words, so nationwide recalls will be in 
	 *  EVERY states data set.<br /><br />
	 *  
	 *  Additionally since this functionality is being built using the search capability, states with overlapping names
	 *  (e.g. Virginia/West Virginia) need to be dealt with.  This service will handle ensuring these overlaps are excluded 
	 *  in the expressed search criteria.<br /><br />
	 *  
	 *  The resulting value can be used directly in the search attribute  in the FDA API.<br /><br />
	 *   
	 *   It is important to not some behaviors of the FDA search parameter which is documented here:
	 *   https://open.fda.gov/food/enforcement/
	 *   1) These terms are not case sensitive.  AL and al will both find AL, al, Al, and aL.
	 *   2) These terms are treated as complete words, so AL will find " AL ", and " AL," but not "ALSO" or "also"
	 *   
	 * @param e A State not null.If null then a search string will be returned for nationwide recalls
	 * 
	 * @return search criteria string for the "search" parameter related to searching for a specific state. 
	 */
	public String generateCriteria(State state) {
		
		//(distribution_pattern:%22Virginia%22+AND+NOT+distribution_pattern:%22West+Virginia%22)+distribution_pattern:VA+distribution_pattern:Nationwide
		StringBuffer criteria = new StringBuffer("distribution_pattern:nationwide+distribution_pattern:\"all+50+u.s.+states\"");

		if (state != null) {
			criteria .append("+distribution_pattern:").append(state.getAbbreviation());
			for (String name : state.getAllFullNames()) {
				Collection<String> exclude = this.getExclusionCriteria(state, name);
				if (!exclude.isEmpty()) {
					criteria.append("+(");
					exclude.forEach((exc) -> criteria.append("NOT+distribution_pattern:\"").append(exc.replaceAll(" ", "+")).append("\"+AND"));
					criteria.append("+distribution_pattern:\"").append(name.replaceAll(" ", "+")).append("\"");					
					criteria.append(")");
				} else {
					criteria .append("+distribution_pattern:\"").append(name.replaceAll(" ", "+")).append("\"");					
				}
				
			}
		}
		return criteria.toString();
		
	}
	
	/**
	 * Given a term (eg Virginia), how many other state specific names/abbreviations is it a subset of. This allows us to 
	 * create the specific criteria to find the term without eagerly fetching false positives where the term is a direct 
	 * subset of another state's term
	 * 
	 * @return the exclusion criteria or null if there is no exclusion criteria.
	 */
	private Collection<String> getExclusionCriteria(State baseState, String term) {
		Collection<String> excludeTerms = new ArrayList<String>();
		for (State state : State.values()){
        	if (baseState.equals(state)) {
        		//we dont want to exclude terms from the state we are searching for.
        		continue;
        	}
	        for (String cand : state.getAllFullNames()) {
	        	if (cand.contains(term) && !cand.equals(term)) {
	        		//this doesnt match our base term..so we dont care about it
	        		excludeTerms.add(cand);
	        	}
	        }
	    }
		return excludeTerms;
    }
}
