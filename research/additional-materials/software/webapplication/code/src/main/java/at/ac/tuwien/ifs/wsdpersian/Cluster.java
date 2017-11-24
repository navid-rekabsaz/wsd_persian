package at.ac.tuwien.ifs.wsdpersian;

import java.util.List;

public class Cluster {

	public int id;
	public String name;
	public List<Translation> translations;

	// Must have no-argument constructor
	public Cluster() {

	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setTranslations(List<Translation> translations) {
		this.translations = translations;
	}

	public List<Translation> getTranslations() {
		return this.translations;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}


}