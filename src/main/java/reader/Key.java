package main.java.reader;

public class Key {
	
	private String country;
	private String year;
	private String month;
	
	Key(String country, String year, String month) {
		this.country = country;
		this.year = year;
		this.month = month.startsWith("0") ? month.substring(1) : month;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.country.equals(((Key) obj).getCountry()) && this.year.equals(((Key) obj).getYear()) && this.month.equals(((Key) obj).getMonth());
	}
	
	@Override
	public String toString() {
		return new StringBuilder(country).append(",").append(year).append(",").append(month).toString();
	}
}
