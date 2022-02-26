package main.java;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.exceptions.CsvException;

import main.java.reader.CSVCEEReader;
import main.java.reader.CSVCovid;
import main.java.reader.CSVEducation;
import main.java.reader.CSVEmployment;
import main.java.reader.CountryCovid;
import main.java.reader.CovidReader;
import main.java.writer.CSVFinalWriter;
import main.java.writer.CovidWriter;

public class Main {

	private static Logger LOGGER = Logger.getAnonymousLogger();
	
	public static void main(String[] args) {
		
		//covidParser();
		fileMerge();
		
	}
	
	public static void fileMerge() {
		
		CSVCEEReader<CSVEducation> cEducationReader = new CSVCEEReader<>();
		CSVCEEReader<CSVEmployment> cEmploymentReader = new CSVCEEReader<>();
		CSVCEEReader<CSVCovid> cCovidReader = new CSVCEEReader<>();
		
		CSVEducation csvEducation = new CSVEducation();
		CSVEmployment csvEmployment = new CSVEmployment();
		CSVCovid csvCovid = new CSVCovid();
		
		try {
			List<CSVEducation> csvEducations = cEducationReader.read("src/main/resources/education_20092020.csv", csvEducation);
			System.out.println(csvEducations.size());
			
			List<CSVEmployment> csvEmployments = cEmploymentReader.read("src/main/resources/unemployment.csv", csvEmployment);
			System.out.println(csvEmployments.size());
			
			List<CSVCovid> csvCovids = cCovidReader.read("src/main/resources/covidexport.csv", csvCovid);
			System.out.println(csvCovids.size());
			
			CSVFinalWriter csvFinalWriter = new CSVFinalWriter(csvCovids, csvEducations, csvEmployments);
			csvFinalWriter.write();
			
		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}
	}
	
	public static void covidParser() {
		LOGGER.log(Level.INFO, "START - Download covid DATA !");
		
		File file = new File("src/main/resources/covid.xlsx");
		
		/*
		try {
			file = Web.download("https://opendata.ecdc.europa.eu/covid19/nationalcasedeath/xlsx");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "ERROR - Download covid DATA !");
		}
		*/
		
		LOGGER.log(Level.INFO, "COMPLETE - Download covid DATA !");
		
		LOGGER.log(Level.INFO, "START - Parsing file !");
		
		List<CountryCovid> countrycovids = null;
		
		try {
			CovidReader covidReader = new CovidReader(file);
			
			countrycovids = covidReader.read();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "ERROR - Parsing file !");
			e.printStackTrace();
			
		}	
		
		LOGGER.log(Level.INFO, "COMPLETE - Parsing file !");
		
		LOGGER.log(Level.INFO, "START - Writing file !");
		
		try {
			CovidWriter covidReader = new CovidWriter(countrycovids);
			
			covidReader.write();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "ERROR - Writing file !");
			e.printStackTrace();
		}
		
		LOGGER.log(Level.INFO, "COMPLETE - Writing file !");
	}
}
