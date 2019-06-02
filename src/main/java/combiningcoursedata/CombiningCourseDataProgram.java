package combiningcoursedata;

import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import combiningcoursedata.option.MakeOptions;
import combiningcoursedata.zip.Zip;


public class CombiningCourseDataProgram {
	
	private String input;
	private String output;
	private boolean help;
	private ArrayList<String> fileNames;
	
	public void run(String[] args) {
		Options options = MakeOptions.createOptions();
		if (parseOptions(options, args)) {
			if (help) {
				MakeOptions.printHelp(options);
				return;
			}
			try {
				fileNames = Zip.Unzip(input);
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
