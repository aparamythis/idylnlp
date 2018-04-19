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
package ai.idylnlp.pipeline;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import ai.idylnlp.model.entity.Entity;

public class IdylPipelineUtils {

	/**
	 * Remove duplicate entities having a lower confidence.
	 * @param entities A set of {@link Entity} objects.
	 * @return A set of {@link Entity} objects without duplicate entities.
	 */
	public static Set<Entity> removeDuplicateEntities(Set<Entity> entities) {
		
		Set<Entity> removedDuplicateEntities = new LinkedHashSet<>();
		
		for(Entity entity : entities) {						
			
			Set<Entity> entitiesWithSameText = new HashSet<Entity>();
			
			// Is there another entity in the set that has this entity's text?
			for(Entity entity2 : entities) {
				
				if(entity.getText().equalsIgnoreCase(entity2.getText())) {
				
					entitiesWithSameText.add(entity2);
					
				}
				
			}
		
			// Should always be at least one (the same entity).
			if(entitiesWithSameText.size() == 1) {
				
				removedDuplicateEntities.addAll(entitiesWithSameText);
				
			} else {
				
				// Find the one with the highest confidence.
				double highestConfidence = 0;
				Entity entityWithHighestConfidence = null;
				
				for(Entity entity3 : entitiesWithSameText) {
					
					if(entity3.getConfidence() > highestConfidence) {
						
						highestConfidence = entity3.getConfidence();
						entityWithHighestConfidence = entity3;
						
					}
					
				}
				
				removedDuplicateEntities.add(entityWithHighestConfidence);
				
			}
			
		}
		
		return removedDuplicateEntities;
		
	}
	
}
