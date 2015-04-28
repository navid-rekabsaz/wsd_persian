package at.ac.tuwien.ifs.wsdpersian;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

@Path("/question")
public class QuestionController {
	
	String dbUrl;
	String username;
    String password;
    String dbClass = "com.mysql.jdbc.Driver";
    int allQuestionsNumber;
    
	public QuestionController() throws IOException{
		Properties prop = new Properties();
		String propFileName = "config.properties";
 
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		
		dbUrl = "jdbc:mysql://" + prop.getProperty("dbpath") + "/" + prop.getProperty("dbname");
		username = prop.getProperty("dbusername");
	    password = prop.getProperty("dbpassword");
	    
	    try {

	        Class.forName(dbClass);
	        Connection connection = DriverManager.getConnection(dbUrl,
	            username, password);
	        Statement statement = connection.createStatement();
	        
	        String query = "select count(*) cnt from questions";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
            	allQuestionsNumber = Integer.valueOf(resultSet.getString("cnt"));
            }
            
	        connection.close();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getnewquestiontest")
	public String getNewQuestionTest(@QueryParam("annotatorid") int annotatorId)
	{
		return "AnnotatorId : " + Integer.valueOf(annotatorId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getnewquestion")
	public Question getNewQuestion(@QueryParam("annotatorid") int annotatorId)
	{
		Question question = new Question();
		
		try {

	        Class.forName(dbClass);
	        Connection connection = DriverManager.getConnection(dbUrl,
	            username, password);
	        Statement statement = connection.createStatement();
	        
	        String query = "select q.id question_id, q.question sentence, w.id word_id, w.word word_name " +
	        		"from questions q inner join words w on q.word_id = w.id "+
					"where q.id not in " +
					"(select question_id from answer_master where annotator_id=" + Integer.valueOf(annotatorId) + ") " +
					"order by q.id limit 1";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
            	question.id = Integer.valueOf(resultSet.getString("question_id"));
            	question.sentence = resultSet.getString("sentence").replace("<head>", "<b>").replace("</head>", "</b>");
            	question.wordName = resultSet.getString("word_name");
            	int word_id = Integer.valueOf(resultSet.getString("word_id"));
            	question.allQuestionsNumber = allQuestionsNumber;
            	
            	query = "select count(*) cnt from answer_master where annotator_id=" + Integer.valueOf(annotatorId);
            	resultSet = statement.executeQuery(query);
            	if (resultSet.next()){
            		question.answeredQuestionsNumber = Integer.valueOf(resultSet.getString("cnt"));
                }
            	
            	query = "select annotator from annotators where id=" + Integer.valueOf(annotatorId);
            	resultSet = statement.executeQuery(query);
            	if (resultSet.next()){
            		question.annotatorName = resultSet.getString("annotator");
                }
            	
            	Statement clusterStatement = connection.createStatement();
            	query = "select id, cluster from clusters where word_id = " + Integer.valueOf(word_id);
            	ResultSet clusterresultSet = clusterStatement.executeQuery(query);
            	question.clusters=new ArrayList<Cluster>();
            	while (clusterresultSet.next()) {
    	        	Cluster cluster = new Cluster();
    	        	cluster.setId(Integer.valueOf(clusterresultSet.getString("id")));
    	        	cluster.setName(clusterresultSet.getString("cluster"));
    	        	
    	        	Statement translationStatement = connection.createStatement();
                	query = "select id, translation from translations where cluster_id = " + Integer.valueOf(cluster.id);
                	ResultSet translationresultSet = translationStatement.executeQuery(query);
                	cluster.translations=new ArrayList<Translation>();
                	while (translationresultSet.next()) {
        	        	Translation translation = new Translation();
        	        	translation.setId(Integer.valueOf(translationresultSet.getString("id")));
        	        	translation.setName(translationresultSet.getString("translation"));
        	        	
        	        	cluster.translations.add(translation);
        	        }	
                	
    	        	question.clusters.add(cluster);
    	        }
            	
            	
            }
            
	        connection.close();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	
		return question;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/hello")
	public String sayHello()
	{
		return "HELLO FACET WORLD!";
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveAnswer(Answer answer)
	{
		Response response = new Response();
		
		if (answer.translationIds.length>3){
			response.result = false;
	        response.message = "Selected translations should not be more than three!";
	        return response;
		}
		try {

	        Class.forName(dbClass);
	        Connection connection = DriverManager.getConnection(dbUrl,
	            username, password);
	        
	        Statement statement = connection.createStatement();
	        String query = "insert into answer_master (annotator_id, question_id) "+
					"values ('"+ Integer.valueOf(answer.annotatorId) + "', '"+ Integer.valueOf(answer.questionId) + "')";
            statement.executeUpdate(query);
            
            int answer_master_id=1;
            statement = connection.createStatement();
            query = "select max(id) max_id from answer_master";
        	ResultSet resultSet = statement.executeQuery(query);
        	if (resultSet.next()){
        		if (!resultSet.getString("max_id").isEmpty())
        			answer_master_id = Integer.valueOf(resultSet.getString("max_id"));
            }
        	
        	for (int translationId : answer.translationIds) {
        		statement = connection.createStatement();
    	        query = "insert into answer_detail (answer_master_id, translation_id) "+
    					"values ('"+ Integer.valueOf(answer_master_id) + "', '"+ Integer.valueOf(translationId) + "')";
                statement.executeUpdate(query);
                	
        	}
        	
	        connection.close();
	        
	        response.result = true;
			
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        response.result = false;
	        response.message = e.getMessage();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.result = false;
	        response.message = e.getMessage();
	    }
		
		return response;
	}
	
}

