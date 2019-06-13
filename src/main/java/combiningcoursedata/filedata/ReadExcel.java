package combiningcoursedata.filedata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import combiningcoursedata.exceptions.MyNotOfficeXmlFileException;

public class ReadExcel implements Runnable{
	private static HashMap<String, ArrayList<EachRowData>[]> savedContents;
	private ArrayList<String> csvHeader;
	private static ArrayList<String> errorFiles;
	private static String pathForThread;
	private static String errorDirForThread;
	
	public static void readExcel(ArrayList<String> finalPath, String errorDir) throws IOException {
		int numThreads = finalPath.size();
		Thread[] threads = new Thread[numThreads];
		ReadExcel.errorDirForThread = errorDir;
		
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new Thread(new ReadExcel());
			ReadExcel.pathForThread = finalPath.get(i);
			
			threads[i].start();
		}
	}
	private static boolean containsKeyword(String path) {
		if (path.contains("¿ä¾à¹®") || path.contains("Summary")) {
			return true;
		}
		return false;
	}	
	private static void readExcel (String path, String errorDir) throws IOException{
		savedContents = new HashMap<String, ArrayList<EachRowData>[]>();
		for (String key : savedContents.keySet()) {
			if (savedContents.get(key) == null) {
				ArrayList<EachRowData>[] hello = savedContents.get(key);
				for (ArrayList<EachRowData> arraylist : hello) {
					arraylist = new ArrayList<EachRowData>();
					EachRowData<String> lineData = new EachRowData<String>();
				}
			}
		} 
		try {
			FileInputStream fis = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			int rowindex = 0;
			int columnindex = 0;
			
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			for (rowindex = (containsKeyword(path) ? 1 : 2); rowindex < rows; rowindex++) {
				XSSFRow row = sheet.getRow(rowindex);
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					EachRowData<String> lineData = new EachRowData<String>();
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
						int columnNum = (containsKeyword(path) ? 7 : 5);
						if (containsKeyword(path)) {
							if (columnindex == 0) {
								lineData.setTitle1(value);
							} else if (columnindex == 1) {
								lineData.setSummary(value);
							} else if (columnindex == 2) {
								lineData.setKeyword(value);
							} else if (columnindex == 3) {
								lineData.setSearchDate(value);
							} else if (columnindex == 4) {
								lineData.setSource(value);
							} else if (columnindex == 5) {
								lineData.setOrganizationName(value);
							} else if (columnindex == 6) {
								lineData.setCopyright(value);
							}
						} else {
							if (columnindex == 0) {
								lineData.setTitle2(value);
							} else if (columnindex == 1) {
								lineData.setNumberOfData(value);
							} else if (columnindex == 2) {
								lineData.setDataType(value);
							} else if (columnindex == 3) {
								lineData.setExplainData(value);
							} else if (columnindex == 4) {
								lineData.setNumberOfPage(value);
							}
						}
					}
				}
			}
		} catch (NotOfficeXmlFileException e) {
			throw new MyNotOfficeXmlFileException(path);
			
		}
	}
	public void setHeader(ArrayList<String> finalPath) throws IOException {
		csvHeader = new ArrayList<String>();
		for (int i = 0; i < 2; i++) {
			String path = finalPath.get(i);
			FileInputStream fis = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			int rowindex = 0;
			int columnindex = 0;
			
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			rowindex = (containsKeyword(path) ? 0 : 1);
			
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
					if (!(rowindex == 1 && columnindex == 0)) {
						csvHeader.add(value);	
					}
				}
			}
		}
	}
	public ArrayList<String> getCSVHeader() {
		return csvHeader;
	}
	@Override
	public void run() {
		try {
			readExcel(ReadExcel.pathForThread, ReadExcel.errorDirForThread);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
