import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class FileExtraction {
	
	
	/**
	 * Max extraction buffer...4 MB
	 */
	private static final int BUFFER_SIZE = 4096;
	
	public static void unzip(String fileLocation, String destinationPath) throws IOException {
		final File file = new File(fileLocation);
		final BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
		final ZipInputStream zin = new ZipInputStream(input);
		ZipEntry e;
		while ((e = zin.getNextEntry()) != null) {
			if (e.isDirectory()) {
				(new File(destinationPath + e.getName())).mkdir();
			} else {
				if (e.getName().equals(fileLocation)) {
					extract(zin, fileLocation);
					break;
				}
				extract(zin, destinationPath + e.getName());
			}
		}
		zin.close();
	}
	
	private static void extract(ZipInputStream zipIn, String filePath) throws IOException {
       		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        	byte[] bytesIn = new byte[BUFFER_SIZE];
        	int read = 0;
        	while ((read = zipIn.read(bytesIn)) != -1) {
        		bos.write(bytesIn, 0, read);
        	}
        	bos.close();
    	}
	
}

