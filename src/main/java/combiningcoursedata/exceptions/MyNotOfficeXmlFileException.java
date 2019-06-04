package combiningcoursedata.exceptions;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;

public class MyNotOfficeXmlFileException extends NotOfficeXmlFileException{

	public MyNotOfficeXmlFileException(String message) {
		super(message);
		//make error.csv
	}
}
