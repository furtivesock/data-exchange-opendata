package main.java.reader;

public class Content {
	
	private CSVCovid csvCovid;
	private CSVEmployment csvEmployment;
	private CSVEducation csvEducation;
	
	public Content() {}

	public CSVCovid getCsvCovid() {
		return csvCovid;
	}

	public void setCsvCovid(CSVCovid csvCovid) {
		this.csvCovid = csvCovid;
	}

	public CSVEmployment getCsvEmployment() {
		return csvEmployment;
	}

	public void setCsvEmployment(CSVEmployment csvEmployment) {
		this.csvEmployment = csvEmployment;
	}

	public CSVEducation getCsvEducation() {
		return csvEducation;
	}

	public void setCsvEducation(CSVEducation csvEducation) {
		this.csvEducation = csvEducation;
	}
}
