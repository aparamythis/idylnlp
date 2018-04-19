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
package ai.idylnlp.nlp.recognizer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.idylnlp.model.entity.Entity;
import ai.idylnlp.model.exceptions.EntityFinderException;
import ai.idylnlp.model.nlp.EntityExtractionRequest;
import ai.idylnlp.model.nlp.EntityExtractionResponse;
import ai.idylnlp.model.nlp.EntityRecognizer;
import ai.idylnlp.model.nlp.strings.Distance;

import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;
import opennlp.tools.util.StringList;

/**
 * Implementation of {@link EntityRecognizer} that uses a Levenshtein distance
 * algorithm to determine word similarity.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public class FuzzyDictionaryEntityRecognizer implements EntityRecognizer {

	private static final Logger LOGGER = LogManager.getLogger(FuzzyDictionaryEntityRecognizer.class);
			
	private Dictionary dictionary;
	private String type;
	private int maxDistance;
	private Tokenizer tokenizer;
	private Distance distance;
	
	/**
	 * Creates a new fuzzy dictionary entity recognizer.
	 * @param dictionary The {@link Dictionary dictionary}.
	 * @param maxDistance The maximum distance for determining similarity.
	 * @param type The type of entity being extracted. There is a one-to-one relationship
	 * between dictionary and entity type.
	 * @param tokenizer The {@link Tokenizer}.
	 * @param distance The {@link Distance} implementation to calculate String distance.
	 */
	public FuzzyDictionaryEntityRecognizer(Dictionary dictionary, int maxDistance, String type, Tokenizer tokenizer, Distance distance) {
		
		this.dictionary = dictionary;
		this.maxDistance = maxDistance;
		this.type = type;
		this.tokenizer = tokenizer;
		this.distance = distance;
		
	}
	
	/**
	 * Creates a new fuzzy dictionary entity recognizer.
	 * @param dictionaryFile The dictionary {@link File file}.
	 * @param maxDistance The maximum distance for determining similarity.
	 * @param type The type of entity being extracted. There is a one-to-one relationship
	 * between dictionary and entity type.
	 * @param tokenizer The {@link Tokenizer}.
	 * @param distance The {@link Distance} implementation to calculate String distance.
	 * @throws IOException Thrown if the dictionary file cannot be read.
	 */
	public FuzzyDictionaryEntityRecognizer(File dictionaryFile, int maxDistance, String type, Tokenizer tokenizer, Distance distance) throws IOException {

		this.type = type;
		this.maxDistance = maxDistance;
		this.tokenizer = tokenizer;
		this.distance = distance;
		
		this.dictionary = new Dictionary();
		
		// Read in the dictionary.		
		List<String> lines = FileUtils.readLines(dictionaryFile);
		
		for(String line : lines) {
			
			// Ignore commented lines.
			if(!line.startsWith("#")) {
			
				String entry[] = line.split("=");
		
				dictionary.put(new StringList(entry));
			
			}
			
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityExtractionResponse extractEntities(EntityExtractionRequest request) throws EntityFinderException {

		LOGGER.trace("Finding entities with the dictionary entity recognizer.");
	
		Set<Entity> entities = new LinkedHashSet<Entity>();
		
		long startTime = System.currentTimeMillis();
			
		final String text = StringUtils.join(request.getText(), " ");
				
		// tokenize the text into the required OpenNLP format 
        String[] tokens = tokenizer.tokenize(text); 
        
        //the values used in these Spans are string character offsets of each token from the sentence beginning 
        Span[] tokenPositionsWithinSentence = tokenizer.tokenizePos(text); 
        
        int x = 0;
        
		for(String token : tokens) {
			
			for(String dicWord : dictionary.asStringSet()) {
		
				double d = distance.calculate(token, dicWord);
				
				if(d <= maxDistance) {
			
					Span span = new Span(x, x+1);
					Span characterSpan = tokenPositionsWithinSentence[x];
					
					// Make an entity.
					Entity entity = new Entity();
					entity.setText(dicWord);
					entity.setType(type);
					entity.setConfidence(100.0);
					entity.setSpan(new ai.idylnlp.model.entity.Span(span.getStart(), span.getEnd()));
					entity.setContext(request.getContext());
					entity.setExtractionDate(System.currentTimeMillis());
					
					// Add this entity to the list.
					entities.add(entity);
					
				}
					
			}
			
			x++;
			
		}
		
		long extractionTime = (System.currentTimeMillis() - startTime);
		
		EntityExtractionResponse response = new EntityExtractionResponse(entities, extractionTime, true);
		
		return response;
		
	}

}
