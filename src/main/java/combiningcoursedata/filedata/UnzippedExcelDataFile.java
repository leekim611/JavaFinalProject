package combiningcoursedata.filedata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

public class UnzippedExcelDataFile {
	public ArrayList<String> finalPath;
	
	public static void settingFinalPath(String startDir, ArrayList<String> finalPath){
		File directory = new File(startDir);
		File[] fileList = directory.listFiles();
		try {
			for (int i = 0; i < fileList.length; i++) {
				File file = fileList[i];
				String ext = FilenameUtils.getExtension(file.getName());
				if (file.isFile() && !ext.equals("zip")) {
					finalPath.add(file.getAbsolutePath());
				}
				else if (file.isDirectory()) {
					settingFinalPath(file.getCanonicalPath().toString(), finalPath);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getFinalPath(){
		return finalPath;
	}
	public void setFinalPath(ArrayList<String> finalPath) {
		this.finalPath= finalPath;
	}
}
