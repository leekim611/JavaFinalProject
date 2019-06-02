package combiningcoursedata;

import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import combiningcoursedata.option.MakeOptions;
import combiningcoursedata.zip.Zip;


public class CombiningCourseDataProgram {
	
	private String input;	// save data.zip path
	private String output;	// save result.csv or result.xls path
	private boolean help;	// helpPrint
	private ArrayList<String> fileNames;	// save file's path after unzipping data.zip
	
	
	public void run(String[] args) {
		Options options = MakeOptions.createOptions();
		if (parseOptions(options, args)) {
			if (help) {
				MakeOptions.printHelp(options);
				return;
			}
			try {
				fileNames = Zip.Unzip(input);
				ArrayList<String> tempFileNames = new ArrayList<String>();
				tempFileNames = (ArrayList<String>) fileNames.clone();
				for (String fileName : tempFileNames) {
					//fileNames = Zip.Unzip(fileName);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
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
