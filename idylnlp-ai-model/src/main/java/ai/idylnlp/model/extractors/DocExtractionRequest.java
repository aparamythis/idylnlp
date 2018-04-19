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

import java.io.FileInputStream;

/**
 * A request to extract sentences from a Microsoft Word document (.doc file).
 * @author Mountain Fog, Inc.
 *
 */
public class DocExtractionRequest extends TextExtractionRequest {

	private static final long serialVersionUID = 3643465805634796399L;
	
	private FileInputStream fileInputStream;
	
	/**
	 * Creates a Microsoft Word document sentence extraction request.
	 * @param fileInputStream The FileInputStream containing the document.
	 */
	public DocExtractionRequest(FileInputStream fileInputStream) {
		
		this.fileInputStream = fileInputStream;
		
	}

	/**
	 * Gets the FileInputStream containing the document.
	 * @return The FileInputStream containing the document.
	 */
	public FileInputStream getFileInputStream() {
		return fileInputStream;
	}

}
