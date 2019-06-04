package combiningcoursedata.zip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FilenameUtils;


public class Zip {
    
    public static void unzip(File zippedFile) throws IOException {
        unzip(zippedFile, zippedFile.getParentFile(), Charset.defaultCharset().name());
    }
    public static void unzip(File zippedFile, File destDir, String charsetName)
    throws IOException {
    	InputStream is = new FileInputStream(zippedFile);
        ZipArchiveInputStream zis ;
        ZipArchiveEntry entry ;
        String name ;
        File target ;
        int nWritten = 0;
        BufferedOutputStream bos ;
        byte [] buf = new byte[1024 * 8];
 
        zis = new ZipArchiveInputStream(is, charsetName, false);
        while ( (entry = zis.getNextZipEntry()) != null ){
        	// make directory
        	String fileFullName = zippedFile.getName();
        	int index = fileFullName.lastIndexOf(".");
        	String fileName = fileFullName.substring(0, index);
        	File absolutePath = new File(zippedFile.getAbsolutePath());
        	File saveDir = new File(absolutePath.getParent() + "\\" + fileName);
        	if (!saveDir.exists()) {
        		saveDir.mkdirs();
        	}
            name = entry.getName();
            target = new File (saveDir, name);
            if ( entry.isDirectory() ){
                target.mkdirs();
            } else {
                target.createNewFile();
                bos = new BufferedOutputStream(new FileOutputStream(target));
                while ((nWritten = zis.read(buf)) >= 0 ){
                    bos.write(buf, 0, nWritten);
                }
                bos.close();
            }
            String ext = FilenameUtils.getExtension(name);
            if (ext.equals("zip")) {
            	String studentZip = saveDir + "\\" + name;
            	File studentZipFile = new File(studentZip);
            	unzip(studentZipFile);
            }
        }
        zis.close();
    }
}
