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
package ai.idylnlp.model.nlp.disambiguation;

/**
 * Represents a candidate disambiguation for an entity.
 * A candidate contains a URI, a description, and a label
 * that is a summary of the candidate.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public class DisambiguationCandidate {

	private String uri;
	private String description;
	private String label;
	
	/**
	 * Create a disambiguation candidate.
	 * @param uri The URI of the matched entity.
	 * @param description The description of the matched entity.
	 * @param label The label of the matched entity.
	 */
	public DisambiguationCandidate(String uri, String description, String label) {
		
		this.uri = uri;
		this.description = description;
		this.label = label;
		
	}

	/**
	 * Gets the matched entity's URI.
	 * @return The matched entity's URI.
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Gets the matched entity's description.
	 * @return The matched entity's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the matched entity's label.
	 * @return The matched entity's label.
	 */
	public String getLabel() {
		return label;
	}

}
