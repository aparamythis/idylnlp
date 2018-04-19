package ai.idylnlp.opennlp.custom.validators;

import ai.idylnlp.model.ModelValidator;
import ai.idylnlp.model.exceptions.ValidationException;

public class TrueValidator implements ModelValidator {

	@Override
	public boolean validateVersion(String creatorVersion) {
		return true;
	}

}