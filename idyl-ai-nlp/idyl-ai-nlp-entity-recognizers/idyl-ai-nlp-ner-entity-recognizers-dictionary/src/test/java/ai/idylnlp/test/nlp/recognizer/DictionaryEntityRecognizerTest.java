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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import ai.idylnlp.model.entity.Entity;
import ai.idylnlp.model.exceptions.EntityFinderException;
import ai.idylnlp.model.nlp.EntityExtractionRequest;
import ai.idylnlp.model.nlp.EntityExtractionResponse;

import ai.idylnlp.nlp.recognizer.DictionaryEntityRecognizer;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;

public class DictionaryEntityRecognizerTest {
	
	private static final Logger LOGGER = LogManager.getLogger(DictionaryEntityRecognizerTest.class);
	
	private static final String MTNFOG_EN_DICT_MODEL = "mtnfog-en-drug-dict-test.bin";
	
	private File tempDictionary;
	
	@Before
	public void before() throws IOException {
		
	    tempDictionary = File.createTempFile("test-dictionary", ".dic");
	    tempDictionary.deleteOnExit();

	    List<String> lines = new ArrayList<String>();
	    
	    lines.add("# This is a comment.");
	    lines.add("The Matrix");
	    lines.add("The Matrix Reloaded");
	    lines.add("Speed Racer");
	    lines.add("Matrix");
	    
	    FileUtils.writeLines(tempDictionary, lines);
		
	}
	
	@Test
	public void test() throws EntityFinderException, IOException {

		Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
		
		DictionaryEntityRecognizer recognizer = new DictionaryEntityRecognizer(tempDictionary, "city", tokenizer);
		
		String input = "The Matrix was a good movie.";		
		String[] text = input.split(" ");
		
		EntityExtractionRequest request = new EntityExtractionRequest(text);
		
		EntityExtractionResponse response = recognizer.extractEntities(request);
		
		assertTrue(response.getEntities().size() > 0);
		
		for(Entity entity : response.getEntities()) {
			
			LOGGER.info("Entity: " + entity.getText());
			
		}
		
	}
	
}
