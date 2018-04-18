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

import com.mtnfog.entity.Entity;

import ai.idylnlp.model.exceptions.DisambiguationException;

/**
 * Implement this interface to create custom Idyl disambiguators.
 * @author Mountain Fog, Inc.
 *
 */
@FunctionalInterface
public interface Disambiguator {

	/**
	 * Disambiguate an entity from known entities.
	 * @param input The text the entity was extracted from.
	 * @param entity The text of the entity.
	 * @return The URI of the disambiguated entity. If the URI cannot be disambiguated the code will be <code>null</code>.
	 * @throws DisambiguationException Thrown when an error occurs during disambiguation.
	 */
	public String disambiguate(String input, Entity entity) throws DisambiguationException;
	
}
