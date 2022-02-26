package main.java.reader;

public class CSVEducation implements CSV<CSVEducation> {

	private Key key;
	
	private String graduatesRate_t;
	private String graduatesRate_m;
	private String graduatesRate_f;
	
	public CSVEducation from(String[] fields) {
		
		CSVEducation csvEducation = new CSVEducation();
		
		Key key = new Key(fields[2], fields[0], fields[1]);
		
		csvEducation.setKey(key);
		csvEducation.setGraduatesRate_t(fields[3]);
		csvEducation.setGraduatesRate_m(fields[4]);
		csvEducation.setGraduatesRate_f(fields[5]);
		
		return csvEducation;
		
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getGraduatesRate_t() {
		return graduatesRate_t;
	}

	public void setGraduatesRate_t(String graduatesRate_t) {
		this.graduatesRate_t = graduatesRate_t;
	}

	public String getGraduatesRate_m() {
		return graduatesRate_m;
	}

	public void setGraduatesRate_m(String graduatesRate_m) {
		this.graduatesRate_m = graduatesRate_m;
	}

	public String getGraduatesRate_f() {
		return graduatesRate_f;
	}

	public void setGraduatesRate_f(String graduatesRate_f) {
		this.graduatesRate_f = graduatesRate_f;
	}
	
	
}
