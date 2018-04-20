/*******************************************************************************
 * Copyright 2018 Mountain Fog, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package ai.idylnlp.nlp.utils.ngrams;

import java.util.Arrays;
import java.util.Collection;

/**
 * Utility functions for N-Grams.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public class NgramUtils {

	private NgramUtils() {
		// This is a utility class.
	}
	
	/**
	 * Returns the N-Grams for a string of a given length.
	 * @param s The string input.
	 * @param len The length of the n-grams.
	 * @return A collection of N-Grams for the input string.
	 */
	public static Collection<String> getNgrams(String s, int len, boolean removePunctuation) {
		
		if(removePunctuation) {
			s = s.replaceAll("\\p{P}", "");
		}
		
		// See: http://stackoverflow.com/a/3656855
		
		// TODO: Any benefit to using a whitespace tokenizer instead of splitting on a space?
	    String[] parts = s.split(" ");
	    String[] result = new String[parts.length - len + 1];
	    
	    for(int i = 0; i < parts.length - len + 1; i++) {
	      
	    	StringBuilder sb = new StringBuilder();
	       
	       for(int k = 0; k < len; k++) {
	           if(k > 0) sb.append(' ');
	           sb.append(parts[i+k]);
	       }
	       
	       result[i] = sb.toString();
	    }
	    
	    return Arrays.asList(result);
		
	}
	
}
