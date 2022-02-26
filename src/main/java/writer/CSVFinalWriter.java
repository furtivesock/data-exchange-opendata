package main.java.writer;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.neovisionaries.i18n.CountryCode;
import com.opencsv.CSVWriter;

import main.java.reader.CSVCovid;
import main.java.reader.CSVEducation;
import main.java.reader.CSVEmployment;
import main.java.reader.Content;

public class CSVFinalWriter {

	List<CSVCovid> csvCovids;
	List<CSVEducation> csvEducations;
	List<CSVEmployment> csvEmployments;
	
	public CSVFinalWriter(List<CSVCovid> csvCovids, List<CSVEducation> csvEducations, List<CSVEmployment> csvEmployments) {
		this.csvCovids = csvCovids;
		this.csvEducations = csvEducations;
		this.csvEmployments = csvEmployments;
	}
	
	public void write () throws IOException {
	
		Map<String, Content> result = new HashMap<>();
	
		for(CSVEmployment csvEmployment : csvEmployments) {
			if(!exists(result, csvEmployment.getKey().toString())) {
				result.put(csvEmployment.getKey().toString(), new Content());
			}
			
			result.get(csvEmployment.getKey().toString()).setCsvEmployment(csvEmployment);
		}
		
		for(CSVCovid csvCovid : csvCovids) {
			if(!exists(result, csvCovid.getKey().toString())) {
				result.put(csvCovid.getKey().toString(), new Content());
			}
			
			result.get(csvCovid.getKey().toString()).setCsvCovid(csvCovid);
		}
		
		for(CSVEducation csvEducation : csvEducations) {
			if(!exists(result, csvEducation.getKey().toString())) {
				result.put(csvEducation.getKey().toString(), new Content());
			}
			
			result.get(csvEducation.getKey().toString()).setCsvEducation(csvEducation);
		}
		
		
		
		Writer writer = Files.newBufferedWriter(Paths.get("src/main/resources/result.csv"));

        CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
		
        String[] headerRecord = {"Year", "Month", "Country", "CountryCode","Continent", "Population ", "Unemployment rate total", "Unemployment rate men", "Unemployment rate women", "Unemployment rate 14-24yo", "Unemployment rate 14-24yo men", "Unemployment rate 14-24yo women", "Unemployment rate 25-75yo", "Unemployment rate 25-75yo men", "Unemployment rate 25-75yo women", "CovidDeath", "CovidCases", "Recent graduates employment rate total (20-34yo)", "Recent graduates employment rate men", "Recent graduates employment rate women"};
        csvWriter.writeNext(headerRecord);
        
        
        result.forEach((key, value) -> {
        	
        	
        	
        	if(CountryCode.getByCode(key.split(",")[0]) != null) {
        		
        		System.out.println(CountryCode.getByCode(key.split(",")[0]).getName().replace("\"", ""));
        		
	        	csvWriter.writeNext(new String[] {
	        			key.split(",").length > 1 ? key.split(",")[1] : "",
	        			key.split(",").length > 2 ? key.split(",")[2] : "",
	        			CountryCode.getByCode(key.split(",")[0]).getName().replace(",", " - "),
	        			key.split(",")[0],
	        			value.getCsvCovid() == null ? "" : value.getCsvCovid().getContinent(),
	        			value.getCsvCovid() == null ? "" : value.getCsvCovid().getPopulation(),
	        			value.getCsvEmployment() == null ? "" : value.getCsvEmployment().getRateTotal(),
	        			value.getCsvEmployment() == null ? "" : value.getCsvEmployment().getRateMen(),
	        			value.getCsvEmployment() == null ? "" : value.getCsvEmployment().getRateWomen(),
	        			value.getCsvEmployment() == null ? "" : value.getCsvEmployment().getRate1424yo(),
	        			value.getCsvEmployment() == null ? "" : value.getCsvEmployment().getRate1424yoMen(),
	        			value.getCsvEmployment() == null ? "" : value.getCsvEmployment().getRate1424yoWomen(),
	        			value.getCsvEmployment() == null ? "" : value.getCsvEmployment().getRate2575yo(),
	        			value.getCsvEmployment() == null ? "" : value.getCsvEmployment().getRate2575yoMen(),
	        			value.getCsvEmployment() == null ? "" : value.getCsvEmployment().getRate2575yoWomen(),
	        			value.getCsvCovid() == null ? "" : value.getCsvCovid().getCovidDeath(),
	        			value.getCsvCovid() == null ? "" : value.getCsvCovid().getCovidCases(),
	        			value.getCsvEducation() == null ? "" : value.getCsvEducation().getGraduatesRate_t(),
	        			value.getCsvEducation() == null ? "" : value.getCsvEducation().getGraduatesRate_m(),
	        			value.getCsvEducation() == null ? "" : value.getCsvEducation().getGraduatesRate_f(),
	        	});
        	}
        });  
        
        csvWriter.close();
	}
	
	public boolean exists(Map<String, Content> result, String key) {
		Set<String> keys = result.keySet();
		Iterator<String> itr = keys.iterator();
		
		while(itr.hasNext()){
		  if(itr.next().equals(key)) return true;
		}
		
		return false;
	}
}
