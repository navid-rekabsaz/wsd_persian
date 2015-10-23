package at.ac.tuwien.ifs.wsdpersian;

public class Translation {

	public int id;
	public String name;
	
	// Must have no-argument constructor
	public Translation() {

	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}


}