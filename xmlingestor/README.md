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

After modifying POM.xml, check:  

```
cd xmlingestor

mvn dependency:tree -Dverbose


[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building xmlingestor 1.0
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-dependency-plugin:2.8:tree (default-cli) @ xmlingestor ---
[INFO] org.elasticsearch.ingest:xmlingestor:jar:1.0
[INFO] +- org.elasticsearch:elasticsearch:jar:7.12.1:provided
[INFO] |  +- org.elasticsearch:elasticsearch-core:jar:7.12.1:provided
[INFO] |  +- org.elasticsearch:elasticsearch-secure-sm:jar:7.12.1:provided
[INFO] |  +- org.elasticsearch:elasticsearch-x-content:jar:7.12.1:provided
[INFO] |  |  +- (org.elasticsearch:elasticsearch-core:jar:7.12.1:provided - omitted for duplicate)
[INFO] |  |  +- org.yaml:snakeyaml:jar:1.26:provided
[INFO] |  |  +- com.fasterxml.jackson.core:jackson-core:jar:2.10.4:provided
[INFO] |  |  +- com.fasterxml.jackson.dataformat:jackson-dataformat-smile:jar:2.10.4:provided
[INFO] |  |  |  \- (com.fasterxml.jackson.core:jackson-core:jar:2.10.4:provided - omitted for duplicate)
[INFO] |  |  +- com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:jar:2.10.4:provided
[INFO] |  |  |  +- com.fasterxml.jackson.core:jackson-databind:jar:2.10.4:provided
[INFO] |  |  |  |  +- com.fasterxml.jackson.core:jackson-annotations:jar:2.10.4:provided
[INFO] |  |  |  |  \- (com.fasterxml.jackson.core:jackson-core:jar:2.10.4:provided - omitted for duplicate)
[INFO] |  |  |  +- (org.yaml:snakeyaml:jar:1.26:provided - omitted for duplicate)
[INFO] |  |  |  \- (com.fasterxml.jackson.core:jackson-core:jar:2.10.4:provided - omitted for duplicate)
[INFO] |  |  \- com.fasterxml.jackson.dataformat:jackson-dataformat-cbor:jar:2.10.4:provided
[INFO] |  |     \- (com.fasterxml.jackson.core:jackson-core:jar:2.10.4:provided - omitted for duplicate)
[INFO] |  +- org.elasticsearch:elasticsearch-geo:jar:7.12.1:provided
[INFO] |  +- org.apache.lucene:lucene-core:jar:8.8.0:provided
[INFO] |  +- org.apache.lucene:lucene-analyzers-common:jar:8.8.0:provided
[INFO] |  |  \- (org.apache.lucene:lucene-core:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  +- org.apache.lucene:lucene-backward-codecs:jar:8.8.0:provided
[INFO] |  |  \- (org.apache.lucene:lucene-core:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  +- org.apache.lucene:lucene-grouping:jar:8.8.0:provided
[INFO] |  |  +- (org.apache.lucene:lucene-core:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  |  \- (org.apache.lucene:lucene-queries:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  +- org.apache.lucene:lucene-highlighter:jar:8.8.0:provided
[INFO] |  |  +- (org.apache.lucene:lucene-core:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  |  +- (org.apache.lucene:lucene-memory:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  |  \- (org.apache.lucene:lucene-queries:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  +- org.apache.lucene:lucene-join:jar:8.8.0:provided
[INFO] |  |  \- (org.apache.lucene:lucene-core:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  +- org.apache.lucene:lucene-memory:jar:8.8.0:provided
[INFO] |  |  \- (org.apache.lucene:lucene-core:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  +- org.apache.lucene:lucene-misc:jar:8.8.0:provided
[INFO] |  |  \- (org.apache.lucene:lucene-core:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  +- org.apache.lucene:lucene-queries:jar:8.8.0:provided
[INFO] |  |  \- (org.apache.lucene:lucene-core:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  +- org.apache.lucene:lucene-queryparser:jar:8.8.0:provided
[INFO] |  |  +- (org.apache.lucene:lucene-core:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  |  +- (org.apache.lucene:lucene-queries:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  |  \- (org.apache.lucene:lucene-sandbox:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  +- org.apache.lucene:lucene-sandbox:jar:8.8.0:provided
[INFO] |  |  \- (org.apache.lucene:lucene-core:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  +- org.apache.lucene:lucene-spatial-extras:jar:8.8.0:provided
[INFO] |  |  +- (org.apache.lucene:lucene-core:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  |  +- (org.apache.lucene:lucene-spatial3d:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  |  +- io.sgr:s2-geometry-library-java:jar:1.0.0:provided
[INFO] |  |  \- org.locationtech.spatial4j:spatial4j:jar:0.7:provided
[INFO] |  +- org.apache.lucene:lucene-spatial3d:jar:8.8.0:provided
[INFO] |  |  \- (org.apache.lucene:lucene-core:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  +- org.apache.lucene:lucene-suggest:jar:8.8.0:provided
[INFO] |  |  +- (org.apache.lucene:lucene-analyzers-common:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  |  \- (org.apache.lucene:lucene-core:jar:8.8.0:provided - omitted for duplicate)
[INFO] |  +- org.elasticsearch:elasticsearch-cli:jar:7.12.1:provided
[INFO] |  |  +- net.sf.jopt-simple:jopt-simple:jar:5.0.2:provided
[INFO] |  |  \- (org.elasticsearch:elasticsearch-core:jar:7.12.1:provided - omitted for duplicate)
[INFO] |  +- com.carrotsearch:hppc:jar:0.8.1:provided
[INFO] |  +- joda-time:joda-time:jar:2.10.4:provided
[INFO] |  +- com.tdunning:t-digest:jar:3.2:provided
[INFO] |  +- org.hdrhistogram:HdrHistogram:jar:2.1.9:provided
[INFO] |  +- (org.apache.logging.log4j:log4j-api:jar:2.11.1:provided - omitted for duplicate)
[INFO] |  +- org.elasticsearch:jna:jar:5.7.0-1:provided
[INFO] |  \- org.elasticsearch:elasticsearch-plugin-classloader:jar:7.12.1:provided
[INFO] +- org.apache.logging.log4j:log4j-api:jar:2.11.1:provided
[INFO] \- org.json:json:jar:20210307:compile
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------


#or use the maven-enforcer-plugin :
mvn verify

```

