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

See `README_Solving_issues.md`  for the steps related to solving transitive dependencies conflicts and setting up security policies for the plugin.

Note that if we don't provide stanford-corenlp-4.2.2-models.jar and we don't specify file for POS model, we are norified that:

```
edu.stanford.nlp.io.RuntimeIOException: Error while loading a tagger model (probably missing model file)
	at org.elasticsearch.ingest.Test3rdParty.setUp(Test3rdParty.java:55)
Caused by: java.io.IOException: Unable to open "edu/stanford/nlp/models/pos-tagger/english-left3words-distsim.tagger" as class path, filename or URL
	at org.elasticsearch.ingest.Test3rdParty.setUp(Test3rdParty.java:55)
```

So we can download the pos taggers from https://nlp.stanford.edu/software/tagger.shtml#Download , unzip the archive and copy just the  `stanford-postagger-full-2020-11-17/models/english-left3words-distsim.tagger` to our resources folder.

The second resource we use is the updated list of 3000 basic (fundamental) words for the Dale-Chall formula. In the implementation we need to check the lemma of the word against this list in order to account for words inflexions.


For the tests, we use the results from the R library Quanteda (see Jupyter notebook) as expected values.  
However, these are not necessarily the groundtruth, so after generating test reports, we skip the tests when generating the jar.  
Run the command:  

```
mvn surefire-report:report
``` 

and look for the outputs in folder `target/surefire-reports` and in HTML report `target/site/surefire-report.html` .


Build and package:

```
mvn clean install -DskipTests


tree .

.
├── pom.xml
├── Readability_Py_R.ipynb
├── README.md
├── README_Solving_issues.md
├── src
│   ├── main
│   │   ├── assemblies
│   │   │   └── plugin.xml
│   │   ├── java
│   │   │   └── org
│   │   │       └── elasticsearch
│   │   │           └── ingest
│   │   │               ├── MetricsResult.java
│   │   │               ├── readabilityingestorPlugin.java
│   │   │               ├── ReadabilityMetrics.java
│   │   │               └── ReadabilityProcessor.java
│   │   ├── plugin-metadata
│   │   │   ├── plugin-descriptor.properties
│   │   │   └── plugin-security.policy
│   │   └── resources
│   │       ├── DC3000.txt
│   │       └── english-left3words-distsim.tagger
│   └── test
│       └── java
│           └── org
│               └── elasticsearch
│                   └── ingest
│                       ├── Test3rdParty.java
│                       └── TestReadabilityMetrics.java
└── target
    ├── archive-tmp
    ├── classes
    │   ├── DC3000.txt
    │   ├── english-left3words-distsim.tagger
    │   └── org
    │       └── elasticsearch
    │           └── ingest
    │               ├── MetricsResult.class
    │               ├── readabilityingestorPlugin.class
    │               ├── ReadabilityMetrics$1.class
    │               ├── ReadabilityMetrics.class
    │               ├── ReadabilityProcessor$1.class
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
    └── test-classes
        └── org
            └── elasticsearch
                └── ingest
                    ├── Test3rdParty.class
                    ├── TestReadabilityMetrics$1.class
                    ├── TestReadabilityMetrics$2.class
                    ├── TestReadabilityMetrics$3.class
                    ├── TestReadabilityMetrics$4.class
                    └── TestReadabilityMetrics.class

36 directories, 38 files

```

then inspect the jar and the zip:

