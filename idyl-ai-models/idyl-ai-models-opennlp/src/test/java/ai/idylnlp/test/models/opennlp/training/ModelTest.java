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
package ai.idylnlp.test.models.opennlp.training;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;

import com.mtnfog.entity.Entity;
import ai.idylnlp.opennlp.custom.model.DictionaryModel;
import ai.idylnlp.opennlp.custom.modelloader.LocalModelLoader;
import com.neovisionaries.i18n.LanguageCode;

import ai.idylnlp.model.ModelValidator;
import ai.idylnlp.model.exceptions.ModelLoaderException;
import ai.idylnlp.model.exceptions.ValidationException;
import ai.idylnlp.model.manifest.StandardModelManifest;
import ai.idylnlp.model.nlp.ConfidenceFilter;
import ai.idylnlp.model.nlp.EntityExtractionRequest;
import ai.idylnlp.model.nlp.EntityExtractionResponse;
import ai.idylnlp.model.nlp.EntityRecognizer;
import ai.idylnlp.nlp.filters.confidence.HeuristicConfidenceFilter;
import ai.idylnlp.nlp.filters.confidence.serializers.LocalConfidenceFilterSerializer;
import ai.idylnlp.nlp.recognizer.OpenNLPEntityRecognizer;
import ai.idylnlp.nlp.recognizer.configuration.OpenNLPEntityRecognizerConfiguration;
import ai.idylnlp.nlp.recognizer.configuration.OpenNLPEntityRecognizerConfiguration.Builder;
import ai.idylnlp.pipeline.IdylPipeline;
import opennlp.tools.namefind.TokenNameFinderModel;

public class ModelTest {

	private static final Logger LOGGER = LogManager.getLogger(ModelTest.class);
	
	private static final String MODEL_DIRECTORY = "/home/ubuntu/";
	private static final String MODEL_FILENAME = "model.bin";
	
	private static final LocalConfidenceFilterSerializer serializer = new LocalConfidenceFilterSerializer();
	private static final ConfidenceFilter confidenceFilter = new HeuristicConfidenceFilter(serializer);
	
	public static void main(String args[]) throws ModelLoaderException, ValidationException {
	
		ModelValidator modelValidator = Mockito.mock(ModelValidator.class);
		
		when(modelValidator.validateVersion(any(String.class))).thenReturn(true);
		
		LocalModelLoader<TokenNameFinderModel> entityModelLoader = new LocalModelLoader<TokenNameFinderModel>(modelValidator, MODEL_DIRECTORY);
		LocalModelLoader<DictionaryModel> dictionaryModelLoader = new LocalModelLoader<DictionaryModel>(modelValidator, MODEL_DIRECTORY);
		
		Set<StandardModelManifest> modelManifests = new HashSet<StandardModelManifest>();
		modelManifests.add(new StandardModelManifest.ModelManifestBuilder(UUID.randomUUID().toString(), "name", MODEL_FILENAME, LanguageCode.en, "idylami589012347", "person", "1.0.0", "").build());
		
		Map<LanguageCode, Set<StandardModelManifest>> languages = new HashMap<>();
		languages.put(LanguageCode.en, modelManifests);
		
		Map<String, Map<LanguageCode, Set<StandardModelManifest>>> models = new HashMap<>();
		models.put("person", languages);
		
		OpenNLPEntityRecognizerConfiguration config = new Builder()
			.withEntityModelLoader(entityModelLoader)
			.withDictionaryFinderModelLoader(dictionaryModelLoader)
			.withConfidenceFilter(confidenceFilter)
			.withEntityModels(models)
			.build();
		
		Set<String> typees = new HashSet<String>();
		typees.add("person");
		
		EntityRecognizer entityRecognizer = new OpenNLPEntityRecognizer(config);
		
		IdylPipeline.IdylPipelineBuilder pipelineBuilder = new IdylPipeline.IdylPipelineBuilder();
		pipelineBuilder.setEntityRecognizers(Arrays.asList(entityRecognizer));
		
		IdylPipeline pipeline = pipelineBuilder.build();
				
		String input = "George Washington was a president of the United States.";
		LOGGER.info("Input text: " + input);
		
		String[] text = input.split(" ");
		
		EntityExtractionRequest request = new EntityExtractionRequest(text);
		request.setConfidenceThreshold(0);
		
		EntityExtractionResponse response = pipeline.run(request);
		
		LOGGER.info("Entity extraction took " + response.getExtractionTime() + " ns.");
		LOGGER.info("Extracted entities: " + response.getEntities().size());
		
		for(Entity entity : response.getEntities()) {			
			LOGGER.info("Entity: " + entity.toString());			
		}
		
	}
	
}
