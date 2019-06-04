package combiningcoursedata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import combiningcoursedata.option.MakeOptions;
import combiningcoursedata.zip.Zip;
import combiningcoursedata.filedata.ReadExcel;
import combiningcoursedata.filedata.UnzippedExcelDataFile;


public class CombiningCourseDataProgram {
	
	private String input;	// save data.zip path
	private String output;	// save result.csv or result.xls path
	private boolean help;	// helpPrint
	
	public void run(String[] args) throws IOException {
		Options options = MakeOptions.createOptions();
		if (parseOptions(options, args)) {
			if (help) {
				MakeOptions.printHelp(options);
				return;
			}
			File file = new File(input);
			Zip.unzip(file);
			// get C:\git\JavaFinalProject\data start
			String fileFullName = file.getName();
        	int index = fileFullName.lastIndexOf(".");
        	String fileName = fileFullName.substring(0, index);
        	File absolutePath = new File(file.getAbsolutePath());
        	File saveDir = new File(absolutePath.getParent() + "\\" + fileName);
        	String startDir = saveDir.getAbsolutePath();
        	// get C:\git\JavaFinalProject\data end
        	UnzippedExcelDataFile files = new UnzippedExcelDataFile();
        	ArrayList<String> finalPath = new ArrayList<String>();
        	files.setFinalPath(finalPath);
        	UnzippedExcelDataFile.settingFinalPath(startDir, finalPath);  // save all xlsx files absolute path

        	ReadExcel.readExcel(finalPath, absolutePath.getParent());
		}
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			
			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			help = cmd.hasOption("h");
			
		} catch (Exception e) {
			MakeOptions.printHelp(options);
			return false;
		}
		return true;
	}
}
