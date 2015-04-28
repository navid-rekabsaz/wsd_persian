package at.ac.tuwien.ifs.wsdpersian;

import java.util.List;

public class Question {
	public int id;
	public int allQuestionsNumber;
	public int answeredQuestionsNumber;
    public String annotatorName;
    public String wordName;
    public String sentence;
    public int pages;
    public List<Cluster> clusters;
                    
    public Question(){};

}