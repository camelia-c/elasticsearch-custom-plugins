# Solving issues

### Solving issues related to transitive dependencies conflicts


Remove some artefacts from Maven local repo, to force refresh:  
see https://maven.apache.org/plugins/maven-dependency-plugin/purge-local-repository-mojo.html   

```
cd readabilityingestor

mvn dependency:purge-local-repository -DmanualInclude="com.itextpdf,com.github.mfietz,com.google.code.gson" -DactTransitively=false

```

Note that the POM.xml so far is that of commit https://github.com/camelia-c/elasticsearch-custom-plugins/commit/7a17606fc00d93990823d08348bafd3af9957702  

If we build now:  

```
mvn clean install

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------

```

But it hides a mountain of dependencies issues, so we use the **maven-enforcer-plugin** in the POM to highlight transitive depencies conflicts  

```
mvn verify


[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building readabilityingestor 1.0
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-enforcer-plugin:3.0.0-M3:enforce (enforce) @ readabilityingestor ---
[WARNING] 
Dependency convergence error for org.apache.lucene:lucene-core:8.8.0 paths to dependency are:
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-org.elasticsearch:elasticsearch:7.12.1
    +-org.apache.lucene:lucene-core:8.8.0
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-org.elasticsearch:elasticsearch:7.12.1
    +-org.apache.lucene:lucene-backward-codecs:8.8.0
      +-org.apache.lucene:lucene-core:8.8.0
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-org.elasticsearch:elasticsearch:7.12.1
    +-org.apache.lucene:lucene-grouping:8.8.0
      +-org.apache.lucene:lucene-core:8.8.0
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-org.elasticsearch:elasticsearch:7.12.1
    +-org.apache.lucene:lucene-highlighter:8.8.0
      +-org.apache.lucene:lucene-core:8.8.0
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-org.elasticsearch:elasticsearch:7.12.1
    +-org.apache.lucene:lucene-join:8.8.0
      +-org.apache.lucene:lucene-core:8.8.0
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-org.elasticsearch:elasticsearch:7.12.1
    +-org.apache.lucene:lucene-memory:8.8.0
      +-org.apache.lucene:lucene-core:8.8.0
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-org.elasticsearch:elasticsearch:7.12.1
    +-org.apache.lucene:lucene-misc:8.8.0
      +-org.apache.lucene:lucene-core:8.8.0
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-org.elasticsearch:elasticsearch:7.12.1
    +-org.apache.lucene:lucene-spatial-extras:8.8.0
      +-org.apache.lucene:lucene-core:8.8.0
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-org.elasticsearch:elasticsearch:7.12.1
    +-org.apache.lucene:lucene-spatial3d:8.8.0
      +-org.apache.lucene:lucene-core:8.8.0
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-org.elasticsearch:elasticsearch:7.12.1
    +-org.apache.lucene:lucene-suggest:8.8.0
      +-org.apache.lucene:lucene-core:8.8.0
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-edu.stanford.nlp:stanford-corenlp:4.2.2
    +-org.apache.lucene:lucene-queryparser:8.8.0
      +-org.apache.lucene:lucene-core:8.8.0
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-edu.stanford.nlp:stanford-corenlp:4.2.2
    +-org.apache.lucene:lucene-analyzers-common:8.8.0
      +-org.apache.lucene:lucene-core:7.5.0
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-edu.stanford.nlp:stanford-corenlp:4.2.2
    +-org.apache.lucene:lucene-core:7.5.0

[WARNING] 
Dependency convergence error for org.slf4j:slf4j-api:1.7.12 paths to dependency are:
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-edu.stanford.nlp:stanford-corenlp:4.2.2
    +-org.slf4j:slf4j-api:1.7.12
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-com.itextpdf:hyph:7.1.15
    +-org.slf4j:slf4j-api:1.7.30
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-com.itextpdf:layout:7.1.15
    +-com.itextpdf:kernel:7.1.15
      +-com.itextpdf:io:7.1.15
        +-org.slf4j:slf4j-api:1.7.30
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-com.itextpdf:layout:7.1.15
    +-com.itextpdf:kernel:7.1.15
      +-org.slf4j:slf4j-api:1.7.30
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-com.itextpdf:layout:7.1.15
    +-org.slf4j:slf4j-api:1.7.30

[WARNING] 
Dependency convergence error for javax.xml.bind:jaxb-api:2.2.7 paths to dependency are:
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-edu.stanford.nlp:stanford-corenlp:4.2.2
    +-de.jollyday:jollyday:0.4.9
      +-javax.xml.bind:jaxb-api:2.2.7
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-edu.stanford.nlp:stanford-corenlp:4.2.2
    +-javax.xml.bind:jaxb-api:2.4.0-b180830.0359

[WARNING] 
Dependency convergence error for joda-time:joda-time:2.10.4 paths to dependency are:
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-org.elasticsearch:elasticsearch:7.12.1
    +-joda-time:joda-time:2.10.4
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-edu.stanford.nlp:stanford-corenlp:4.2.2
    +-de.jollyday:jollyday:0.4.9
      +-joda-time:joda-time:2.10.4
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-edu.stanford.nlp:stanford-corenlp:4.2.2
    +-joda-time:joda-time:2.10.5

[WARNING] 
Dependency convergence error for xml-apis:xml-apis:1.3.03 paths to dependency are:
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-edu.stanford.nlp:stanford-corenlp:4.2.2
    +-xom:xom:1.3.2
      +-xml-apis:xml-apis:1.3.03
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-edu.stanford.nlp:stanford-corenlp:4.2.2
    +-xom:xom:1.3.2
      +-xerces:xercesImpl:2.8.0
        +-xml-apis:xml-apis:1.3.03
and
+-org.elasticsearch.ingest:readabilityingestor:1.0
  +-edu.stanford.nlp:stanford-corenlp:4.2.2
    +-xom:xom:1.3.2
      +-xalan:xalan:2.7.0
        +-xml-apis:xml-apis:1.0.b2

[WARNING] Rule 0: org.apache.maven.plugins.enforcer.DependencyConvergence failed with message:
Failed while enforcing releasability. See above detailed error message.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------


```

