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
import java.util.Calendar;
import java.util.Properties;

@Path("/sql")
public class SqlController {
	
	String dbUrl;
	String username;
    String password;
    String dbClass = "com.mysql.jdbc.Driver";
    int allQuestionsNumber;
    
	public SqlController() throws IOException{
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
	    
	  
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response execute(String sqlText)
	{
		System.out.println(sqlText);
		Response response = new Response();
		
		try {

	        Class.forName(dbClass);
	        Connection connection = DriverManager.getConnection(dbUrl,
	            username, password);

	        Statement statement = connection.createStatement();
	        statement.executeUpdate(sqlText);
            
        	
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

