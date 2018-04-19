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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * A request to extract sentences from a PDF document.
 * @author Mountain Fog, Inc.
 *
 */
public class PdfExtractionRequest extends TextExtractionRequest {
	
	private static final long serialVersionUID = -1538918000519904121L;
	
	private byte[] document;
	
	/**
	 * Creates a Microsoft Word document sentence extraction request.
	 * @param document A byte array of the document.
	 */
	public PdfExtractionRequest(byte[] document) {
		
		this.document = document;
		
	}

	/**
	 * Gets the document as a ByteArrayInputStream.
	 * @return The document as a ByteArrayInputStream.
	 */
	public InputStream getDocumentAsInputStream() {
		return new ByteArrayInputStream(document);
	}

	/**
	 * Gets the document as a byte array.
	 * @return The document as a byte array.
	 */
	public byte[] getDocument() {
		return document;
	}

	/**
	 * Gets the document as a String.
	 * @return The document as a String.
	 * @throws UnsupportedEncodingException Thrown if the text is not UTF-8.
	 */
	public String getDocumentAsString() throws UnsupportedEncodingException {
		
		return new String(this.document, "UTF-8"); 
		
	}
	
}
