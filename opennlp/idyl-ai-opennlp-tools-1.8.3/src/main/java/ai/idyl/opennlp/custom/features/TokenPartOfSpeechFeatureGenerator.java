package ai.idylnlp.opennlp.custom.features;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import ai.idylnlp.model.nlp.pos.PartsOfSpeechTagger;
import ai.idylnlp.model.nlp.pos.PartsOfSpeechToken;

import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;

/**
 * Generates features for tokens based on the token's part of speech.
 * 
 * @author Mountain Fog, Inc.
 * @see IDYLSDK-493
 *
 */
public class TokenPartOfSpeechFeatureGenerator implements AdaptiveFeatureGenerator {

	private static final String POS_PREFIX = "tpos";

	private PartsOfSpeechTagger tagger;
	private Map<String, String> tokPosMap;

	public TokenPartOfSpeechFeatureGenerator(PartsOfSpeechTagger tagger) {

		this.tagger = tagger;
		tokPosMap = new HashMap<String, String>();

	}

	@Override
	public void createFeatures(List<String> features, String[] tokens, int index, String[] previousOutcomes) {

		String[] postags = getPostags(tokens);
		features.add(POS_PREFIX + "=" + postags[index]);

	}

	private String[] getPostags(String[] tokens) {

		String text = StringUtils.join(tokens, " ");

		if (tokPosMap.containsKey(text)) {

			return tokPosMap.get(text).split(" ");

		} else {

			List<PartsOfSpeechToken> partsOfSpeechTokens = tagger.tag(tokens);
			String[] tags = PartsOfSpeechToken.getTokens(partsOfSpeechTokens);

			tokPosMap.put(text, StringUtils.join(tags, " "));

			return tags;

		}

	}

}