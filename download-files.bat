
mkdir idylnlp-ai-nlp/idylnlp-ai-nlp-pos-tagging/src/test/resources/models/
mkdir idylnlp-ai-nlp-language-detection/idylnlp-ai-nlp-language-detection-opennlp/src/main/resources/

REM Non-Apache licensed OpenNLP models.
wget http://opennlp.sourceforge.net/models-1.5/en-pos-maxent.bin -P idylnlp-ai-nlp/idylnlp-ai-nlp-pos-tagging/src/test/resources/models/
wget http://opennlp.sourceforge.net/models-1.5/en-sent.bin -P idylnlp-ai-nlp/idylnlp-ai-nlp-sentence-detection/src/test/resources/
wget http://opennlp.sourceforge.net/models-1.5/en-sent.bin -P idylnlp-ai-nlp/idylnlp-ai-nlp-entity-recognizers/idylnlp-ai-nlp-ner-entity-recognizers-opennlp/src/test/resources/models/

REM Apache licensed OpenNLP models.
wget http://apache.claz.org/opennlp/models/langdetect/1.8.3/langdetect-183.bin -P idylnlp-ai-nlp/idylnlp-ai-nlp-language-detection/idylnlp-ai-nlp-language-detection-opennlp/src/main/resources/
wget http://apache.claz.org/opennlp/models/langdetect/1.8.3/langdetect-183.bin -P idylnlp-ai-nlp/idylnlp-ai-nlp-language-detection/idylnlp-ai-nlp-language-detection-opennlp/src/test/resources/
