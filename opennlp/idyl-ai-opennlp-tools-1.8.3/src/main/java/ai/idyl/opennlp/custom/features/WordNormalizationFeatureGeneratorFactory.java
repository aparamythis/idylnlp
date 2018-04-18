package ai.idylnlp.opennlp.custom.features;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import ai.idylnlp.model.ModelValidator;
import ai.idylnlp.model.manifest.ModelManifest;
import ai.idylnlp.model.manifest.ModelManifestUtils;
import ai.idylnlp.model.manifest.StandardModelManifest;
import ai.idylnlp.model.nlp.lemma.Lemmatizer;
import ai.idylnlp.model.nlp.pos.PartsOfSpeechTagger;
import ai.idylnlp.opennlp.custom.nlp.lemmatization.DefaultLemmatizer;
import ai.idylnlp.opennlp.custom.nlp.pos.DefaultPartsOfSpeechTagger;
import ai.idylnlp.opennlp.custom.validators.TrueValidator;

import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.FeatureGeneratorResourceProvider;
import opennlp.tools.util.featuregen.GeneratorFactory.XmlFeatureGeneratorFactory;

/**
 * Factory for {@link WordNormalizationFeatureGenerator}.
 * 
 * @author Mountain Fog, Inc.
 * @see IDYLSDK-490
 *
 */
public class WordNormalizationFeatureGeneratorFactory implements XmlFeatureGeneratorFactory {

	private static final String ELEMENT_NAME = "wordnormalization";

	private Lemmatizer modelLemmatizer;
	private Lemmatizer dictonaryLemmatizer;
	private PartsOfSpeechTagger partsOfSpeechTagger;
	private ModelValidator validator;
	
	@Override
	public AdaptiveFeatureGenerator create(Element generatorElement, FeatureGeneratorResourceProvider resourceManager)
			throws InvalidFormatException {
		
		validator = new TrueValidator();
		
		try {
		
			loadLemmatizers(generatorElement);
			loadPartsOfSpeechTagger(generatorElement);
		
			return new WordNormalizationFeatureGenerator(partsOfSpeechTagger, modelLemmatizer, dictonaryLemmatizer);
			
		} catch (Exception ex) {
			
			throw new InvalidFormatException("Unable to load lemmatizer or parts-of-speech model.", ex);
			
		}
		
	}

	public static void register(Map<String, XmlFeatureGeneratorFactory> factoryMap) {
		factoryMap.put(ELEMENT_NAME, new WordNormalizationFeatureGeneratorFactory());
	}
	
	private void loadLemmatizers(Element generatorElement) throws Exception {
		
		final String lemmaModelPath = generatorElement.getAttribute("modelPath");
		final String lemmaModelManifest = generatorElement.getAttribute("modelManifest");
		final String lemmaDictionary = generatorElement.getAttribute("dictionary");
		
		ModelManifest modelManifest = ModelManifestUtils.readManifest(lemmaModelPath + lemmaModelManifest);
		
		StandardModelManifest standardModelManifest = (StandardModelManifest) modelManifest;
		
		if(StringUtils.isNotEmpty(lemmaModelPath) && StringUtils.isNotEmpty(lemmaModelManifest)) {
			
			modelLemmatizer = new DefaultLemmatizer(lemmaModelPath, standardModelManifest, validator);
			
		}
		
		if(StringUtils.isNotEmpty(lemmaDictionary)) {
			dictonaryLemmatizer = new DefaultLemmatizer(lemmaDictionary);
		}
		
	}
	
	private void loadPartsOfSpeechTagger(Element generatorElement) throws Exception {
		
		final String posModelpath = generatorElement.getAttribute("modelPath");
		final String posModelManfiest = generatorElement.getAttribute("modelManifest");
		
		ModelManifest modelManifest = ModelManifestUtils.readManifest(posModelpath + posModelManfiest);
		
		StandardModelManifest standardModelManifest = (StandardModelManifest) modelManifest;
		
		// TODO: Get a Validator in here. See: https://cm01.mtnfog.com/jira/browse/IDYLSDK-572
		partsOfSpeechTagger = new DefaultPartsOfSpeechTagger(posModelpath, standardModelManifest, validator);
		
	}
	
}