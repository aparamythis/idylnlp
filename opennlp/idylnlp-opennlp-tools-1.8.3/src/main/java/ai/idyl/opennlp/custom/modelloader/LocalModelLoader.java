package ai.idylnlp.opennlp.custom.modelloader;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.idylnlp.model.ModelValidator;

import opennlp.tools.util.model.BaseModel;

/**
 * A model loader for using models from the local file system.
 * @author Mountain Fog, Inc.
 *
 */
public final class LocalModelLoader<T extends BaseModel> extends ModelLoader<T> {

	private static final Logger LOGGER = LogManager.getLogger(LocalModelLoader.class);			
	
	/**
	 * Creates a local model loader.
	 * @param modelDirectory The directory on the local file system that contains the models.
	 */
	public LocalModelLoader(ModelValidator modelValidator, String modelDirectory) {
		
		super(modelValidator);
		
		if(!modelDirectory.endsWith(File.separator)) {
			modelDirectory = modelDirectory + File.separator;
		}
		
		LOGGER.info("Using local model loader directory {}", modelDirectory);
		
		super.setModelDirectory(modelDirectory);
			
	}
	
}