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
package ai.idylnlp.model.nlp.enrichment;

import java.io.IOException;
import java.util.Map;

/**
 * Performs entity enrichment.
 * @author Mountain Fog, Inc.
 * 
 */
public interface Enricher {
	
	/**
	 * Enrich an entity.
	 * @param entityUri The URI of the entity.
	 * @param maxEnrichments The maximum number of enrichments to return.
	 * @return A map of enrichment facts.
	 * @throws IOException Thrown if the entity enrichment predicate meanings cannot be read.
	 */
	public Map<String, String> enrich(String entityUri, int maxEnrichments) throws IOException;
	
	/**
	 * Close the data source used for enrichment. Some data sources
	 * may require closing after data retrieval is complete.
	 */
	public void close();
	
}
