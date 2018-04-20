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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.idylnlp.model.entity.Entity;

import ai.idylnlp.model.exceptions.EntityFinderException;
import ai.idylnlp.model.exceptions.ModelLoaderException;
import ai.idylnlp.model.nlp.DuplicateEntityStrategy;
import ai.idylnlp.model.nlp.EntityComparator;
import ai.idylnlp.model.nlp.EntityExtractionRequest;
import ai.idylnlp.model.nlp.EntityExtractionResponse;
import ai.idylnlp.model.nlp.EntityRecognizer;
import ai.idylnlp.model.nlp.EntitySanitizer;
import ai.idylnlp.model.nlp.pipeline.Pipeline;
import ai.idylnlp.model.stats.StatsReporter;

/**
 * The NLP and NER pipeline.
 *  
 * @author Mountain Fog, Inc.
 *
 */
public class IdylPipeline implements Pipeline {		
	
	private static final Logger LOGGER = LogManager.getLogger(IdylPipeline.class);		
	
	private static Map<String, IdylPipeline> namedPipelines = new HashMap<String, IdylPipeline>();

	private String name;
	private List<EntityRecognizer> entityRecognizers;
	private List<EntitySanitizer> entitySanitizers;
	private StatsReporter statsReporter;
	private DuplicateEntityStrategy duplicateEntityStrategy = DuplicateEntityStrategy.USE_HIGHEST_CONFIDENCE;
	
	private IdylPipeline(String name,
			List<EntityRecognizer> entityRecognizers,
			List<EntitySanitizer> entitySanitizers,
			StatsReporter statsReporter,
			DuplicateEntityStrategy duplicateEntityStrategy) {
		
		this.entityRecognizers = entityRecognizers;
		this.entitySanitizers = entitySanitizers;
		this.statsReporter = statsReporter;
		this.duplicateEntityStrategy = duplicateEntityStrategy;
		
	}
	
	/**
	 * Gets a named pipeline.
	 * @param name The name of the pipeline.
	 * @return An {@link IdylPipeline} or <code>null</code> if there is no
	 * pipeline having the given name.
	 */
	public IdylPipeline getIdylPipeline(String name) {
		
		return namedPipelines.get(name);
		
	}
		
	public static class IdylPipelineBuilder {
		
		private List<EntityRecognizer> entityRecognizers;	
		private List<EntitySanitizer> entitySanitizers;
		private StatsReporter statsReporter;
		private DuplicateEntityStrategy duplicateEntityStrategy;
		
		/**
		 * Sets the entity recognizers for the pipeline.
		 * @param entityRecognizers The entity {@link EntityRecognizer recognizers}.
		 * @return The {@link IdylPipeline pipeline} so calls can be chained.
		 */
		public IdylPipelineBuilder setEntityRecognizers(List<EntityRecognizer> entityRecognizers) {
			this.entityRecognizers = entityRecognizers;
			return this;
		}
		
		/**
		 * Sets the entity sanitizers for the pipeline.
		 * @param entitySanitizers The entity {@link EntitySanitzer sanitzers}.
		 * @return The {@link IdylPipeline pipeline} so calls can be chained.
		 */
		public IdylPipelineBuilder setEntitySanitizers(List<EntitySanitizer> entitySanitizers) {
			this.entitySanitizers = entitySanitizers;
			return this;
		}
		
		/**
		 * Sets the {@link StatsReporter}.
		 * @param statsReporter The {@link StatsReporter}.
		 * @return The {@link IdylPipeline pipeline} so calls can be chained.
		 */
		public IdylPipelineBuilder setStatsReporter(StatsReporter statsReporter) {
			this.statsReporter = statsReporter;
			return this;
		}

		/**
		 * Sets the duplicate entity strategy for the pipeline.
		 * @param duplicateEntityStrategy The duplicate entity {@link DuplicateEntityStrategy strategy}.
		 * @return The {@link IdylPipeline pipeline} so calls can be chained.
		 */
		public IdylPipelineBuilder setDuplicateEntityStrategy(DuplicateEntityStrategy duplicateEntityStrategy) {
			this.duplicateEntityStrategy = duplicateEntityStrategy;
			return this;
		}
				
		/**
		 * Builds the default Idyl pipeline and stores it as a named pipeline.
		 * @return A {@link IdylPipeline pipeline}.
		 */
		public IdylPipeline build() {
			
			return build(DEFAULT_PIPELINE_NAME);
			
		}
		