```
jar tfv target/readabilityingestor-1.0.jar 


     0 Sun Jun 27 12:30:28 EEST 2021 META-INF/
   133 Sun Jun 27 12:30:26 EEST 2021 META-INF/MANIFEST.MF
     0 Sun Jun 27 12:30:24 EEST 2021 org/
     0 Sun Jun 27 12:30:24 EEST 2021 org/elasticsearch/
     0 Sun Jun 27 12:30:24 EEST 2021 org/elasticsearch/ingest/
  1632 Sun Jun 27 12:30:24 EEST 2021 org/elasticsearch/ingest/ReadabilityProcessor$Factory.class
  4850 Sun Jun 27 12:30:24 EEST 2021 org/elasticsearch/ingest/MetricsResult.class
  1121 Sun Jun 27 12:30:24 EEST 2021 org/elasticsearch/ingest/ReadabilityMetrics$1.class
  8947 Sun Jun 27 12:30:24 EEST 2021 org/elasticsearch/ingest/ReadabilityMetrics.class
  1591 Sun Jun 27 12:30:24 EEST 2021 org/elasticsearch/ingest/ReadabilityProcessor$1.class
  2432 Sun Jun 27 12:30:24 EEST 2021 org/elasticsearch/ingest/ReadabilityProcessor.class
  1247 Sun Jun 27 12:30:24 EEST 2021 org/elasticsearch/ingest/readabilityingestorPlugin.class
15198877 Sun Jun 27 12:30:22 EEST 2021 english-left3words-distsim.tagger
 18642 Sun Jun 27 12:30:22 EEST 2021 DC3000.txt
     0 Sun Jun 27 12:30:30 EEST 2021 META-INF/maven/
     0 Sun Jun 27 12:30:30 EEST 2021 META-INF/maven/org.elasticsearch.ingest/
     0 Sun Jun 27 12:30:30 EEST 2021 META-INF/maven/org.elasticsearch.ingest/readabilityingestor/
 10808 Thu Jun 24 15:14:54 EEST 2021 META-INF/maven/org.elasticsearch.ingest/readabilityingestor/pom.xml
   127 Sun Jun 27 12:30:28 EEST 2021 META-INF/maven/org.elasticsearch.ingest/readabilityingestor/pom.properties

#-----------------------------------

du -h target/releases/readabilityingestor-1.0.zip 

41M	target/releases/readabilityingestor-1.0.zip


less target/releases/readabilityingestor-1.0.zip


Archive:  target/releases/readabilityingestor-1.0.zip
 Length   Method    Size  Cmpr    Date    Time   CRC-32   Name
--------  ------  ------- ---- ---------- ----- --------  ----
     201  Defl:N      117  42% 2021-06-17 11:12 0207b79f  plugin-descriptor.properties
   11719  Defl:N     8595  27% 2020-08-30 10:09 9ecc1d7f  AppleJavaExtensions-1.4.jar
  128076  Defl:N   112237  12% 2021-06-17 18:02 5f109f29  jaxb-api-2.3.1.jar
   56674  Defl:N    52560   7% 2020-08-30 10:09 59af44ed  javax.activation-api-1.2.0.jar
   85353  Defl:N    75030  12% 2019-08-06 22:53 8ea10eaa  javax.servlet-api-3.0.1.jar
  195119  Defl:N   153792  21% 2006-04-10 23:32 a156e0ab  xml-apis-1.3.03.jar
 2730442  Defl:N  2565346   6% 2020-08-30 10:09 4eb90171  xalan-2.7.0.jar
  334005  Defl:N   308638   8% 2021-06-16 12:27 9c976e02  ejml-fdense-0.39.jar
   85147  Defl:N    74448  13% 2019-10-19 02:07 856d5077  javax.json-1.0.4.jar
  128941  Defl:N   111222  14% 2021-06-17 19:18 2abb30fd  jakarta.xml.bind-api-3.0.1.jar
   62154  Defl:N    55683  10% 2021-06-17 19:18 746630e3  jakarta.activation-2.0.1.jar
  982956  Defl:N   946114   4% 2021-06-21 15:07 540680bc  hyph-7.1.15.jar
  822964  Defl:N   776457   6% 2021-06-21 15:07 395637ec  io-7.1.15.jar
 5961178  Defl:N  5118382  14% 2021-06-16 15:05 feb67c06  bcprov-jdk15on-1.68.jar
     152  Defl:N      103  32% 2021-06-22 11:19 269d46bb  plugin-security.policy
  213591  Defl:N   180025  16% 2020-08-30 10:09 68846d2a  jollyday-0.4.9.jar
  412693  Defl:N   381549   8% 2019-10-18 23:42 6ba35960  commons-lang3-3.3.1.jar
  327624  Defl:N   287547  12% 2020-08-30 10:09 05f6b5e9  xom-1.3.2.jar
 1207073  Defl:N  1087369  10% 2020-08-30 10:09 2835f384  xercesImpl-2.8.0.jar
  199222  Defl:N   180661   9% 2021-06-16 12:27 271d42a8  ejml-core-0.39.jar
   19936  Defl:N    14794  26% 2019-10-17 13:32 204031ef  jsr305-3.0.2.jar
  335068  Defl:N   309615   8% 2021-06-16 12:27 cae4ca5e  ejml-ddense-0.39.jar
   74558  Defl:N    68723   8% 2021-06-16 12:27 6c779850  ejml-cdense-0.39.jar
   70300  Defl:N    65191   7% 2021-06-16 12:27 2bfbef70  ejml-fsparse-0.39.jar
 1635823  Defl:N  1538738   6% 2020-08-30 10:09 7c02b90f  protobuf-java-3.9.2.jar
 1227067  Defl:N  1129647   8% 2021-06-21 15:07 8ae4d3bb  kernel-7.1.15.jar
  395831  Defl:N   386857   2% 2021-06-21 15:07 6a630a01  JHyphenator-master.jar
  240400  Defl:N   216265  10% 2021-06-21 15:07 82af5a49  gson-2.8.7.jar
 6713812  Defl:N  6714089   0% 2021-06-27 12:30 a1b08613  readabilityingestor-1.0.jar
   18642  Defl:N     7915  58% 2021-06-23 02:03 579f8c64  DC3000.txt
10826379  Defl:N 10217395   6% 2021-06-20 13:45 3460718a  stanford-corenlp-4.2.2.jar
15198877  Defl:N  6690139  56% 2020-01-12 11:47 15507eb5  english-left3words-distsim.tagger
  178321  Defl:N   161511   9% 2021-06-16 12:27 b66b0c56  ejml-simple-0.39.jar
   74948  Defl:N    69164   8% 2021-06-16 12:27 df67b653  ejml-zdense-0.39.jar
   70943  Defl:N    65732   7% 2021-06-16 12:27 ef29ad73  ejml-dsparse-0.39.jar
   41472  Defl:N    36754  11% 2021-05-16 01:41 66a852ee  slf4j-api-1.7.30.jar
  238251  Defl:N   202383  15% 2021-06-17 19:18 9e067085  jaxb-core-3.0.1.jar
  934608  Defl:N   811245  13% 2021-06-17 19:18 4df0464b  jaxb-impl-3.0.1.jar
  532475  Defl:N   499124   6% 2021-06-21 15:07 4903da69  layout-7.1.15.jar
  887800  Defl:N   767321  14% 2021-06-16 15:05 c76c77f0  bcpkix-jdk15on-1.68.jar
--------          -------  ---                            -------
53660795         42448477  21%                            40 files

```

