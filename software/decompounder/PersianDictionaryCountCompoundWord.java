/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.ifs.myluceneanalyzers.fa.algorithm;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.compound.DictionaryCompoundWordTokenFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import at.ac.tuwien.ifs.myluceneanalyzers.fa.PersianStemFilter;

/**
 *
 * @author navid rekabsaz
 */
public class PersianDictionaryCountCompoundWord {

	private Map<String, Double> mapWordCount;
	private CharArraySet dictionary;
	int minWordSize;
	int minSubwordSize;
	int maxSubwordSize;
	Attribute attributeHermmean;
	Instances instances;
	Classifier cls;
	
	/**
   * Creates a new {@link PersianDictionaryCountCompoundWord}. Unlike {@link DictionaryCompoundWordTokenFilter} it considers
   * onlyLongestMatch to be true and it will only return subwords of maximal size. <br/>
   * Example: "moonlight" will be returned as "moonlight" only if it is in the dictionary (not as "moonlight, light" as 
   * the DictionaryCompoundWordTokenFilter with onlyLongestMatch=true would.
   * 
   * @param input
   *          the {@link TokenStream} to process
   * @param dictionary
   *          the word dictionary to match against.
   * @param minWordSize
   *          only words longer than this get processed
   * @param minSubwordSize
   *          only subwords longer than this get to the output stream
   * @param maxSubwordSize
   *          only subwords shorter than this get to the output stream
	 * @throws Exception 
   */
  	public PersianDictionaryCountCompoundWord(CharArraySet dictionary, Map<String, Double> mapWordCount,
 		   int minWordSize, int minSubwordSize, int maxSubwordSize) throws Exception {
  		if (dictionary == null) {
 		   throw new IllegalArgumentException("dictionary cannot be null");
  		}
  		this.dictionary=dictionary;
 	   	this.mapWordCount= mapWordCount;
  		this.minWordSize=minWordSize;
  		this.minSubwordSize=minSubwordSize;
  		this.maxSubwordSize=maxSubwordSize;
  		
  		// Create the attributes
        attributeHermmean = new Attribute("harmmean"); 
        
        // Declare the class attribute along with its values
        FastVector fvClassVal = new FastVector(2);
        fvClassVal.addElement("1");
        fvClassVal.addElement("0");
        Attribute classAttribute = new Attribute("iscorrect", fvClassVal);
        
        // Create list of instances with one element 
        FastVector fvWekaAttributes = new FastVector(2); 
        fvWekaAttributes.addElement(attributeHermmean); 
        fvWekaAttributes.addElement(classAttribute);
        
        
        instances = new Instances("Test relation", fvWekaAttributes, 1); 
        instances.setClassIndex(1); 
        
        cls = (Classifier) weka.core.SerializationHelper.read("content/adtree.model");
        
   	}

    /**
     * Same as {@link DictionaryCompoundWordTokenFilter#decompose()} except if it found a match, it moves the pointer to the end of it
     * @throws Exception 
     */
    public List<String> decompose(String token) throws Exception {
    	List<String> result = new ArrayList<String>();
    	List<String> bestTokens = new ArrayList<String>();
    	
    	if (!dictionary.contains(token))
    	{
			if (!dictionary.contains(stem(token)))
			{
	    		final int len = token.length();
	    		int i = 0;
	            List<ArrayList<String>> listMatchTokens = new ArrayList<ArrayList<String>>();
	            for (int j = this.minSubwordSize; j <= len - this.minSubwordSize; ++j) {
	                if (i + j > len) {
	                    break;
	                }
	                if (dictionary.contains(token.substring(i, j)) && dictionary.contains(token.substring(j, len))) {
	            		String matchToken1 = token.substring(i, j);
	            		String matchToken2 = token.substring(j, len);
	            		ArrayList<String> tokens = new ArrayList<String>();
	            		tokens.add(matchToken1);
	            		tokens.add(matchToken2);
	            		listMatchTokens.add(tokens);
	            	}
	            }
	            
	            Double maxcountavg=mapWordCount.get(token.toString());
	        	for (ArrayList<String> matchTokens : listMatchTokens){
	            	List<Double> wordCounts = new ArrayList<Double>();
	            	for (String matchToken : matchTokens){
	            		Double wordCount = mapWordCount.get(matchToken);
	                	if (wordCount == null)
	                		wordCount=0.0;
	                	
	                	wordCounts.add(wordCount);
	                }
	            	
	            	//mean
	            	double sum = 0.0;
	                for(Double a : wordCounts)
	                    sum += a;
	                Double countmean= (sum/matchTokens.size());
	                //var
	                double temp = 0;
	                for(Double a : wordCounts)
		                    temp += (countmean-a)*(countmean-a);
	                Double countvar=Math.sqrt((temp/matchTokens.size()));
	                //harmmean
	                Double countharmmean=0.0;
	                if ((wordCounts.get(0) != 0) && (wordCounts.get(1) != 0))
	                	countharmmean=((2*wordCounts.get(0)*wordCounts.get(1))/(wordCounts.get(0)+wordCounts.get(1)));
	                
	                
	                //check with the max
	             	if (maxcountavg <= countmean){
	            		bestTokens.clear();
	            		for (String matchToken : matchTokens){
	            			bestTokens.add(matchToken);
		            	}
	            		bestTokens.add(String.valueOf((int)Math.floor(countmean)));
	            		bestTokens.add(String.valueOf((int)Math.floor(countvar)));
	            		bestTokens.add(String.valueOf((int)Math.floor(countharmmean)));
	            		maxcountavg = countmean;
	            	}
	            }					
			}
		}
    	if (bestTokens.size() > 0){
    		//check the classifier
            //Evaluation eTest = new Evaluation(instances);
            //eTest.evaluateModel(cls, instances);
            //String strSummary = eTest.toSummaryString();
            //System.out.println(strSummary);
    		
    		Instance instance = new Instance(2); 
            int harmmean = Integer.valueOf(bestTokens.get(bestTokens.size()-1));
            instance.setValue(attributeHermmean, harmmean); 
            instances.add(instance);
            
            double value=cls.classifyInstance(instances.instance(0));
            String prediction=instances.classAttribute().value((int)value); 
            
            if (prediction.equals("1")){
            	result = bestTokens;
            }
    	}
    	
    	return result;
    }
    
    @SuppressWarnings({ "resource", "deprecation" })
	private String stem(String input) throws IOException
    {
    	String output = "";
    	Reader reader = new StringReader(input);
		Tokenizer source = new StandardTokenizer(Version.LUCENE_4_10_3, reader);
		TokenStream tokenStream = new PersianStemFilter(source);
		
		CharTermAttribute charTermAttributeGreedy = tokenStream.getAttribute(CharTermAttribute.class);
	    tokenStream.reset();
    	while (tokenStream.incrementToken()) {
    		output = output + " " + charTermAttributeGreedy.toString();
	    	
	    }
    	return output.trim();
    }
}
