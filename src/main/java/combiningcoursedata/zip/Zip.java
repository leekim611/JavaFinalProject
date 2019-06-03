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


public class Zip {
	/*
	/**
	 * 여기서는 zip파일을 압축해제할거에요
	 * 이것저것 import해서 압축해제를 해내겠죠?
	 * 
	 * 그럼 어떻게 해야할 지 알아봅시다.
	 * 먼저 cli에서 input을 받아와야해요
	 * 그리고 zip파일을 저장 할 dir주소도 필요하겠죠? (이건 어떻게 만들지 생각좀 해봅시다.)
	 * 
	 * 그럼 input(ex: C:\\git\\JavaFinalProject\\data.zip    or    data.zip) 요친구를 이용해서 unzip을 하면
	 * 하위 file이나 folder가 targetDirectory에 생성될거에요.
	 * 그리고 그 하위 file 중에서 zip파일이 있으면 또 열어야하잖아요
	 * 그런데 우리는 이미 data.zip 안에 0001.zip ~ 0005.zip이 있다는걸 알아요
	 * 그러니까 쉽게쉽게 가자구요
	 * 먼저 data.zip을 unzip하는 과정에서 하위 zipfile의 name을 받아와요
	 * 그래야 data.zip을 unzip한 후 다시 0001.zip ~ 0005.zip을 unzip할 수 있으니까요
	 * 이건 어디 저장해야 할까요??????
	 * 
	 * 어쨌든 unzip만 해주면 되는 class에요
	 * 그런데 한글이 있으면 또 잘 안된다네요??
	 * 미치겠죠? 그래서 jazzlib를 쓴다는 사람도 있고, 뭐시기뭐시기 말이 많아요.
	 * 어쨌든 이걸 해결해봐야겠어요.
	 */
    
    public static void unzip(File zippedFile) throws IOException {
        unzip(zippedFile, Charset.defaultCharset().name());
    }
     
    public static void unzip(File zippedFile, String charsetName ) throws IOException {
        unzip(zippedFile, zippedFile.getParentFile(), charsetName);
    }
    public static void unzip(File zippedFile, File destDir) throws IOException {
        unzip(new FileInputStream(zippedFile), destDir, Charset.defaultCharset().name());
    }
     
    public static void unzip(File zippedFile, File destDir, String charsetName)
    throws IOException {
        unzip(new FileInputStream(zippedFile), destDir, charsetName);
    }
    public static void unzip(InputStream is, File destDir) throws IOException{
        unzip(is, destDir, Charset.defaultCharset().name());
    }
    public static void unzip( InputStream is, File destDir, String charsetName)
    throws IOException {
        ZipArchiveInputStream zis ;
        ZipArchiveEntry entry ;
        String name ;
        File target ;
        int nWritten = 0;
        BufferedOutputStream bos ;
        byte [] buf = new byte[1024 * 8];
 
        zis = new ZipArchiveInputStream(is, charsetName, false);
        while ( (entry = zis.getNextZipEntry()) != null ){
        	System.out.println(entry.getName());
        	/*
            name = entry.getName();
            target = new File (destDir, name);
            if ( entry.isDirectory() ){
                target.mkdirs();
            } else {
                target.createNewFile();
                bos = new BufferedOutputStream(new FileOutputStream(target));
                while ((nWritten = zis.read(buf)) >= 0 ){
                    bos.write(buf, 0, nWritten);
                }
                bos.close();
            }*/
        }
        zis.close();
    }
}
