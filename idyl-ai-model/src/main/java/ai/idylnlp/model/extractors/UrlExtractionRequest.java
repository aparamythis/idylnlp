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
package ai.idylnlp.model.extractors;

/**
 * A request to extract sentences from a URL.
 * @author Mountain Fog, Inc.
 *
 */
public class UrlExtractionRequest extends TextExtractionRequest {
	
	private static final long serialVersionUID = -7494678513795383473L;
	
	private String url;

	/**
	 * Creates a URL sentence extraction request.
	 * @param url The URL.
	 */
	public UrlExtractionRequest(String url) {
			
		this.url = url;
		
	}

	/**
	 * Gets the URL.
	 * @return The URL.
	 */
	public String getUrl() {
		return url;
	}

}
