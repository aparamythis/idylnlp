#!/bin/bash

echo "Downloading required files."

mkdir -p ./idyl-ai-nlp/idyl-ai-nlp-pos-tagging/src/test/resources/models/
mkdir -p ./idyl-ai-nlp-language-detection/idyl-ai-nlp-language-detection-opennlp/src/main/resources/

# Non-Apache licensed OpenNLP models.
wget http://opennlp.sourceforge.net/models-1.5/en-pos-maxent.bin -O ./idyl-ai-nlp/idyl-ai-nlp-pos-tagging/src/test/resources/models/en-pos-maxent.bin
wget http://opennlp.sourceforge.net/models-1.5/en-sent.bin -O ./idyl-ai-nlp/idyl-ai-nlp-sentence-detection/src/test/resources/en-sent.bin
wget http://opennlp.sourceforge.net/models-1.5/en-sent.bin -O ./idyl-ai-nlp/idyl-ai-nlp-entity-recognizers/idyl-ai-nlp-ner-entity-recognizers-opennlp/src/test/resources/models/en-sent.bin

# Apache licensed OpenNLP models.
wget http://apache.claz.org/opennlp/models/langdetect/1.8.3/langdetect-183.bin -O ./idyl-ai-nlp/idyl-ai-nlp-language-detection/idyl-ai-nlp-language-detection-opennlp/src/main/resources/langdetect-183.bin
wget http://apache.claz.org/opennlp/models/langdetect/1.8.3/langdetect-183.bin -O ./idyl-ai-nlp/idyl-ai-nlp-language-detection/idyl-ai-nlp-language-detection-opennlp/src/test/resources/langdetect-183.bin

echo "Download finished."

