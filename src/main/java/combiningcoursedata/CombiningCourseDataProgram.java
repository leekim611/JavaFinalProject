package combiningcoursedata;

import java.io.File;
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
	
	public void run(String[] args) {
		Options options = MakeOptions.createOptions();
		if (parseOptions(options, args)) {
			if (help) {
				MakeOptions.printHelp(options);
				return;
			}
			try {
				File relativePath = new File("data.zip");
				File absolutePath = new File(relativePath.getAbsolutePath());
				Zip.unzip(absolutePath);
				Zip.unzip(new File("C:\\git\\JavaFinalProject\\hi.zip"));
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
