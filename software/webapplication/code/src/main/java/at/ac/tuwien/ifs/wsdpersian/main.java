package at.ac.tuwien.ifs.wsdpersian;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String dbUrl;
		String username;
	    String password;
	    String dbClass = "com.mysql.jdbc.Driver";
	    
	    dbUrl = "jdbc:mysql://127.0.0.1/farsidb";
		username = "root";
	    password = "root";
		
	    Set<String> words = new HashSet<String>();
		try {

	        Class.forName(dbClass);
	        Connection connection = DriverManager.getConnection(dbUrl,
	            username, password);
	        Statement statement = connection.createStatement();
	        
	        String query = "select * from questions";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
            	String question = resultSet.getString("question");
            	question = question.replaceAll("<head>", "").toLowerCase();
            	question = question.replaceAll("<\\head>", "");
            	question = question.replaceAll("[0-9]","");
            	List<String> tokens = tokenize(question);
            	
            	for (String token : tokens) {
            		words.add(token);			
        	    }
        		
            }
            
	        connection.close();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/navid/workspace/papers/04-emnlp15/data/dictionary/clwsd-dic.txt"), "utf-8"));
		List<String> wordsArray = new ArrayList<String>(words);
		Collections.sort(wordsArray);
		for (String word : wordsArray) {
			writer.write(word + "\n");
	    }
		writer.close();
		
	}
	
	@SuppressWarnings("resource")
	private static List<String> tokenize(String input) throws IOException
    {
    	List<String> tokens = new ArrayList<String>();
    	
    	input=input.replaceAll("ء", "");
    	Reader reader = new StringReader(input);
		Tokenizer source = new StandardTokenizer(Version.LUCENE_4_10_3, reader);
		TokenStream tokenStream = new LowerCaseFilter(source);
		
		CharTermAttribute charTermAttributeGreedy = tokenStream.getAttribute(CharTermAttribute.class);
	    tokenStream.reset();
    	while (tokenStream.incrementToken()) {
    		tokens.add(charTermAttributeGreedy.toString());
	    	
	    }
    	return tokens;
    }
}