**Also, pay a closer look to a "jars hell" brought by transitive dependencies onto JAXB used by CoreNLP:**   

```
mvn dependency:tree -Dverbose -Dincludes=javax.xml.bind:jaxb-api

[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-dependency-plugin:2.8:tree (default-cli) @ readabilityingestor ---
[INFO] org.elasticsearch.ingest:readabilityingestor:jar:1.0
[INFO] \- edu.stanford.nlp:stanford-corenlp:jar:4.2.2:compile
[INFO]    +- de.jollyday:jollyday:jar:0.4.9:compile
[INFO]    |  \- (javax.xml.bind:jaxb-api:jar:2.2.7:compile - omitted for conflict with 2.4.0-b180830.0359)
[INFO]    \- javax.xml.bind:jaxb-api:jar:2.4.0-b180830.0359:compile
[INFO] ------------------------------------------------------------------------



mvn dependency:tree -Dverbose -Dincludes=com.sun.xml.bind:jaxb-core

[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-dependency-plugin:2.8:tree (default-cli) @ readabilityingestor ---
[INFO] org.elasticsearch.ingest:readabilityingestor:jar:1.0
[INFO] \- edu.stanford.nlp:stanford-corenlp:jar:4.2.2:compile
[INFO]    \- com.sun.xml.bind:jaxb-core:jar:2.3.0.1:compile
[INFO] ------------------------------------------------------------------------



 mvn dependency:tree -Dverbose -Dincludes=com.sun.xml.bind:jaxb-impl

[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-dependency-plugin:2.8:tree (default-cli) @ readabilityingestor ---
[INFO] org.elasticsearch.ingest:readabilityingestor:jar:1.0
[INFO] \- edu.stanford.nlp:stanford-corenlp:jar:4.2.2:compile
[INFO]    \- com.sun.xml.bind:jaxb-impl:jar:2.4.0-b180830.0438:compile
[INFO] ------------------------------------------------------------------------

```


**There are multiple ways of solving all the above issues ( see [1-3] )**, but first let's check what's available in elasticsearch/lib:  

```

export GCP_PROJ=$(gcloud config get-value project)
echo $GCP_PROJ

gcloud beta compute ssh --project "$GCP_PROJ" \
                        --zone "us-west2-c" "elasticsearch-1-vm"


cd /opt/bitnami

ls elasticsearch/lib 

elasticsearch-7.12.1.jar                     elasticsearch-x-content-7.12.1.jar   java-version-checker-7.12.1.jar  lucene-analyzers-common-8.8.0.jar  lucene-misc-8.8.0.jar            snakeyaml-1.26.jar
elasticsearch-cli-7.12.1.jar                 HdrHistogram-2.1.9.jar               jna-5.7.0-1.jar                  lucene-backward-codecs-8.8.0.jar   lucene-queries-8.8.0.jar         spatial4j-0.7.jar
elasticsearch-core-7.12.1.jar                hppc-0.8.1.jar                       joda-time-2.10.4.jar             lucene-core-8.8.0.jar              lucene-queryparser-8.8.0.jar     t-digest-3.2.jar
elasticsearch-geo-7.12.1.jar                 jackson-core-2.10.4.jar              jopt-simple-5.0.2.jar            lucene-grouping-8.8.0.jar          lucene-sandbox-8.8.0.jar         tools
elasticsearch-launchers-7.12.1.jar           jackson-dataformat-cbor-2.10.4.jar   jts-core-1.15.0.jar              lucene-highlighter-8.8.0.jar       lucene-spatial3d-8.8.0.jar
elasticsearch-plugin-classloader-7.12.1.jar  jackson-dataformat-smile-2.10.4.jar  log4j-api-2.11.1.jar             lucene-join-8.8.0.jar              lucene-spatial-extras-8.8.0.jar
elasticsearch-secure-sm-7.12.1.jar           jackson-dataformat-yaml-2.10.4.jar   log4j-core-2.11.1.jar            lucene-memory-8.8.0.jar            lucene-suggest-8.8.0.jar


exit
```


