package main.java.reader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CSVCEEReader<T> {
	
	public List<T> read(String fileName, CSV<T> className) throws IOException, CsvException {

		List<T> csv = new ArrayList<>();
		
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            List<String[]> lines = reader.readAll();
            
            lines.remove(0);
            
            lines.forEach(fields -> csv.add((T) className.from(fields)));
        }
        
        return csv;
	}
}
