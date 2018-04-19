package ai.idylnlp.opennlp.custom.features;

import java.util.Map;

import org.w3c.dom.Element;

import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.FeatureGeneratorResourceProvider;
import opennlp.tools.util.featuregen.GeneratorFactory.XmlFeatureGeneratorFactory;

/**
 * Factory for {@link SpecialCharacterFeatureGenerator}.
 * 
 * @author Mountain Fog, Inc.
 * @see IDYLSDK-489
 *
 */
public class SpecialCharacterFeatureGeneratorFactory implements XmlFeatureGeneratorFactory {

	private static final String ELEMENT_NAME = "specchar";

	@Override
	public AdaptiveFeatureGenerator create(Element generatorElement, FeatureGeneratorResourceProvider resourceManager)
			throws InvalidFormatException {
		
		return new SpecialCharacterFeatureGenerator();
		
	}

	public static void register(Map<String, XmlFeatureGeneratorFactory> factoryMap) {
		factoryMap.put(ELEMENT_NAME, new SpecialCharacterFeatureGeneratorFactory());
	}
	
}