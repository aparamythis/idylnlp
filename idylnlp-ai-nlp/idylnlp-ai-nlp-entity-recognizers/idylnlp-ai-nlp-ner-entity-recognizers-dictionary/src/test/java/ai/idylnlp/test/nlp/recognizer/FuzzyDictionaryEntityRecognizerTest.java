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
package ai.idylnlp.test.nlp.recognizer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import ai.idylnlp.model.entity.Entity;
import ai.idylnlp.model.exceptions.EntityFinderException;
import ai.idylnlp.model.nlp.EntityExtractionRequest;
import ai.idylnlp.model.nlp.EntityExtractionResponse;
import ai.idylnlp.nlp.utils.distance.LevenshteinDistance;

import ai.idylnlp.nlp.recognizer.FuzzyDictionaryEntityRecognizer;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;

public class FuzzyDictionaryEntityRecognizerTest {
	
	private static final Logger LOGGER = LogManager.getLogger(FuzzyDictionaryEntityRecognizerTest.class);
	
	private File tempDictionary;
	
	@Before
	public void before() throws IOException {
		
	    tempDictionary = File.createTempFile("test-dictionary", ".dic");
	    tempDictionary.deleteOnExit();

	    List<String> lines = new ArrayList<String>();
	    
	    lines.add("# This is a comment.");
	    lines.add("Pontiac");
	    lines.add("Chevrolet");
	    lines.add("Ford");
	    
	    FileUtils.writeLines(tempDictionary, lines);
		
	}
	
	@Test
	public void test() throws EntityFinderException, IOException {

		final int maxDistance = 3;
		
		Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
		
		FuzzyDictionaryEntityRecognizer recognizer = new FuzzyDictionaryEntityRecognizer(tempDictionary, maxDistance, "city", tokenizer, LevenshteinDistance.INSTANCE());
		
		String input = "He was driving a blue pontac.";
		String[] text = input.split(" ");
		
		EntityExtractionRequest request = new EntityExtractionRequest(text);
		
		EntityExtractionResponse response = recognizer.extractEntities(request);
		
		assertEquals(1, response.getEntities().size());
				
		Entity entity = response.getEntities().iterator().next();
		
		assertEquals("Pontiac", entity.getText());
		assertEquals(new Span(5, 6).toString(), entity.getSpan().toString());
		
		// Show the response as JSON.
		Gson gson = new Gson();
		String json = gson.toJson(response);
		
		LOGGER.info(json);
		
	}
	
}
