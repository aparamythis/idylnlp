# Idyl NLP

Idyl NLP is a natural language processing (NLP) framework released under the business-friendly Apache License, version 2.0. The framework features NLP capabilities such as language detection, sentence extraction, tokenization, named-entity extraction, and others. Some capabilities, such as named-entity extraction and document classification, are capable of using neural networks on both CPU and GPU architectures.

Idyl NLP uses a combination of custom implementations and other open-source projects to perform its tasks. In some cases there are multiple implementations available. All core capabilities are abstracted by interfaces to allow an implementation choice.

The goal of Idyl NLP is to provide a comprehensive NLP library that is available under a business-friendly open-source license.

If you are looking for pre-built NLP microservices available as Docker containers and deployable on AWS and Azure checkout the [NLP Building Blocks](http://www.mtnfog.com/nlp-building-blocks/) powered by Idyl NLP.

For more information see the Idyl NLP home at [idylnlp.ai](http://www.idylnlp.ai).

## Core Capabilities

Refer to the samples directory for example implementations of the below capabilities. Some of the unit tests will also provide examples.

* Language Detection
* Sentence Extraction
* Tokenization
* Named-Entity Extraction
* Document Classification

All of these core capabilities with the exception of language detection can utilize custom trained models. The ability to train and evaluate trained models is available. Named-entity extraction and document classification support neural network models as well as maximum entropy and perceptron-based models.

## Building

Idyl NLP requires Java 8.

To build:

```
./download-files.sh
mvn clean install
```

The `download-files.sh` script downloads some files required by unit tests.

### Testing

Unit tests are included. Some tests require data that cannot be made publicly available at this time due to either size constraints or licensing. These tests are marked with the `@ExternalData` annotation and are skipped during a regular build. We execute these tests using an in-house build job executed after each commit. We are working to find a suitable location to host the large test data to make it available to everyone.

## License

Idyl NLP is available under the Apache License, version 2.0.

Copyright 2018 Mountain Fog, Inc.
