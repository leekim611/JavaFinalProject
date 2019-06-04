package combiningcoursedata.filedata;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Hi {
	public static void readXlsx(String path) throws IOException {
		FileInputStream fis = new FileInputStream("C:\\Users\\user\\Desktop\\JavaProject\\data\\0001\\통일한국개론자료수집양식(요약문)-21500387 안정윤.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int rowindex = 0;
		int columnindex = 0;
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows();
		for (rowindex = 0; rowindex < rows; rowindex++) {
			XSSFRow row = sheet.getRow(rowindex);
			if (row != null) {
				int cells = row.getPhysicalNumberOfCells();
				for (columnindex = 0; columnindex < cells; columnindex++) {
					XSSFCell cell = sheet.getRow(rowindex).getCell((short)columnindex);
					String value = "";
					value = cell.getStringCellValue();
					
					System.out.println("각 셀 내용: " + value);
				}
			}
		}
	}
}
