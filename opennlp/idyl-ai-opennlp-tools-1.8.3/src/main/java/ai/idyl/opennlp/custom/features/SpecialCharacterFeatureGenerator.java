package ai.idylnlp.opennlp.custom.features;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;

/**
 * Generates features for tokens containing hyphens.
 * 
 * @author Mountain Fog, Inc.
 * @see IDYLSDK-489
 *
 */
public class SpecialCharacterFeatureGenerator implements AdaptiveFeatureGenerator {

	private Pattern p;
	
	public SpecialCharacterFeatureGenerator() {
		
		p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		
	}
	
	@Override
	public void createFeatures(List<String> features, String[] tokens, int index, String[] previousOutcomes) {

		Matcher m = p.matcher(tokens[index]);
		boolean containsSpecialCharacters = m.find();

		if(containsSpecialCharacters) {
		
			features.add("specchar=" + tokens[index]);
			
		}

	}

}