## Deploy



```
export GCP_PROJ=$(gcloud config get-value project)
echo $GCP_PROJ

gcloud compute scp --project $GCP_PROJ \
                   --zone us-west2-c \
                   target/releases/readabilityingestor-1.0.zip  \
                   elasticsearch-1-vm:~/


gcloud beta compute ssh --project "$GCP_PROJ" \
                        --zone "us-west2-c" "elasticsearch-1-vm" 

```

Now, on the VM execute:

```

sudo su

cd /opt/bitnami/elasticsearch

bin/elasticsearch-plugin remove readabilityingestor

/opt/bitnami/ctlscript.sh restart

bin/elasticsearch-plugin install file:///home/camelia/readabilityingestor-1.0.zip 


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



/opt/bitnami/ctlscript.sh restart

bin/elasticsearch-plugin list | grep readabilityingestor

tree /opt/bitnami/elasticsearch/plugins/readabilityingestor

/opt/bitnami/elasticsearch/plugins/readabilityingestor
├── AppleJavaExtensions-1.4.jar
├── bcpkix-jdk15on-1.68.jar
├── bcprov-jdk15on-1.68.jar
├── commons-lang3-3.3.1.jar
├── DC3000.txt
├── ejml-cdense-0.39.jar
├── ejml-core-0.39.jar
├── ejml-ddense-0.39.jar
├── ejml-dsparse-0.39.jar
├── ejml-fdense-0.39.jar
├── ejml-fsparse-0.39.jar
├── ejml-simple-0.39.jar
├── ejml-zdense-0.39.jar
├── english-left3words-distsim.tagger
├── gson-2.8.7.jar
├── hyph-7.1.15.jar
├── io-7.1.15.jar
├── jakarta.activation-2.0.1.jar
├── jakarta.xml.bind-api-3.0.1.jar
├── javax.activation-api-1.2.0.jar
├── javax.json-1.0.4.jar
├── javax.servlet-api-3.0.1.jar
├── jaxb-api-2.3.1.jar
├── jaxb-core-3.0.1.jar
├── jaxb-impl-3.0.1.jar
├── JHyphenator-master.jar
├── jollyday-0.4.9.jar
├── jsr305-3.0.2.jar
├── kernel-7.1.15.jar
├── layout-7.1.15.jar
├── plugin-descriptor.properties
├── plugin-security.policy
├── protobuf-java-3.9.2.jar
├── readabilityingestor-1.0.jar
├── slf4j-api-1.7.30.jar
├── stanford-corenlp-4.2.2.jar
├── xalan-2.7.0.jar
├── xercesImpl-2.8.0.jar
├── xml-apis-1.3.03.jar
└── xom-1.3.2.jar

0 directories, 40 files

exit
```

