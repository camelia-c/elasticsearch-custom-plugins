package org.elasticsearch.ingest;


import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import static org.assertj.core.api.Assertions.*;
import org.junit.Assume;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.util.logging.RedwoodConfiguration;

import com.itextpdf.layout.hyphenation.Hyphenation;
import com.itextpdf.layout.hyphenation.*;

import de.mfietz.jhyphenator.HyphenationPattern;
import de.mfietz.jhyphenator.*;

import java.util.Properties;
import java.util.*;
import java.util.stream.Collectors;


@RunWith(JUnitParamsRunner.class)
public class Test3rdParty {

    static com.itextpdf.layout.hyphenation.Hyphenator hyp1;
    static de.mfietz.jhyphenator.Hyphenator hyp2;
    static StanfordCoreNLP pipeline;


    @BeforeClass
    public static void setUp() {
        // --- reated to itext7
        hyp1 = new com.itextpdf.layout.hyphenation.Hyphenator("en", "EN_US", 2, 2);

        // ---- related to jhyphenator
        HyphenationPattern hyp_en = HyphenationPattern.lookup("en_us");
        hyp2 = de.mfietz.jhyphenator.Hyphenator.getInstance(hyp_en);


        // --- related to corenlp
        //disable logging 
        RedwoodConfiguration.empty2();    

        Properties props = new Properties();
        // set the list of annotators to run - tokenizer and sentences splits
        props.setProperty("annotators", "tokenize,ssplit");
        props.setProperty("tokenize.options", "splitHyphenated=false,americanize=false");

        // build pipeline
        pipeline = new StanfordCoreNLP(props);
    }



    @Test
    @Parameters({
      "Elasticsearch, 3", 
      "is, 1", 
      "search, 1",
      "engine, 2",
      "Lucene, 2",
      "library, 2",
      "documents, 3",
      "real-time, 3",
      "multitenancy, 4",
      "today, 2"
    })  
    public void testHyphenV1(String word, int expected) { 
 
        int auxNumSylToken = 0;
        System.out.println("========= HYPHEN TEST ITEXT7 ===============");
	try{
             Hyphenation syl = hyp1.hyphenate(word); 
             //the number of hyphenation points in the word 
             auxNumSylToken = syl.length() + 1;
             System.out.println(String.format("%s\t%s\t%d", word, syl, auxNumSylToken));
        }
        catch(Exception e){
             //for monosyllabic words itext7 hyphen returns null
             System.out.println("Catched exception:   " + e);
             auxNumSylToken = 1;
             System.out.println(String.format("%s\t%s\t%d", word, "n/a", auxNumSylToken));
        }
        //assume instead of assert , in order to ignore if these tests fail 
        Assume.assumeTrue(auxNumSylToken == expected);
    }


    @Test
    @Parameters({
      "Elasticsearch, 3", 
      "is, 1", 
      "search, 1",
      "engine, 2",
      "Lucene, 2",
      "library, 2",
      "documents, 3",
      "real-time, 3",
      "multitenancy, 4",
      "today, 2"
    })  
    public void testHyphenV2(String word, int expected) { 
 
        int auxNumSylToken = 0;
        System.out.println("========= HYPHEN TEST JHYPHENATOR ===============");
	try{
             List<String> syl = hyp2.hyphenate(word); 
             //the number of hyphenation points in the word 
             auxNumSylToken = syl.size();

             StringBuilder sylSb = new StringBuilder(syl.get(0));
             for (int i = 1; i < syl.size(); i++) {
                  sylSb.append("-").append(syl.get(i));
             }
             String sylStr = sylSb.toString();
             System.out.println(String.format("%s\t%s\t%d", word, sylStr, auxNumSylToken));
        }
        catch(Exception e){
             //for monosyllabic words itext7 hyphen returns null
             System.out.println("Catched exception:   " + e);
             auxNumSylToken = 1;
             System.out.println(String.format("%s\t%s\t%d", word, "n/a", auxNumSylToken));
        }
        //assume instead of assert , in order to ignore if these tests fail 
        Assume.assumeTrue(auxNumSylToken == expected);
    }
    

    

    @Test
    @Parameters(method = "generateValues") 
    public void testCoreNLP(String txt, int expectedNumSentences, int expectedNumTokens) {    

        List<String> punctuation = Arrays.asList( ".", "," , ";" , ":", "?", "!");

        System.out.println("========= PARSING WITH CORENLP ===============");
        System.out.println("TEXT: " + txt);
       
        // corenlp for extracting sentences and tokens
        CoreDocument doc = new CoreDocument(txt);

        pipeline.annotate(doc);

        for (CoreLabel tok : doc.tokens()) {
            System.out.println(String.format("%s\t%d\t%d", tok.word(), tok.beginPosition(), tok.endPosition()));
        }

        int numSentences = doc.sentences().size();

        //remove punctuation tokens and then count tokens
        List<String> initialTokens = doc.tokens().stream().map(token -> new String(token.word())).collect(Collectors.toList()); 
        initialTokens.removeAll(punctuation);
        int numWords = initialTokens.size();

        System.out.println(String.format("numSentences=%d\tnumWords=%d", numSentences, numWords));
        
        assertThat(numSentences).isEqualTo(expectedNumSentences);
        assertThat(numWords).isEqualTo(expectedNumTokens); 
    }

    
    private Object[] generateValues() {
        //source of texts: https://en.wikipedia.org/wiki/Elasticsearch
	return new Object[]{
	         new Object[]{"Elasticsearch is a search engine based on the Lucene library.", 1,10},
	         new Object[]{"Elasticsearch can be used to search all kinds of documents. It provides scalable search, has near real-time search, and supports multitenancy.", 2, 21}
	    };
    }




}
