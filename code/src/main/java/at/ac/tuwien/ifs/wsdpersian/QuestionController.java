package at.ac.tuwien.ifs.wsdpersian;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/question")
public class QuestionController {
	
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
	public Student getNewQuestion(@QueryParam("annotatorid") int annotatorId)
	{
		Student st = new Student("Navid", "Diaz",22,1);

		return st;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/hello")
	public String sayHello()
	{
		return "HELLO FACET WORLD!";
	}
	/*
	@POST
	public void saveAnswer(Integer annotatorId, Integer questionId, Integer[] translationIds)
	{
		
	}
	*/
}

