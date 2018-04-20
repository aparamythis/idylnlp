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
package ai.idylnlp.test.nlp.utils.distance;

import static org.junit.Assert.*;

import org.junit.Test;

import ai.idylnlp.nlp.utils.distance.CosineDistance;

public class CosineDistanceTest {

	@Test
	public void distance() {
		
		CosineDistance distance = new CosineDistance();
		
		double i = distance.calculate("test", "tst");
		
		assertEquals(1.0, i, 0);
		
	}
	
}