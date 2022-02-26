package main.java.reader;

import java.util.Calendar;

public class Covid {

	private String indicator;
	private int count;	
	private Calendar week;	
	private double rate14Day;
	private int cumulative;
	private String source;
	private String note;
	
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public void setWeek(int week, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, week);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		this.week = cal;
	}
	
	public Calendar getWeek() {
		return this.week;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getRate14Day() {
		return rate14Day;
	}
	public void setRate14Day(double rate14Day) {
		this.rate14Day = rate14Day;
	}
	public int getCumulative() {
		return cumulative;
	}
	public void setCumulative(int cumulative) {
		this.cumulative = cumulative;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}
