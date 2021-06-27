package org.elasticsearch.ingest;

import edu.stanford.nlp.ling.*;
import java.util.*;
import java.lang.reflect.Field;

public class MetricsResult{

         public String txt;
         
         //transient because we don't want this field serialized
         public transient List<CoreLabel> listTokens; 
         public int numSentences = 0;

         public int numWords = 0;
         public int numUniqWords = 0;
         public int numBasicWords = 0;

         public int numChars = 0;
         public int numLetters = 0;
         public int numPunctuation = 0;

         public int numSyllables = 0;
         public int numPolysylWords = 0;

         public int numNouns = 0;
         public int numVerbs = 0;
         public int numParticipleVerbs = 0;
         public int numModals = 0;
         public int numAdverbs = 0;
         public int numAdjectives = 0;
         public int numPronouns = 0;
         public int numPreposConjunctions = 0;        

         public Map<String, Float> metrics; 


         public MetricsResult(String s){
            this.txt = s;
            this.numChars = s.length();
            this.metrics = new HashMap<String, Float>();

         }

         public MetricsResult(Map<String,Object> mapInputs){
            //constructor using input map with mixed types, used in testing
            this.txt = (String)mapInputs.get("txt");
            this.metrics = new HashMap<String, Float>();

            //loop and set public fields values only for keys present in map
            for (Map.Entry<String, Object> entry : mapInputs.entrySet()){
                 String k = entry.getKey();
                 Object v = entry.getValue();

                 if (k != "txt"){
                        try{
		             //reflection recap https://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Class.html#getField(java.lang.String)
		               
                             if(k.equals(k.toUpperCase()) == true){
                                    //if only capital letters in k, then it's a metric name
                                    float vf = (float)v;
                                    this.metrics.put(k, vf );
                               }
                               else {
                                     //else it's counter
                                     Field field = this.getClass().getField(k);
                                     field.set(this, (int)v );
                               }
		            
		         }
		         catch (Exception e) {
		             System.out.println(e);
		         }
                 }//if
             }//for 
            
         }
          


         @Override
         public String toString() {
             StringBuilder sb = new StringBuilder("");
             sb.append("TEXT:").append(this.txt).append("\n");
             
             sb.append("Sent:").append(this.numSentences).append("\t");    
             sb.append("Word:").append(this.numWords).append("\t"); 
             sb.append("uniqW:").append(this.numUniqWords).append("\t");
             sb.append("basicW:").append(this.numBasicWords).append("\t");
             sb.append("polysylW:").append(this.numPolysylWords).append("\t");
             sb.append("Syl:").append(this.numSyllables).append("\t"); 
             sb.append("Lett:").append(this.numLetters).append("\t");                 
             sb.append("Char:").append(this.numChars).append("\n"); 

             sb.append("Nouns:").append(this.numNouns).append("\t");    
             sb.append("Prons:").append(this.numPronouns).append("\t"); 
             sb.append("Vbs:").append(this.numVerbs).append("\t");
             sb.append("VbsPart:").append(this.numParticipleVerbs).append("\t"); 
             sb.append("Mods:").append(this.numModals).append("\t"); 
             sb.append("Adj:").append(this.numAdjectives).append("\t");                 
             sb.append("Advs:").append(this.numAdverbs).append("\t"); 
             sb.append("Preps:").append(this.numPreposConjunctions).append("\t");
             sb.append("Pcts:").append(this.numPunctuation).append("\n");
            
             for (Map.Entry<String, Float> entry : this.metrics.entrySet()){
                 sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\t");
             }

             sb.append("\n-----------------------------------------------------------------\n");
             String s = sb.toString();
             return s;
         }
    } 
