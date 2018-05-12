# Idyl NLP

|    | Links |
| ------------- | ------------- |
| Build Status  | [![Build Status](https://travis-ci.org/idylnlp/idylnlp.svg?branch=master)](https://travis-ci.org/idylnlp/idylnlp)  |
| Current Release  | [![Release](https://img.shields.io/nexus/r/https/oss.sonatype.org/ai.idylnlp/idylnlp.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22ai.idylnlp%22)  |
| Current Snapshots  | [![Snapshot](https://img.shields.io/nexus/s/https/oss.sonatype.org/ai.idylnlp/idylnlp.svg)](https://oss.sonatype.org/content/repositories/snapshots/ai/idylnlp/) |
| Unit Test Coverage | [![Coverage Status](https://coveralls.io/repos/github/idylnlp/idylnlp/badge.svg?branch=master)](https://coveralls.io/github/idylnlp/idylnlp?branch=master)
| Follow  | [![Follow](	https://img.shields.io/twitter/follow/mtnfog.svg?style=social&label=Follow)](https://twitter.com/mtnfog)  |

Idyl NLP is a natural language processing (NLP) framework released under the business-friendly Apache License, version 2.0. The framework features core NLP capabilities such as language detection, sentence extraction, tokenization, and named-entity extraction.

Idyl NLP uses a combination of custom implementations and other open-source projects to perform its tasks. In some cases there are multiple implementations available allowing a choice of which to use.  Idyl NLP stands on the shoulders of giants to provide a capable, flexible, and powerful NLP framework.

If you are looking for commercially supported NLP microservices look at the [NLP Building Blocks](http://www.mtnfog.com/nlp-building-blocks/). These applications are powered by Idyl NLP.

Visit the Idyl NLP home page at [idylnlp.ai](http://www.idylnlp.ai).

### Idyl NLP Capabilities

Refer to the [sample projects](https://github.com/idylnlp/idylnlp-samples) for example implementations of the below capabilities. Some of the unit tests in this project will also provide examples.

* Language Detection
* Sentence Extraction
* Tokenization
* Named-Entity Extraction (supports neural network models on CPU/GPU)
* Document Classification (supports neural network models on CPU/GPU)

All of these core capabilities with the exception of language detection can utilize custom trained models. The ability to train and evaluate trained models is available. Named-entity extraction and document classification support neural network models as well as maximum entropy and perceptron-based models.

### Idyl NLP Ecosystem Projects

* [idylnlp-nifi](https://github.com/idylnlp/idylnlp-nifi) provides Apache NiFi processors using Idyl NLP for NLP tasks.
* [idylnlp-deeplearning4j](https://github.com/idylnlp/idylnlp-deeplearning4j) allows for using Idyl NLP within DeepLearning4j projects.
* [idylnlp-standford-core-nlp](https://github.com/idylnlp/idylnlp-stanford-core-nlp) provides wrapper implementations to use [Stanford Core NLP](https://github.com/stanfordnlp/CoreNLP) within Idyl NLP.

### Projects Using Idyl NLP

* [Renku Language Detection Engine](https://github.com/mtnfog/renku-language-detection-engine) is an open source microservice that identifes the language of natural language text.
* [Sonnet Tokenization Engine](https://github.com/mtnfog/sonnet-tokenization-engine) is an open source microservice for performing string tokenization.

## Usage

[Release](https://search.maven.org/#search%7Cga%7C1%7Cg%3A"ai.idylnlp") and [snapshot](https://oss.sonatype.org/content/repositories/snapshots/ai/idylnlp/) dependencies are available:

```
<dependency>
  <groupId>ai.idylnlp</groupId>
  <artifactId>...</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Simplified NER Pipeline

An example to quickly make a named-entity extraction pipeline to extract person entities from English natural language text:

```
NerPipelineBuilder builder = new NerPipeline.NerPipelineBuilder();
NerPipeline pipeline = builder.build(LanguageCode.en);

EntityExtractionResponse response = pipeline.run("George Washington was president.");

for(Entity e : response.getEntities()) {
   System.out.println(e.toString());
}
```

This code outputs:

```
Text: George Washington; Confidence: 0.96; Type: person; Language Code: eng; Span: [0..2);
```

## Building Idyl NLP

Idyl NLP requires Java 8. To build, simply:

```
mvn clean install
```

### Testing

Unit tests are included. Some tests require data that cannot be made publicly available at this time due to either size constraints or licensing. These tests are categorized as `ExternalData` and are skipped during a regular build. We execute these tests using an in-house build job executed after each commit. We are working to find a suitable location to host the large test data to make it available to everyone.

There are also some tests categorized as `HighMemoryUsage`. These tests require a very large amount of memory to execute. For this reason they are disabled during regular builds. We execute these tests on a privately hosted build server.

## License

Idyl NLP is available under the Apache License, version 2.0.

Copyright 2018 Mountain Fog, Inc.