## Develop


Compile and package :

```
mvn clean install

tree .
.
├── pom.xml
├── README.md
├── src
│   └── main
│       ├── assemblies
│       │   └── plugin.xml
│       ├── java
│       │   └── org
│       │       └── elasticsearch
│       │           └── ingest
│       │               ├── xmlingestorPlugin.java
│       │               └── XMLProcessor.java
│       └── plugin-metadata
│           └── plugin-descriptor.properties
└── target
    ├── archive-tmp
    ├── classes
    │   └── org
    │       └── elasticsearch
    │           └── ingest
    │               ├── xmlingestorPlugin.class
    │               ├── XMLProcessor.class
    │               └── XMLProcessor$Factory.class
    ├── generated-sources
    │   └── annotations
    ├── maven-archiver
    │   └── pom.properties
    ├── maven-status
    │   └── maven-compiler-plugin
    │       └── compile
    │           └── default-compile
    │               ├── createdFiles.lst
    │               └── inputFiles.lst
    ├── releases
    │   └── xmlingestor-1.0.zip
    ├── xmlingestor-1.0.jar
    └── xmlingestor-1.0-sources.jar

22 directories, 15 files


```

then inspect the jar and the zip:

```

jar tfv target/xmlingestor-1.0.jar 


     0 Thu Jun 17 11:53:26 EEST 2021 META-INF/
   133 Thu Jun 17 11:53:24 EEST 2021 META-INF/MANIFEST.MF
     0 Thu Jun 17 11:53:24 EEST 2021 org/
     0 Thu Jun 17 11:53:24 EEST 2021 org/elasticsearch/
     0 Thu Jun 17 11:53:24 EEST 2021 org/elasticsearch/ingest/
  1199 Thu Jun 17 11:53:24 EEST 2021 org/elasticsearch/ingest/xmlingestorPlugin.class
  1592 Thu Jun 17 11:53:24 EEST 2021 org/elasticsearch/ingest/XMLProcessor$Factory.class
  1668 Thu Jun 17 11:53:24 EEST 2021 org/elasticsearch/ingest/XMLProcessor.class
     0 Thu Jun 17 11:53:26 EEST 2021 META-INF/maven/
     0 Thu Jun 17 11:53:26 EEST 2021 META-INF/maven/org.elasticsearch.ingest/
     0 Thu Jun 17 11:53:26 EEST 2021 META-INF/maven/org.elasticsearch.ingest/xmlingestor/
  3650 Thu Jun 17 11:52:54 EEST 2021 META-INF/maven/org.elasticsearch.ingest/xmlingestor/pom.xml
   119 Thu Jun 17 11:53:26 EEST 2021 META-INF/maven/org.elasticsearch.ingest/xmlingestor/pom.properties



less target/releases/xmlingestor-1.0.zip

Archive:  target/releases/xmlingestor-1.0.zip
 Length   Method    Size  Cmpr    Date    Time   CRC-32   Name
--------  ------  ------- ---- ---------- ----- --------  ----
     177  Defl:N      109  38% 2021-06-17 11:08 45a392fe  plugin-descriptor.properties
   69710  Defl:N    66414   5% 2021-05-15 00:20 ad78057c  json-20210307.jar
    5171  Defl:N     3829  26% 2021-06-17 11:53 0eee84ac  xmlingestor-1.0.jar
--------          -------  ---                            -------
   75058            70352   6%                            3 files


```

