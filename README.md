# Idyl NLP

[![Build Status](https://travis-ci.org/idylnlp/idylnlp.svg?branch=master)](https://travis-ci.org/idylnlp/idylnlp)

Visit the Idyl NLP home page at [idylnlp.ai](http://www.idylnlp.ai).

Idyl NLP is a natural language processing (NLP) framework released under the business-friendly Apache License, version 2.0. The framework features core NLP capabilities such as language detection, sentence extraction, tokenization, and named-entity extraction.

Idyl NLP uses a combination of custom implementations and other open-source projects to perform its tasks. In some cases there are multiple implementations available allowing a choice of which to use.  Idyl NLP stands on the shoulders of giants to provide a capable, flexible, and powerful NLP framework.

If you are looking for commercially supported NLP microservices look at the [NLP Building Blocks](http://www.mtnfog.com/nlp-building-blocks/). These applications are powered by Idyl NLP.

## Usage

Available in [Maven Central](https://search.maven.org/#search%7Cga%7C1%7Cg%3A"ai.idylnlp"):

```
<dependency>
  <groupId>ai.idylnlp</groupId>
  <artifactId>...</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Core NLP Capabilities

Refer to the [sample projects](https://github.com/idylnlp/idylnlp-samples) for example implementations of the below capabilities. Some of the unit tests in this project will also provide examples.

* Language Detection
* Sentence Extraction
* Tokenization
* Named-Entity Extraction (supports neural network models on CPU/GPU)
* Document Classification (supports neural network models on CPU/GPU)

All of these core capabilities with the exception of language detection can utilize custom trained models. The ability to train and evaluate trained models is available. Named-entity extraction and document classification support neural network models as well as maximum entropy and perceptron-based models.

## Applications

* Apache NiFi processors using Idyl NLP for NLP tasks are available in the [idylnlp-nifi](https://github.com/idylnlp/idylnlp-nifi) project.

## Building

Idyl NLP requires Java 8. To build, simply:

```
mvn clean install
```

### Tests

Unit tests are included. Some tests require data that cannot be made publicly available at this time due to either size constraints or licensing. These tests are categorized as `ExternalData` and are skipped during a regular build. We execute these tests using an in-house build job executed after each commit. We are working to find a suitable location to host the large test data to make it available to everyone.

There are also some tests categorized as `HighMemoryUsage`. These tests require a very large amount of memory to execute. For this reason they are disabled during regular builds. We execute these tests on a privately hosted build server.

## License

Idyl NLP is available under the Apache License, version 2.0.

Copyright 2018 Mountain Fog, Inc.
