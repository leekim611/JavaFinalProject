package combiningcoursedata.exceptions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;

public class MyNotOfficeXmlFileException extends NotOfficeXmlFileException{
	private ArrayList<String> errorFiles;
	public MyNotOfficeXmlFileException(String path) throws IOException {
		super(path);
		if (errorFiles == null) {
			errorFiles = new ArrayList<String>();
		}
		errorFiles.add(path);
		String errorCSVPath = System.getProperty("user.dir");
		createErrorFile(errorFiles, errorCSVPath);
	}
	private static void createErrorFile(ArrayList<String> errorFiles, String errorDir) throws IOException {
		String savedErrorFile = errorDir + "\\" + "error.csv";
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(savedErrorFile));
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
		for (String errorFileFull : errorFiles) {
			File errorFile = new File(errorFileFull);
			String errorFileName = errorFile.getName();
			csvPrinter.printRecord(errorFileName);
		}
		csvPrinter.flush();
	}
}
