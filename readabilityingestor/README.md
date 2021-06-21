# README

Create a new Maven project from archetype (see https://github.com/codelibs/elasticsearch-plugin-archetype) :

```
mvn archetype:generate \
    -DarchetypeGroupId=org.codelibs \
    -DarchetypeArtifactId=elasticsearch-plugin-archetype \
    -DarchetypeVersion=6.6.0 \
    -DgroupId=org.elasticsearch.ingest \
    -DartifactId=readabilityingestor \
    -Dversion=1.0 \
    -DpluginName=readabilityingestor
```

So far we've got:

```
tree readabilityingestor

readabilityingestor
├── pom.xml
└── src
    └── main
        ├── assemblies
        │   └── plugin.xml
        ├── java
        │   └── org
        │       └── elasticsearch
        │           └── ingest
        │               ├── readabilityingestorPlugin.java
        │               └── rest
        │                   └── RestreadabilityingestorAction.java
        └── plugin-metadata
            └── plugin-descriptor.properties

9 directories, 5 files


```

## Develop



For the tests, generate HTML report in `target/site/surefire-report.html`.   
Also check the tests outputs in files under `target/surefire-reports`.   

```
mvn surefire-report:report
``` 

Build and package:

```
mvn clean install

tree .

.
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── assemblies
│   │   │   └── plugin.xml
│   │   ├── java
│   │   │   └── org
│   │   │       └── elasticsearch
│   │   │           └── ingest
│   │   │               ├── readabilityingestorPlugin.java
│   │   │               ├── ReadabilityMetrics.java
│   │   │               └── ReadabilityProcessor.java
│   │   └── plugin-metadata
│   │       └── plugin-descriptor.properties
│   └── test
│       └── java
│           └── org
│               └── elasticsearch
│                   └── ingest
│                       └── Test3rdParty.java
└── target
    ├── archive-tmp
    ├── classes
    │   └── org
    │       └── elasticsearch
    │           └── ingest
    │               ├── readabilityingestorPlugin.class
    │               ├── ReadabilityMetrics.class
    │               ├── ReadabilityProcessor.class
    │               └── ReadabilityProcessor$Factory.class
    ├── generated-sources
    │   └── annotations
    ├── generated-test-sources
    │   └── test-annotations
    ├── maven-archiver
    │   └── pom.properties
    ├── maven-status
    │   └── maven-compiler-plugin
    │       ├── compile
    │       │   └── default-compile
    │       │       ├── createdFiles.lst
    │       │       └── inputFiles.lst
    │       └── testCompile
    │           └── default-testCompile
    │               ├── createdFiles.lst
    │               └── inputFiles.lst
    ├── readabilityingestor-1.0.jar
    ├── readabilityingestor-1.0-sources.jar
    ├── releases
    │   └── readabilityingestor-1.0.zip
    ├── site
    │   └── surefire-report.html
    ├── surefire-reports
    │   ├── org.elasticsearch.ingest.Test3rdParty-output.txt
    │   ├── org.elasticsearch.ingest.Test3rdParty.txt
    │   └── TEST-org.elasticsearch.ingest.Test3rdParty.xml
    └── test-classes
        └── org
            └── elasticsearch
                └── ingest
                    └── Test3rdParty.class

37 directories, 25 files

```