So we can **exclude** some transitive dependencies in the plugin's POM.xml and use **dependency management** to pin the versions for the others.  

After we modify the POM, we check to see that the pinned versions were used (these are marked as "version managed").  

```
mvn dependency:tree -Dverbose  | grep "managed"

[INFO] |  |  \- (javax.xml.bind:jaxb-api:jar:2.3.1:compile - version managed from 2.4.0-b180830.0359; omitted for duplicate)
[INFO] |  |     \- (xml-apis:xml-apis:jar:1.3.03:compile - version managed from 2.0.2; omitted for duplicate)
[INFO] |  +- org.slf4j:slf4j-api:jar:1.7.30:compile (version managed from 1.7.12)
[INFO] |  +- com.sun.xml.bind:jaxb-core:jar:3.0.1:compile (version managed from 2.3.0.1)
[INFO] |  \- com.sun.xml.bind:jaxb-impl:jar:3.0.1:compile (version managed from 2.4.0-b180830.0438)
[INFO] |     \- (com.sun.xml.bind:jaxb-core:jar:3.0.1:compile - version managed from 2.3.0.1; omitted for duplicate)
[INFO] |  \- (org.slf4j:slf4j-api:jar:1.7.30:compile - version managed from 1.7.12; omitted for duplicate)
[INFO] |  |  |  \- (org.slf4j:slf4j-api:jar:1.7.30:compile - version managed from 1.7.12; omitted for duplicate)
[INFO] |  |  \- (org.slf4j:slf4j-api:jar:1.7.30:compile - version managed from 1.7.12; omitted for duplicate)
[INFO] |  \- (org.slf4j:slf4j-api:jar:1.7.30:compile - version managed from 1.7.12; omitted for duplicate)


```

The  POM.xml now is that of commit https://github.com/camelia-c/elasticsearch-custom-plugins/commit/1de115bcb1f52187bcab82b4f79c4f39a1573a09  



### Solving issues related to runtime security 


To begin with, we revisit the permissions acceptable to be set for a plugin in Elasticsearch:

https://github.com/elastic/elasticsearch/blob/7.12/server/src/main/java/org/elasticsearch/bootstrap/PolicyUtil.java  

#### setIO  caused by original CoreNLP

This was solved by my changes in the source code of CoreNLP in https://github.com/camelia-c/CoreNLP  - branch `no_setIO_Redwood`  

Just a short recap:  

```
Caused by: java.security.AccessControlException: access denied ("java.lang.RuntimePermission" "setIO")
......
at edu.stanford.nlp.pipeline.AnnotationPipeline.<clinit>(AnnotationPipeline.java:30) ~[?:?]
```

The issue concerns logging in CoreNLP, mainly Redwood and RedwoodConfiguration,  
https://github.com/stanfordnlp/CoreNLP/blob/main/src/edu/stanford/nlp/util/logging/Redwood.java  
https://github.com/stanfordnlp/CoreNLP/blob/main/src/edu/stanford/nlp/util/logging/RedwoodConfiguration.java  

These classes use the following setIO which are not acceptable for ES plugins:
- System.setOut(...)
- System.setErr(...)


#### accessDeclaredMembers  caused by GSON

Just a short recap:

```
Caused by: java.security.AccessControlException: access denied ("java.lang.RuntimePermission" "accessDeclaredMembers")
.........
at com.google.gson.internal.ConstructorConstructor.<init>(ConstructorConstructor.java:51) ~[?:?]
```

Luckily, there exist permissions that we can grant for our plugin to solve this issue.  
So we add file src/main/plugin-metadata/plugin-security.policy  which grants:  

```
grant {
   permission java.lang.RuntimePermission "accessDeclaredMembers";
   permission java.lang.reflect.ReflectPermission "suppressAccessChecks";
};

```

and we configure src/main/assemblies/plugin.xml to includ this policy settings file in the final zip.  

The consequence is that at installation time, the ES admin gets prompted to accept these permissions for the new custom plugin (see also README.md):   

```
-> Installing file:///home/camelia/readabilityingestor-1.0.zip
-> Downloading file:///home/camelia/readabilityingestor-1.0.zip
[=================================================] 100%   
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@     WARNING: plugin requires additional permissions     @
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
* java.lang.RuntimePermission accessDeclaredMembers
* java.lang.reflect.ReflectPermission suppressAccessChecks
See http://docs.oracle.com/javase/8/docs/technotes/guides/security/permissions.html
for descriptions of what these permissions allow and the associated risks.

Continue with installation? [y/N]y
-> Installed readabilityingestor
-> Please restart Elasticsearch to activate any plugins installed

```

Solved!  

--------
[1] Surviving Dependency Hell with Maven by Robert Scholte and Ray Tsang. (JNation channel) https://www.youtube.com/watch?v=oQNpMSyge84  
[2] https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html  
[3] https://maven.apache.org/guides/introduction/introduction-to-optional-and-excludes-dependencies.html  

