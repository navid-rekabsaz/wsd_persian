package at.ac.tuwien.ifs.wsdpersian;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "job")
public class Question {
    public String name;
    public String status;
    public int pages;

    // just to make JAXB happy
    public Question(){};

    public Question(String name, String status, int pages) {
        this.name = name;
        this.status = status;
        this.pages = pages;
    }

}