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
package ai.idylnlp.test.nlp.utils.ngrams;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import ai.idylnlp.nlp.utils.ngrams.NgramUtils;

public class NgramUtilsTest {
	
	private static final Logger LOGGER = LogManager.getLogger(NgramUtilsTest.class);

	@Test
	public void test1() {
		
		Collection<String> ngrams = NgramUtils.getNgrams("George Washington was president.", 2, true);
		
		for(String ngram : ngrams) {
			LOGGER.info(ngram);
		}
		
		assertEquals(3, ngrams.size());
		
		assertTrue(ngrams.contains("George Washington"));
		assertTrue(ngrams.contains("Washington was"));
		assertTrue(ngrams.contains("was president"));
		
	}
	
}
