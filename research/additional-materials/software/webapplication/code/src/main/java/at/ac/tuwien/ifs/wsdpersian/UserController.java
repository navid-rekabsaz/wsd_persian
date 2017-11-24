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

@Path("/user")
public class UserController {
	
	String dbUrl;
	String username;
    String password;
    String dbClass = "com.mysql.jdbc.Driver";
    
	public UserController() throws IOException{
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/checkpassword")
	public Boolean checkPassword(@QueryParam("annotatorid") int annotatorId, @QueryParam("passwrd") String annotatorPassword)
	{
		Boolean result = false;
		try {

	        Class.forName(dbClass);
	        Connection connection = DriverManager.getConnection(dbUrl,
	            username, password);
	        Statement statement = connection.createStatement();
	        
	        String query = "select count(*) cnt from annotators where id="+Integer.valueOf(annotatorId)+" and passwrd='"+annotatorPassword+"'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
            	if (Integer.valueOf(resultSet.getString("cnt")) > 0)
            		result = true;
            }
            
	        connection.close();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	
		return result;
	}
	
}

