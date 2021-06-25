package org.elasticsearch.ingest;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.util.logging.RedwoodConfiguration;

//we decided to use JHyphenator
import de.mfietz.jhyphenator.HyphenationPattern;
import de.mfietz.jhyphenator.Hyphenator;

import org.elasticsearch.SpecialPermission;
import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.Properties;
import java.util.*;
import java.util.stream.Collectors;
import java.lang.Math;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class ReadabilityMetrics {


    private StanfordCoreNLP pipeline; 
    private Hyphenator hyp;
    private Map<String, String> basicWords;

    private SecurityManager sec;


    public ReadabilityMetrics(){

        this.sec = System.getSecurityManager();
        if (this.sec != null) {
	    //silently passses if permission is granted
	    this.sec.checkPermission(new SpecialPermission());
	}

        // --- related to corenlp
        //disable logging 
        RedwoodConfiguration.empty2();    

        Properties props = new Properties();
        // set the list of annotators to run - tokenizer and sentences splits
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma"); 
        props.setProperty("tokenize.options", "splitHyphenated=false,americanize=false");
        //pos tagger needs model (15 MB) from https://nlp.stanford.edu/software/tagger.shtml#Download stored in resources folder
        props.setProperty("pos.model", "english-left3words-distsim.tagger");

        //creating pos tagger baed on model requires priviledged mode to be used in ES plugin
        this.pipeline = AccessController.doPrivileged(new PrivilegedAction<StanfordCoreNLP>() {

	    public StanfordCoreNLP run() {
		// build pipeline
		return new StanfordCoreNLP(props);
	    }
        });        

        // ---- related to jhyphenator
        HyphenationPattern hyp_en = HyphenationPattern.lookup("en_us");
        this.hyp = Hyphenator.getInstance(hyp_en);

        //------ read file for DC metric
        this.basicWords = readResourceFile("DC3000.txt");
        
    }


    private Map<String, String> readResourceFile(String txtFilename){
        /** read file with basic words as of DC 3000 list, one per line
        **/

        String str = "";
        Map<String, String> res = new HashMap<String, String>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + txtFilename), StandardCharsets.UTF_8));
                                   
            while ((str = reader.readLine()) != null) {    
                    res.put(str,"");               
            }
        } catch (Exception e) {
            System.out.println("error opening file:" + e);
        }

        return res;
    } 


    private MetricsResult tokenizeText(MetricsResult mr){
         /** use CoreNLP pipeline created in the constructor to parse and annotate tokens
             see https://stanfordnlp.github.io/CoreNLP/tokenize.html
                 https://stanfordnlp.github.io/CoreNLP/ssplit.html
         **/
        String punctuations = ".,;:!?\"()";
 
        //recap : pass-by-value semantics, a copy of mr is modified 

        mr.listTokens = new ArrayList<CoreLabel>(); 

        // corenlp for extracting sentences and tokens
        CoreDocument doc = new CoreDocument(mr.txt);

        this.pipeline.annotate(doc);

        //we can already set number of sentences in the result
        mr.numSentences = doc.sentences().size();

        //remove symbols (punctuation) based on pos tags
        for (CoreLabel tok : doc.tokens()) {
            if ((tok.tag() != "SYM") && (punctuations.contains(tok.word()) == false)) {
                 mr.listTokens.add(tok);
            }
        }
        
        mr.numWords = mr.listTokens.size();
        mr.numPunctuation = doc.tokens().size() - mr.listTokens.size();

        return mr;    
    }


    private MetricsResult computeCounts(MetricsResult mr){
        Map<String, String> uniq = new HashMap<String, String>();
        String word = "";
        String lemma = "";
        String tag = "";

        for (CoreLabel tok : mr.listTokens) {
            word = tok.word();
            lemma = tok.lemma();
            tag = tok.tag();

            mr.numLetters += word.length();

            //verify if lemma in lisst of 3000 basic words
            if (this.basicWords.get(lemma) != null) {
                mr.numBasicWords += 1;
            }

            if (uniq.get(lemma) == null){
                uniq.put(lemma,"");
                mr.numUniqWords += 1;
            }

            /* source:   https://cs.nyu.edu/~grishman/jet/guide/PennPOS.html
                1.	CC	Coordinating conjunction
		2.	CD	Cardinal number
		3.	DT	Determiner
		4.	EX	Existential there
		5.	FW	Foreign word
	        6.	IN	Preposition or subordinating conjunction
		7.	JJ	Adjective
		8.	JJR	Adjective, comparative
		9.	JJS	Adjective, superlative
		10.	LS	List item marker
		11.	MD	Modal
		12.	NN	Noun, singular or mass
		13.	NNS	Noun, plural
		14.	NNP	Proper noun, singular
		15.	NNPS	Proper noun, plural
		16.	PDT	Predeterminer
		17.	POS	Possessive ending
		18.	PRP	Personal pronoun
		19.	PRP$	Possessive pronoun
		20.	RB	Adverb
		21.	RBR	Adverb, comparative
		22.	RBS	Adverb, superlative
		23.	RP	Particle
		24.	SYM	Symbol
		25.	TO	to
		26.	UH	Interjection
		27.	VB	Verb, base form
		28.	VBD	Verb, past tense
		29.	VBG	Verb, gerund or present participle
		30.	VBN	Verb, past participle
		31.	VBP	Verb, non-3rd person singular present
		32.	VBZ	Verb, 3rd person singular present
		33.	WDT	Wh-determiner
		34.	WP	Wh-pronoun
		35.	WP$	Possessive wh-pronoun
		36.	WRB	Wh-adverb

            */
            switch (tag) {
               case "CC":
               case "IN":
                   mr.numPreposConjunctions += 1;
                   break; 

               case "NN":
               case "NNS":
               case "NNP":
               case "NNPS":
                   mr.numNouns += 1;
                   break; 

               case "JJ":
               case "JJR":
               case "JJS":
                   mr.numAdjectives += 1;
                   break;

               case "PRP":
               case "PRP$":
               case "WP":
               case "WP$":
                   mr.numPronouns += 1;
                   break;
               
               case "MD":
                   mr.numModals += 1;
                   break;

               case "RB":
               case "RBR":
               case "RBS":
               case "WRB":
                   mr.numAdverbs += 1;
                   break;

               case "VB":
               case "VBD":
               case "VBP":
               case "VBZ":
                   mr.numVerbs += 1;
                   break;

               case "VBG":
               case "VBN":
                   mr.numParticipleVerbs += 1;
                   break;
                  
            }

        }

        return mr; 
    }


    private MetricsResult syllablesCounts(MetricsResult mr){

       String word = "";
       int auxNumSylToken = 0;

       for (CoreLabel tok : mr.listTokens) {
            word = tok.word();
            // hyphenation of each non-punctuation token
            try{
                List<String> syl = this.hyp.hyphenate(word); 
                //the number of hyphenation points in the word 
                auxNumSylToken = syl.size();
            }
            catch(Exception e){
                auxNumSylToken = 1;
            }

            mr.numSyllables += auxNumSylToken;

            if (auxNumSylToken > 2){
                mr.polysylWords += 1;
            }

       }
       return mr;

    }


    public MetricsResult computeMetrics(MetricsResult mr){

       //compute some quntities that appear frequently in formulas (note: at least one operand must be float, not both int)

       double wordsPerSentence = ((float) mr.numWords) / mr.numSentences;
       double charsPerWord = ((float) mr.numLetters) / mr.numWords;
       double syllablesPerWord = ((float) mr.numSyllables) / mr.numWords;
       double ratioDifficultWords = (mr.numWords - mr.numBasicWords) / ((float) mr.numWords);
       
       //Automated Readability Index (ARI) grade - see https://en.wikipedia.org/wiki/Automated_readability_index
       double ARI = 0.5 * wordsPerSentence + 4.71 * charsPerWord - 21.43;
       mr.metrics.put("ARI", (float)ARI);

       //Flesch–Kincaid grade - see https://en.wikipedia.org/wiki/Flesch%E2%80%93Kincaid_readability_tests#Flesch%E2%80%93Kincaid_grade_level
       double FK = 0.39 * wordsPerSentence + 11.8 * syllablesPerWord - 15.59;
       mr.metrics.put("FK", (float)FK);

       //Coleman–Liau grade - see https://en.wikipedia.org/wiki/Coleman%E2%80%93Liau_index
       double CL = 5.88 * charsPerWord + 29.6 / wordsPerSentence - 15.8;
       mr.metrics.put("CL", (float)CL);

       //SMOG grade - see https://en.wikipedia.org/wiki/SMOG
       double SMOG = 1.0430 * Math.sqrt(mr.polysylWords * 30/mr.numSentences) + 3.1291;
       mr.metrics.put("SMOG", (float)SMOG);
 
       //Flesch reading ease score - see https://en.wikipedia.org/wiki/Flesch%E2%80%93Kincaid_readability_tests#Flesch_reading_ease
       double FRE = 206.835 - 1.015 * wordsPerSentence - 84.6 * syllablesPerWord;
       mr.metrics.put("FRE", (float)FRE);

       //Dale–Chall readability score - see https://en.wikipedia.org/wiki/Dale%E2%80%93Chall_readability_formula 
       double DC = 0.0496 * wordsPerSentence + 15.79 * ratioDifficultWords;
       mr.metrics.put("DC", (float)DC);
 
       return mr;
    }


    public MetricsResult end2endProcessing(String s){
        /** end2end Readability metrics **/

        MetricsResult mr = new MetricsResult(s);

        MetricsResult mrStage1 = tokenizeText(mr); 

        MetricsResult mrStage2 = computeCounts(mrStage1);   

        MetricsResult mrStage3 = syllablesCounts(mrStage2);   

        MetricsResult mrStage4 = computeMetrics(mrStage3);   
        System.out.println(mrStage4);      

        return mr;        
    }

}
