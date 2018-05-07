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
import ai.idylnlp.model.nlp.ner.EntityExtractionRequest;
import ai.idylnlp.model.nlp.ner.EntityExtractionResponse;
import ai.idylnlp.model.nlp.ner.EntityRecognizer;

import com.neovisionaries.i18n.LanguageCode;

import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.namefind.DictionaryNameFinder;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;
import opennlp.tools.util.StringList;

/**
 * Implementation of {@link EntityRecognizer} that uses a dictionary.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public class DictionaryEntityRecognizer implements EntityRecognizer {

	private static final Logger LOGGER = LogManager.getLogger(DictionaryEntityRecognizer.class);
	
	private Dictionary dictionary;
	private String type;
	private Tokenizer tokenizer;
	
	/**
	 * Creates a new dictionary entity recognizer.
	 * @param dictionary The {@link Dictionary dictionary}.
	 * @param type The type of entity being extracted. There is a one-to-one relationship
	 * between dictionary and entity type.
	 * @param tokenizer The {@link Tokenizer}.
	 */
	public DictionaryEntityRecognizer(Dictionary dictionary, String type, Tokenizer tokenizer) {
		
		this.dictionary = dictionary;
		this.type = type;
		this.tokenizer = tokenizer;
		
	}
	
	/**
	 * Creates a new dictionary entity recognizer.
	 * @param dictionaryFile The {@link File file} defining the dictionary.
	 * @param type The type of entity being extracted. There is a one-to-one relationship
	 * between dictionary and entity type.
	 * @param tokenizer The {@link Tokenizer}.
	 * @throws IOException Thrown if the dictionary file cannot be accessed.
	 */
	public DictionaryEntityRecognizer(File dictionaryFile, String type, Tokenizer tokenizer) throws IOException {

		this.type = type;
		this.dictionary = new Dictionary();
		this.tokenizer = tokenizer;
		
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
		
		try {

			final String text = StringUtils.join(request.getText(), " ");
			
			// tokenize the text into the required OpenNLP format 
            String[] tokens = tokenizer.tokenize(text); 
            
            //the values used in these Spans are string character offsets of each token from the sentence beginning 
            Span[] tokenPositionsWithinSentence = tokenizer.tokenizePos(text); 
            
            // find the location names in the tokenized text 
            // the values used in these Spans are NOT string character offsets, they are indices into the 'tokens' array
            DictionaryNameFinder dictionaryNER = new DictionaryNameFinder(dictionary, type); 
            Span names[] = dictionaryNER.find(tokens);
            
            //for each name that got found, create our corresponding occurrence 
            for (Span name : names) { 
 
                //find offsets relative to the start of the sentence 
                int beginningOfFirstWord = tokenPositionsWithinSentence[name.getStart()].getStart();
                
                // -1 because the high end of a Span is noninclusive 
                int endOfLastWord = tokenPositionsWithinSentence[name.getEnd() - 1].getEnd(); 
 
                //look back into the original input string to figure out what the text is that I got a hit on 
                String nameInDocument = text.substring(beginningOfFirstWord, endOfLastWord);                  
                
                // Create a new entity object.
				Entity entity = new Entity(nameInDocument, 100.0, type, LanguageCode.undefined.getAlpha3().toString());
				entity.setSpan(new ai.idylnlp.model.entity.Span(name.getStart(), name.getEnd()));
				entity.setContext(request.getContext());
				entity.setExtractionDate(System.currentTimeMillis());
				
				LOGGER.debug("Found entity with text: {}", nameInDocument);
				
				// Add the entity to the list.
				entities.add(entity);
				
				LOGGER.trace("Found entity [{}] as a {} with span {}.", nameInDocument, type, name.toString());

            }  
			
			long extractionTime = (System.currentTimeMillis() - startTime);
						
			EntityExtractionResponse response = new EntityExtractionResponse(entities, extractionTime, true);
			
			return response;
		
		} catch (Exception ex) {
			
			LOGGER.error("Unable to find entities with the DictionaryEntityRecognizer.", ex);
			
			throw new EntityFinderException("Unable to find entities with the DictionaryEntityRecognizer.", ex);
			
		}
		
	}

}