		/**
		 * Builds the Idyl pipeline and stores it as a named pipeline.
		 * @return A {@link IdylPipeline pipeline}.
		 */
		public IdylPipeline build(String name) {
			
			if(entityRecognizers == null) {
				entityRecognizers = new ArrayList<EntityRecognizer>();
			}
			
			if(entitySanitizers == null) {
				entitySanitizers = new ArrayList<EntitySanitizer>();
			}
														
			IdylPipeline pipeline = new IdylPipeline(name, entityRecognizers, entitySanitizers,
					statsReporter, duplicateEntityStrategy);
			
			// Save this pipeline as a named pipeline.
			namedPipelines.put(name, pipeline);
			
			return pipeline;
			
		}
		
	}

	/**
	 * Run all components of the pipeline on the input.
	 * @param request An {@link EntityExtractionRequest} object.
	 * @return An {@link EntityExtractionResponse response}. Returns
	 * <code>null</code> if an error occurred during the execution.
	 */
	@Override
	public EntityExtractionResponse run(EntityExtractionRequest request) {
		
		Set<Entity> entities = new HashSet<Entity>();
		
		boolean successful = true;
		
		long extractionTime = 0;
		
		try { 													
			
			// Extract the entities using all of the NER in the list.
			for(EntityRecognizer entityRecognizer : entityRecognizers) {
			
				LOGGER.debug("Processing input with entity recognizer {}.", entityRecognizer.toString());
				
				EntityExtractionResponse response = entityRecognizer.extractEntities(request);	
				entities.addAll(response.getEntities());
				extractionTime += response.getExtractionTime();
				
			}
			
			if(statsReporter != null) {
				// Increment the count of entity extraction requests.
				statsReporter.increment(StatsReporter.EXTRACTION_REQUESTS, entities.size());
			}
			
			// Sanitize the entities.
			for(EntitySanitizer sanitizer : entitySanitizers) {			
				entities = sanitizer.sanitizeEntities(entities);				
			}			
			
			// Is the duplicate entity strategy overridden for this request?
			DuplicateEntityStrategy entityExtractionRequestDuplicateEntityStrategy = request.getDuplicateEntityStrategy();
			
			if(entityExtractionRequestDuplicateEntityStrategy != null) {
				duplicateEntityStrategy = entityExtractionRequestDuplicateEntityStrategy;
			}
			
			// Handle the duplicate entities per the strategy.
			if(duplicateEntityStrategy == DuplicateEntityStrategy.USE_HIGHEST_CONFIDENCE) {
			
				// Remove duplicate entities having a lower confidence.
				entities = IdylPipelineUtils.removeDuplicateEntities(entities);
			
			}
			
			// Sort the entities before returning.
			entities = EntityComparator.sort(entities, request.getOrder());
					
		} catch (ModelLoaderException | EntityFinderException ex) {
			
			LOGGER.error("Unable to process through the Idyl pipeline.", ex);
			
			// Return null on receipt of an error. This is here
			// because otherwise an incomplete list of enriched
			// entities could potentially be returned when an
			// exception is thrown.
			
			entities = null;
			
			successful = false;
			
		}
		
		// Return the disambiguated entities.
		return new EntityExtractionResponse(entities, extractionTime, successful);
		
	}
	
	/**
	 * Gets the name of the pipeline.
	 * @return
	 */
	public String getName() {
		return name;
	}
				
	/**
	 * Gets the entity recognizers.
	 * @return A list of entity {@link EntityRecognizer recognizers}.
	 */
	public List<EntityRecognizer> getEntityRecognizers() {
		return entityRecognizers;
	}
	
	/**
	 * Gets the entity sanitizers.
	 * @return A list of entity {@link EntitySanizer sanitizers}.
	 */
	public List<EntitySanitizer> getEntitySanitiziers() {
		return entitySanitizers;
	}

	/**
	 * Gets the {@link StatsReporter}.
	 * @return The {@link StatsReporter}.
	 */
	public StatsReporter getStatsReporter() {
		return statsReporter;
	}
	
	/**
	 * Gets the duplicate entity strategy.
	 * @return The duplicate entity {@link DuplicateEntityStrategy strategy}.
	 */
	public DuplicateEntityStrategy getDuplicateEntityStrategy() {
		return duplicateEntityStrategy;
	}
	
}
