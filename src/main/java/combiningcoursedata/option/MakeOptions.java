package combiningcoursedata.option;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class MakeOptions {
	public static Options createOptions() {
		Options options = new Options();
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Output path")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				.build());
		
		return options;
	}
	public  static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Combining Course Data";
		String footer = "";
		formatter.printHelp("HGUCombiningCourseData", header, options, footer, true);
	}
}
