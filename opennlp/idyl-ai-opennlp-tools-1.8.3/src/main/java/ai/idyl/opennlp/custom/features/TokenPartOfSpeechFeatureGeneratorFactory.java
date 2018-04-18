package ai.idylnlp.opennlp.custom.features;

import java.util.Map;

import org.w3c.dom.Element;

import ai.idylnlp.model.manifest.ModelManifest;
import ai.idylnlp.model.manifest.ModelManifestUtils;
import ai.idylnlp.model.manifest.StandardModelManifest;
import ai.idylnlp.model.nlp.pos.PartsOfSpeechTagger;
import ai.idylnlp.opennlp.custom.nlp.pos.DefaultPartsOfSpeechTagger;
import ai.idylnlp.opennlp.custom.validators.TrueValidator;

import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.FeatureGeneratorResourceProvider;
import opennlp.tools.util.featuregen.GeneratorFactory.XmlFeatureGeneratorFactory;

/**
 * Factory for {@link TokenPartOfSpeechFeatureGenerator}.
 * 
 * @author Mountain Fog, Inc.
 * @see IDYLSDK-493
 *
 */
public class TokenPartOfSpeechFeatureGeneratorFactory implements XmlFeatureGeneratorFactory {

	private static final String ELEMENT_NAME = "tokenpos";

	@Override
	public AdaptiveFeatureGenerator create(Element generatorElement, FeatureGeneratorResourceProvider resourceManager)
			throws InvalidFormatException {
		
		final String modelPath = generatorElement.getAttribute("modelPath");
		final String modelManifestPath = generatorElement.getAttribute("modelManifest");
		
		try {
		
			ModelManifest modelManifest = ModelManifestUtils.readManifest(modelPath + modelManifestPath);
			
			StandardModelManifest standardModelManifest = (StandardModelManifest) modelManifest;
		
			PartsOfSpeechTagger tagger = new DefaultPartsOfSpeechTagger(modelPath, standardModelManifest, new TrueValidator());
		
			return new TokenPartOfSpeechFeatureGenerator(tagger);
			
		} catch (Exception ex) {
			
			throw new InvalidFormatException("Unable to load the parts-of-speech model.", ex);
			
		}
		
	}

	public static void register(Map<String, XmlFeatureGeneratorFactory> factoryMap) {
		factoryMap.put(ELEMENT_NAME, new TokenPartOfSpeechFeatureGeneratorFactory());
	}
	
}