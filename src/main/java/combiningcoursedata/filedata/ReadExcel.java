package combiningcoursedata.filedata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	private static ArrayList<String> errorFiles;
	
	public static void readExcel(ArrayList<String> finalPath, String errorDir) throws IOException {
		for (String path : finalPath) {
			readExcel2(path, errorDir);
		}
	}
	private static boolean containsKeyword(String path) {
		if (path.contains("요약문") || path.contains("Summary")) {
			return true;
		}
		return false;
	}
	private static void readExcel2 (String path, String errorDir) throws IOException{
		try {	
			System.out.println("----------------------------------------");
			FileInputStream fis = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			int rowindex = 0;
			int columnindex = 0;
			
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			for (rowindex = (containsKeyword(path) ? 0 : 1); rowindex < rows; rowindex++) {
				XSSFRow row = sheet.getRow(rowindex);
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					for (columnindex = 0; columnindex < cells; columnindex++) {
						XSSFCell cell = sheet.getRow(rowindex).getCell((short)columnindex);
						String value = "";
						if (cell == null) {
							value = "";
						}
						else {
							switch (cell.getCellType()) {
							case FORMULA:
								value = cell.getCellFormula();
								break;
							case STRING:
								value = cell.getStringCellValue() + "";
								break;
							case NUMERIC:
								value = cell.getNumericCellValue() + "";
								break;
							case BLANK:
								value = cell.getBooleanCellValue() + "";
								break;
							case ERROR:
								value = cell.getErrorCellValue() + "";
								break;
							default:
								value = new String();
								break;
							}
						}
						System.out.println("각 셀 내용: " + value);
					}
				}
			}
		} catch (NotOfficeXmlFileException e) {
			if (errorFiles == null) {
				errorFiles = new ArrayList<String>();
			}
			errorFiles.add(path);
			createErrorFile(errorFiles, errorDir);
		}
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