## ES Pipelines to test the custom plugin

Prepare custom pipeline that includes our xml_processor and a json processor:

```
cat << EOF > /tmp/pipeline2.json
{
    "description": "test readability ingest plugin",
    "processors": [
        {
            "readability_processor" :{
                "srcfield" : "_doc_txt"
            },
            "json": {
                "field" : "_readability",
                "add_to_root" : false
            }
        }
    ]
}
EOF


#-----------------------------------------------------------------------

curl -H "Content-Type: application/json"  \
     -XPUT "localhost:9200/_ingest/pipeline/func_readability" \
     -d @/tmp/pipeline2.json   \
     |  python -m json.tool


  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   320  100    21  100   299     82   1172 --:--:-- --:--:-- --:--:--  1254
{
    "acknowledged": true
}


```

Prepare a test doc to simulate ingestion:

```
cat << EOF > /tmp/doc3.json
{
    "docs": [
        {
            "_index": "index",
            "_id": "id",
            "_source": {
                    "_doc_txt" : "In Greek myths, dolphins were seen invariably as helpers of humankind. Dolphins also seem to have been important to the Minoans, judging by artistic evidence from the ruined palace at Knossos. During the 2009 excavations of a major Mycenaean city at Iklaina, a striking fragment of a wall-paintings came to light, depicting a ship with three human figures and dolphins. Dolphins are common in Greek mythology, and many coins from ancient Greece have been found which feature a man, a boy or a deity riding on the back of a dolphin. The Ancient Greeks welcomed dolphins; spotting dolphins riding in a ship's wake was considered a good omen. In both ancient and later art, Cupid is often shown riding a dolphin."
            }
        }
    ]
}
EOF

#-----------------------------------------------------------------------

curl -H "Content-Type: application/json"  \
     -XPOST "localhost:9200/_ingest/pipeline/func_readability/_simulate" \
     -d @/tmp/doc3.json   \
     |  python -m json.tool
```

The result is:

```
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100  2866  100  1993  100   873   3760   1647 --:--:-- --:--:-- --:--:--  5397
{
    "docs": [
        {
            "doc": {
                "_id": "id",
                "_index": "index",
                "_ingest": {
                    "timestamp": "2021-06-27T11:00:44.011755Z"
                },
                "_source": {
                    "_doc_txt": "In Greek myths, dolphins were seen invariably as helpers of humankind. Dolphins also seem to have been important to the Minoans, judging by artistic evidence from the ruined palace at Knossos. During the 2009 excavations of a major Mycenaean city at Iklaina, a striking fragment of a wall-paintings came to light, depicting a ship with three human figures and dolphins. Dolphins are common in Greek mythology, and many coins from ancient Greece have been found which feature a man, a boy or a deity riding on the back of a dolphin. The Ancient Greeks welcomed dolphins; spotting dolphins riding in a ship's wake was considered a good omen. In both ancient and later art, Cupid is often shown riding a dolphin.",
                    "_readability": {
                        "metrics": {
                            "ARI": 10.935437,
                            "CL": 13.368853,
                            "DC": 9.9515085,
                            "FK": 9.169508,
                            "FRE": 65.53765,
                            "SMOG": 9.299571
                        },
                        "numAdjectives": 14,
                        "numAdverbs": 3,
                        "numBasicWords": 81,
                        "numChars": 709,
                        "numLetters": 575,
                        "numModals": 0,
                        "numNouns": 37,
                        "numParticipleVerbs": 13,
                        "numPolysylWords": 7,
                        "numPreposConjunctions": 23,
                        "numPronouns": 0,
                        "numPunctuation": 14,
                        "numSentences": 6,
                        "numSyllables": 174,
                        "numUniqWords": 79,
                        "numVerbs": 10,
                        "numWords": 122,
                        "txt": "In Greek myths, dolphins were seen invariably as helpers of humankind. Dolphins also seem to have been important to the Minoans, judging by artistic evidence from the ruined palace at Knossos. During the 2009 excavations of a major Mycenaean city at Iklaina, a striking fragment of a wall-paintings came to light, depicting a ship with three human figures and dolphins. Dolphins are common in Greek mythology, and many coins from ancient Greece have been found which feature a man, a boy or a deity riding on the back of a dolphin. The Ancient Greeks welcomed dolphins; spotting dolphins riding in a ship's wake was considered a good omen. In both ancient and later art, Cupid is often shown riding a dolphin."
                    }
                },
                "_type": "_doc"
            }
        }
    ]
}


```


