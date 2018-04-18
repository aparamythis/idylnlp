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

import java.util.Iterator;

/**
 * An implementation of {@link Iterator} that produces N-Grams.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public class NgramIterator implements Iterator<String> {

	// See: http://stackoverflow.com/a/3656824
	
	private String[] words;
	private int pos = 0, n;

	public NgramIterator(String str, int n, boolean removePunctuation) {
		
		if(removePunctuation) {
			str = str.replaceAll("\\p{P}", "");
		}
		
		this.n = n;
		words = str.split(" ");
		
	}

	@Override
	public boolean hasNext() {
		return pos < words.length - n + 1;
	}

	@Override
	public String next() {
		
		StringBuilder sb = new StringBuilder();
	
		for (int i = pos; i < pos + n; i++) {
			sb.append((i > pos ? " " : "") + words[i]);
		}
		
		pos++;
		
		return sb.toString();
		
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
