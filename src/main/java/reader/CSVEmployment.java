package main.java.reader;

public class CSVEmployment implements CSV<CSVEmployment> {

	private Key key;
	
	private String rateTotal;
	private String rateMen;
	private String rateWomen;
	private String rate1424yo;
	private String rate1424yoMen;
	private String rate1424yoWomen;
	private String rate2575yo;
	private String rate2575yoMen;
	private String rate2575yoWomen;
	
	public CSVEmployment from(String[] fields) {
		
		CSVEmployment csvEmployment = new CSVEmployment();
		
		Key key = new Key(fields[2], fields[0], fields[1]);
		
		csvEmployment.setKey(key);
		csvEmployment.setRateTotal(fields[3]);
		csvEmployment.setRateMen(fields[6]);
		csvEmployment.setRateWomen(fields[7]);
		csvEmployment.setRate1424yo(fields[5]);
		csvEmployment.setRate1424yoMen(fields[8]);
		csvEmployment.setRate1424yoWomen(fields[9]);
		csvEmployment.setRate2575yo(fields[4]);
		csvEmployment.setRate2575yoMen(fields[10]);
		csvEmployment.setRate2575yoWomen(fields[11]);
		
		return csvEmployment;
		
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getRateTotal() {
		return rateTotal;
	}

	public void setRateTotal(String rateTotal) {
		this.rateTotal = rateTotal;
	}

	public String getRateMen() {
		return rateMen;
	}

	public void setRateMen(String rateMen) {
		this.rateMen = rateMen;
	}

	public String getRateWomen() {
		return rateWomen;
	}

	public void setRateWomen(String rateWomen) {
		this.rateWomen = rateWomen;
	}

	public String getRate1424yo() {
		return rate1424yo;
	}

	public void setRate1424yo(String rate1424yo) {
		this.rate1424yo = rate1424yo;
	}

	public String getRate1424yoMen() {
		return rate1424yoMen;
	}

	public void setRate1424yoMen(String rate1424yoMen) {
		this.rate1424yoMen = rate1424yoMen;
	}

	public String getRate1424yoWomen() {
		return rate1424yoWomen;
	}

	public void setRate1424yoWomen(String rate1424yoWomen) {
		this.rate1424yoWomen = rate1424yoWomen;
	}

	public String getRate2575yo() {
		return rate2575yo;
	}

	public void setRate2575yo(String rate2575yo) {
		this.rate2575yo = rate2575yo;
	}

	public String getRate2575yoMen() {
		return rate2575yoMen;
	}

	public void setRate2575yoMen(String rate2575yoMen) {
		this.rate2575yoMen = rate2575yoMen;
	}

	public String getRate2575yoWomen() {
		return rate2575yoWomen;
	}

	public void setRate2575yoWomen(String rate2575yoWomen) {
		this.rate2575yoWomen = rate2575yoWomen;
	}

	
}
