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
 * A request to extract sentences from an XML document.
 * @author Mountain Fog, Inc.
 *
 */
public class XmlExtractionRequest extends TextExtractionRequest {
	
	private static final long serialVersionUID = -7494678513795383473L;
	
	private String xml;
	private String xPath;
	
	/**
	 * Creates a request to extract sentences from an XML document
	 * @param xml The XML document.
	 * @param xPath An XPath expression that defines the text to extract.
	 */
	public XmlExtractionRequest(String xml, String xPath) {
			
		this.xml = xml;
		this.xPath = xPath;
		
	}

	/**
	 * Gets the XML.
	 * @return The XML.
	 */
	public String getXml() {
		return xml;
	}

	/**
	 * Gets the XPath expression
	 * @return The XPath exprssion.
	 */
	public String getxPath() {
		return xPath;
	}

}
