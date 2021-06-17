# README


Create a new Maven project from archetype (see https://github.com/codelibs/elasticsearch-plugin-archetype) :

```
mvn archetype:generate \
    -DarchetypeGroupId=org.codelibs \
    -DarchetypeArtifactId=elasticsearch-plugin-archetype \
    -DarchetypeVersion=6.6.0 \
    -DgroupId=org.elasticsearch.ingest \
    -DartifactId=xmlingestor \
    -Dversion=1.0 \
    -DpluginName=xmlingestor

```

So far we've got:

```
tree xmlingestor

xmlingestor
├── pom.xml
└── src
    └── main
        ├── assemblies
        │   └── plugin.xml
        ├── java
        │   └── org
        │       └── elasticsearch
        │           └── ingest
        │               ├── rest
        │               │   └── RestxmlingestorAction.java
        │               └── xmlingestorPlugin.java
        └── plugin-metadata
            └── plugin-descriptor.properties

9 directories, 5 files

```


