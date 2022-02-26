package main.java.reader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CovidReader {

	private File file;
	
	public CovidReader(File file) {
		this.file = file;
	}
	
	public List<CountryCovid> read() throws Exception {
		
		List<CountryCovid> covids = new ArrayList<>();
		
		FileInputStream fileInputStream = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		String countryName = "";
		
		CountryCovid countryCovid = null;
		
		for (Row row : sheet) {
			
			if(row.getRowNum() == 0) continue;
			
			if(row.getCell(0) == null) break;
			
			if(!row.getCell(0).toString().equals(countryName)) {
				
				if(!countryName.equals("")) covids.add(countryCovid);
				
				Country country = new Country();
				
				countryName = row.getCell(0).toString();
				
				country.setName(row.getCell(0).toString());
				country.setNameCode(row.getCell(1) == null ? "" : row.getCell(1).toString());
				country.setContinent(row.getCell(2) == null ? "" : row.getCell(2).toString());
				country.setPopulation(row.getCell(3) == null ? "" : row.getCell(3).toString());
				
				countryCovid = new CountryCovid(country);
			}
			
			countryCovid.add(row);
			
			
		}
		
		covids.add(countryCovid);
		
		workbook.close();
		fileInputStream.close();
		
		return covids;
	}
	
}
