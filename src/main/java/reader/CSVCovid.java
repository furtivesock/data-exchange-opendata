package main.java.reader;

public class CSVCovid implements CSV<CSVCovid>{

	private Key key;
	
	private String covidDeath;
	private String covidCases;
	private String population;
	private String continent;
	
	public CSVCovid from(String[] fields) {
		
		CSVCovid csvCovid = new CSVCovid();
		
		Key key = new Key(fields[2], fields[0], fields[1]);
		
		csvCovid.setKey(key);
		csvCovid.setCovidDeath(fields[3]);
		csvCovid.setCovidCases(fields[4]);
		csvCovid.setPopulation(fields[5]);
		csvCovid.setContinent(fields[6]);
		
		return csvCovid;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getCovidDeath() {
		return covidDeath;
	}

	public void setCovidDeath(String covidDeath) {
		this.covidDeath = covidDeath;
	}

	public String getCovidCases() {
		return covidCases;
	}

	public void setCovidCases(String covidCases) {
		this.covidCases = covidCases;
	}

	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}
}
