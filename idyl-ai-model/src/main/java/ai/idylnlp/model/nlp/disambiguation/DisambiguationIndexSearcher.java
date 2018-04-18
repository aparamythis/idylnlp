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

import java.util.List;

import com.mtnfog.entity.Entity;

import ai.idylnlp.model.exceptions.DisambiguationException;

/**
 * Interface for creating disambiguation index searcher. Implement this
 * interface to provide disambiguation functionality when
 * the disambiguation data is stored in a custom format. 
 * 
 * @author Mountain Fog, Inc.
 *
 */
@FunctionalInterface
public interface DisambiguationIndexSearcher {

	/**
	 * Search the disambiguation index to find potential disambiguations.
	 * @param entity The entity to be disambiguated.
	 * @param maxResults The maximum number of potential disambiguations to return.
	 * @return A list of {@link DisambiguationCandidate} objects are candidate disambiguations
	 * for the input entity.
	 * @throws DisambiguationException Thrown when disambiguation fails. Disambiguation
	 * may fail when the index cannot be read due to bad path or invalid permissions to
	 * the index.
	 */
	public List<DisambiguationCandidate> search(Entity entity, int maxResults) throws DisambiguationException;
	
}
