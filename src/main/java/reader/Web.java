package main.java.reader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Web {
	
	public static File download(URL url) throws IOException {
		InputStream in = url.openStream();
		Files.copy(in, Paths.get("src/main/resources/covid.xlsx"), StandardCopyOption.REPLACE_EXISTING);
		return new File("src/main/resources/covid.xlsx");
	}
	
	public static File download(String url) throws IOException {
		InputStream in = new URL(url).openStream();
		Files.copy(in, Paths.get("src/main/resources/covid.xlsx"), StandardCopyOption.REPLACE_EXISTING);
		return new File("src/main/resources/covid.xlsx");
	}
}