## Deploy
 


```
export GCP_PROJ=$(gcloud config get-value project)
echo $GCP_PROJ

gcloud compute scp --project $GCP_PROJ \
                   --zone us-west2-c \
                   target/releases/xmlingestor-1.0.zip  \
                   elasticsearch-1-vm:~/


gcloud beta compute ssh --project "$GCP_PROJ" \
                        --zone "us-west2-c" "elasticsearch-1-vm" 

```

Now, on the VM execute:

```

sudo su

cd /opt/bitnami/elasticsearch

bin/elasticsearch-plugin remove xmlingestor

/opt/bitnami/ctlscript.sh restart

bin/elasticsearch-plugin install file:///home/camelia/xmlingestor-1.0.zip 

/opt/bitnami/ctlscript.sh restart

bin/elasticsearch-plugin list | grep xmlingestor

#----------------------------

tree /opt/bitnami/elasticsearch/plugins/xmlingestor

/opt/bitnami/elasticsearch/plugins/xmlingestor
├── json-20210307.jar
├── plugin-descriptor.properties
└── xmlingestor-1.0.jar

0 directories, 3 files

exit
```

## ES Pipelines to test the custom plugin

Prepare custom pipeline that includes our xml_processor and a json processor:

```
cat << EOF > /tmp/pipeline1.json
{
    "description": "test xml ingest plugin",
    "processors": [
        {
            "xml_processor" :{
                "srcfield" : "_doc_xml"
            },
            "json": {
                "field" : "_converted_json",
                "add_to_root" : true
            }
        }
    ]
}
EOF

#-----------------------------------------------------------------------

curl -H "Content-Type: application/json"  \
     -XPUT "localhost:9200/_ingest/pipeline/func_xml" \
     -d @/tmp/pipeline1.json   \
     |  python -m json.tool


  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   306  100    21  100   285    118   1610 --:--:-- --:--:-- --:--:--  1728
{
    "acknowledged": true
}


```

Prepare a test doc to simulate ingestion:

```
cat << EOF > /tmp/doc1.json
{
    "docs": [
        {
            "_index": "index",
            "_id": "id",
            "_source": {
                    "_doc_xml" : " \
                    <testcomplex> \
                        <a> 100 </a> \
                        <b> <![CDATA[Some text]]> </b> \
                        <c> Some other text </c> \
                        <d> \
                           <e> \
                              <f1> 11.1 </f1> \
                              <f2> 100000000000 </f2> \
                           </e>  \
                           <e>   \
                              <f1> 99.9 </f1> \
                              <f2> 999999999999 </f2> \
                           </e>  \
                        </d>  \
                        <t>2021-06-17 12:00:00</t> \
                        <empty/>  \
                    </testcomplex>"
            }
        }
    ]
}
EOF

#-----------------------------------------------------------------------

curl -H "Content-Type: application/json"  \
     -XPOST "localhost:9200/_ingest/pipeline/func_xml/_simulate" \
     -d @/tmp/doc1.json   \
     |  python -m json.tool
```

The result:
```
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100  2068  100  1221  100   847   108k  77000 --:--:-- --:--:-- --:--:--  201k
{
    "docs": [
        {
            "doc": {
                "_id": "id",
                "_index": "index",
                "_ingest": {
                    "timestamp": "2021-06-17T09:48:10.80568Z"
                },
                "_source": {
                    "_converted_json": "{\"testcomplex\":{\"a\":100,\"b\":\"Some text\",\"c\":\"Some other text\",\"d\":{\"e\":[{\"f1\":11.1,\"f2\":100000000000},{\"f1\":99.9,\"f2\":999999999999}]},\"t\":\"2021-06-17 12:00:00\",\"empty\":\"\"}}",
                    "_doc_xml": "                     <testcomplex>                         <a> 100 </a>                         <b> <![CDATA[Some text]]> </b>                         <c> Some other text </c>                         <d>                            <e>                               <f1> 11.1 </f1>                               <f2> 100000000000 </f2>                            </e>                             <e>                                 <f1> 99.9 </f1>                               <f2> 999999999999 </f2>                            </e>                          </d>                          <t>2021-06-17 12:00:00</t>                         <empty/>                      </testcomplex>",
                    "testcomplex": {
                        "a": 100,
                        "b": "Some text",
                        "c": "Some other text",
                        "d": {
                            "e": [
                                {
                                    "f1": 11.1,
                                    "f2": 100000000000
                                },
                                {
                                    "f1": 99.9,
                                    "f2": 999999999999
                                }
                            ]
                        },
                        "empty": "",
                        "t": "2021-06-17 12:00:00"
                    }
                },
                "_type": "_doc"
            }
        }
    ]
}


```

We can check in this result the transformation: "_doc_xml" --> "_converted_json" --> "testcomplex"  




