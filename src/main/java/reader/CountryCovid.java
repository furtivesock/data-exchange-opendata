package main.java.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;

public class CountryCovid {

	private Country country;
	
	List<Covid> cases;
	List<Covid> deaths;
	
	CountryCovid(Country country) {
		this.country = country;
		
		cases = new ArrayList<>();
		deaths = new ArrayList<>();
		
	}

	public void add(Row row) throws Exception {
		
		Covid covid = new Covid();
		
		covid.setIndicator(row.getCell(4) == null ? "" : row.getCell(4).toString());
		covid.setCount(Math.round(Float.valueOf(row.getCell(5) == null ? "0" : row.getCell(5).toString())));
		
		String stringWeek[] = row.getCell(6).toString().split("-");
		
		int year = Integer.valueOf(stringWeek[0]);
		int week = Integer.valueOf(stringWeek[1]);
		
		covid.setWeek(week, year);
		covid.setRate14Day(Double.valueOf(row.getCell(7) == null ? "0" : row.getCell(7).toString()));
		covid.setCumulative(Math.round(Float.valueOf(row.getCell(8) == null ? "0" : row.getCell(8).toString())));
		covid.setSource(row.getCell(9) == null ? "" : row.getCell(9).toString());
		covid.setNote(row.getCell(10) == null ? "" : row.getCell(10).toString());
		
		if(covid.getIndicator().equals("cases")) {
			cases.add(covid);
		} else if(covid.getIndicator().equals("deaths")) {
			deaths.add(covid);
		} else {
			throw new Exception("ERROR - Invalid indicator :" + covid.getIndicator());
		}
	}
	
	public int getCases(int month, int year) {

		int one = 1 + 4 * (month - 1);
		
		int [] weeks = { one , one + 1, one + 2, one + 3};
		
		int result = 0;
		
		for(Covid covid: cases) {
			if(Arrays.stream(weeks).anyMatch(i -> i == covid.getWeek().get(Calendar.WEEK_OF_YEAR)) && year == covid.getWeek().get(Calendar.YEAR)) {		
				result = result + covid.getCount();
			}
		}
		
		
		return result;
	}
	
	public int getDeaths(int month, int year) {
		
		int one = 1 + 4 * (month - 1);
		
		int [] weeks = { one , one + 1, one + 2, one + 3};
		
		int result = 0;
		
		for(Covid covid: deaths) {
			if(Arrays.stream(weeks).anyMatch(i -> i == covid.getWeek().get(Calendar.WEEK_OF_YEAR)) && year == covid.getWeek().get(Calendar.YEAR)) {	
				result = result + covid.getCount();
			}
		}
		
		
		return result;
	}

	public Country getCountry() {
		return country;
	}
	
	
}
