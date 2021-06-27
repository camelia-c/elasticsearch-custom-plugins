package org.elasticsearch.ingest;


import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assume;


import java.util.*;
import java.lang.reflect.Field;


@RunWith(JUnitParamsRunner.class)
public class TestReadabilityMetrics {

    static ReadabilityMetrics rmet; 

    @BeforeClass
    public static void setUp() {
        rmet = new ReadabilityMetrics(); 

    }


    @Test
    @Parameters(method = "generateValues")
    public void testMetrics(Map<String, Object> mapInputs) {

        MetricsResult mrExpected = new MetricsResult(mapInputs);

	System.out.println("========= METRICS TEST ===============");
        System.out.println("Expected:");
        System.out.println(mrExpected);
        

        MetricsResult mrResult =  rmet.end2endProcessing(mrExpected.txt);
        System.out.println("Computed:");
        System.out.println(mrResult);

        assertThat(mrResult).isNotNull();

        //softassert to ignore failed assertions in a group of assertions
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(mrResult.numSentences).as("numSentences").isEqualTo(mrExpected.numSentences);
        softly.assertThat(mrResult.numWords).as("numWords").isEqualTo(mrExpected.numWords);
        softly.assertThat(mrResult.numSyllables).as("numSyllables").isEqualTo(mrExpected.numSyllables);
        softly.assertThat(mrResult.numBasicWords).as("numBasicWords").isEqualTo(mrExpected.numBasicWords);

        
        //only if the metrics were computed ( >= 5 words in input text)
        if (mrResult.numWords >= 5) {

             for (Map.Entry<String, Object> entry : mapInputs.entrySet()){
                 String k = entry.getKey();
                 if(k.equals(k.toUpperCase()) == true){

                         float roundedMetricValue = (float) (Math.round(mrResult.metrics.get(k) * 100.0) / 100.0);
                         softly.assertThat(roundedMetricValue).as(k).isEqualTo(mrExpected.metrics.get(k));
		         
                 }

             }

         }

        softly.assertAll();

    }

   
    private Object[] generateValues() {

          String t1, t2, t3, t4, t5;
          Map<String, Object> m1, m2, m3, m4, m5;

          t1 = "Elasticsearch is the distributed search and analytics engine at the heart of the Elastic Stack. Elasticsearch is where the indexing, search, and analysis magic happens. It provides scalable " +
	 "search, has near real-time search, and supports multitenancy. Elasticsearch provides near real-time search and analytics for all types of data. Whether you have structured or unstructured text, " + 
	 "numerical data, or geospatial data, Elasticsearch can efficiently store and index it in a way that supports fast searches. You can go far beyond simple data retrieval and aggregate information to " +   
	 "discover trends and patterns in your data.";

	  t2 = "Readability is the ease with which a reader can understand a written text. In natural language, the readability of text depends on its content (the complexity of its vocabulary and syntax) " +
	 "and its presentation (such as typographic aspects that affect legibility, like font size, line height, character spacing, and line length).";

	  t3 = "A smart city is an urban area that uses different types of electronic methods and sensors to collect data. Insights gained from that data are used to manage assets, " + 
	 "resources and services efficiently; in return, that data is used to improve the operations across the city. This includes data collected from citizens, devices, buildings and assets that " + 
	 "is then processed and analyzed to monitor and manage traffic and transportation systems, power plants, utilities, water supply networks, waste, crime detection, information systems, schools, " +
	 "libraries, hospitals, and other community services.";

	  t4 = "A city is a place where many people live closely together. City life has many benefits. Cities bring together a great variety of people from different backgrounds. " +
	 "They offer more jobs, more schools, and more kinds of activities than smaller towns and villages. But cities also can be dangerous and polluted. A city’s central business district, " +
	 "or downtown, usually has its tallest office buildings and biggest stores. The downtown area is often the oldest part of the city. A city usually has one or more areas of factories and warehouses " +
	 "(storage buildings) outside of downtown. Most of the city’s homes lie still farther from downtown.";

          t5 = "Insufficient words number here.";

          //we'll compare with the results from the R library (see Jupyter notebook)
          m1 = new HashMap<String, Object>(){{
                put("txt", t1);
                put("numSentences", 6);
                put("numWords", 93);
                put("numSyllables", 167);
                put("numBasicWords", 93 -38);
                put("ARI", 12.50f);
                put("FK", 11.64f);
                put("FRE", 39.18f);
                put("CL", 14.97f);
                put("DC", 10.85f);
                put("SMOG", 13.55f);
          }};

          m2 = new HashMap<String, Object>(){{
                put("txt", t2);
                put("numSentences", 2);
                put("numWords", 51);
                put("numSyllables", 91);
                put("numBasicWords", 51 -17);
                put("ARI", 15.97f);
                put("FK", 15.40f);
                put("FRE", 29.99f);
                put("CL", 13.82f);
                put("DC", 10.16f);
                put("SMOG", 15.90f);
          }};

          m3 = new HashMap<String, Object>(){{
                put("txt", t3);
                put("numSentences", 3);
                put("numWords", 88);
                put("numSyllables", 165);
                put("numBasicWords", 88 -36);
                put("ARI", 19.40f);
                put("FK", 17.97f);
                put("FRE", 18.43f);
                put("CL", 15.86f);
                put("DC", 11.55f);
                put("SMOG", 17.87f);
          }};

          m4 = new HashMap<String, Object>(){{
                put("txt", t4);
                put("numSentences", 9);
                put("numWords", 105);
                put("numSyllables", 173);
                put("numBasicWords", 105 -13);
                put("ARI", 7.68f);
                put("FK", 8.40f);
                put("FRE", 55.60f);
                put("CL", 10.72f);
                put("DC", 6.17f);
                put("SMOG", 10.50f);
          }};


          m5 = new HashMap<String, Object>();
          m5.put("txt", t5);
          m5.put("numSentences", 1);
          m5.put("numWords", 4);
          m5.put("numPunctuation", 1);

         
          return new Object[]{
                 new Object[]{m1},
                 new Object[]{m2},
                 new Object[]{m3},
                 new Object[]{m4},
                 new Object[]{m5}
         };
    }




}
