package main.java.writer;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVWriter;

import main.java.reader.CountryCovid;

public class CovidWriter {
	
	private int firstYear = 2020;
	private List<CountryCovid> countryCovids;
	
	public CovidWriter(List<CountryCovid> countryCovids) {
		this.countryCovids = countryCovids;
	}
	
	public void write() throws IOException {
		
		Writer writer = Files.newBufferedWriter(Paths.get("src/main/resources/covidexport.csv"));

        CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        
        String[] headerRecord = {"Year", "Month", "Country", "CovidDeath", "CovidCases", "Population", "Continent"};
        csvWriter.writeNext(headerRecord);
        
        Iterator<CountryCovid> countryCovidIterator = countryCovids.iterator();
        while (countryCovidIterator.hasNext()) {
			
			CountryCovid countryCovid = countryCovidIterator.next();
			
			for(int i = firstYear; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
				
				for(int j = 1; j <= 12; j++) {
					if(Calendar.getInstance().get(Calendar.YEAR) <= i && Calendar.getInstance().get(Calendar.MONTH) < j) continue;
					
					csvWriter.writeNext(new String[] {
							String.valueOf(i),
							String.valueOf(j),
		        			countryCovid.getCountry().getNameCode().replace("\"", ""),
		        			String.valueOf(countryCovid.getDeaths(j, i)),
		        			String.valueOf(countryCovid.getCases(j, i)),
		        			countryCovid.getCountry().getPopulation(),
		        			countryCovid.getCountry().getContinent()
		        	});
					
				}
			}
			
			countryCovidIterator.remove();
		}
        
        csvWriter.close();
	}
}
