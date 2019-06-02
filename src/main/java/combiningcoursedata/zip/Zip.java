package combiningcoursedata.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zip {
	
	public static ArrayList<String> Unzip(String input) throws Throwable {
		ArrayList<String> fileNames = new ArrayList<String>();
		
		String zipFileName = input;
		String directory = "a";
		File zipFile = new File(zipFileName);
		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry zipEntry = null;
		
		try {
			fis = new FileInputStream(zipFile);
			zis = new ZipInputStream(fis);
			while ((zipEntry = zis.getNextEntry()) != null) {
				String fileName = zipEntry.getName();
				File file = new File(directory, fileName);
				if (zipEntry.isDirectory()) {
					file.mkdirs();
				}
				else {
					createFile(file, zis);
					fileNames.add(file.getName());
				}
			}
		} catch (Throwable e) {
			throw e;
		} finally {
			if (zis != null) {
				zis.close();
			}
			if (fis != null){
				fis.close();
			}
		}
		return fileNames;
	}
	private static void createFile(File file, ZipInputStream zis) throws Throwable{
		File parentDir = new File(file.getParent());
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		try (FileOutputStream fos = new FileOutputStream(file)){
			byte[] buffer = new byte[256];
			int size = 0;
			while ((size = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, size);
			}
		} catch (Throwable e) {
			throw e;
		}
	}
}
