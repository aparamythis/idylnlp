package ai.idylnlp.opennlp.custom.utils;

import java.util.LinkedList;
import java.util.List;

import opennlp.tools.util.Span;

/**
 * Utility class for converting between OpenNLP's Span and Idyl NLP's Span.
 *
 * @author Mountain Fog, Inc.
 *
 */
public class SpansToSpans {

	private SpansToSpans() {

	}

	/**
	 * Converts an array of OpenNLP Spans to Idyl SDK Spans.
	 * @param spans An array of OpenNLP Spans.
	 * @return An array of Idyl SDK Spans.
	 */
	public static ai.idylnlp.model.nlp.Span[] toSpans(Span[] spans) {

		List<ai.idylnlp.model.nlp.Span> s = new LinkedList<ai.idylnlp.model.nlp.Span>();

		for(opennlp.tools.util.Span span : spans) {

			s.add(new ai.idylnlp.model.nlp.Span(span.getStart(), span.getEnd(), span.getType(), span.getProb()));

		}

		return s.toArray(new ai.idylnlp.model.nlp.Span[s.size()]);

	}

}
