import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class DownloadManager {
	
	public static int DOWNLOAD_SIZE = 0;

	private final static int PROCESSORS = Runtime.getRuntime().availableProcessors();
	private ExecutorService executorService;
	
	private int getDownloadSize(Download download) throws MalformedURLException, IOException {
		if (download == null)
			return -1;
		final URLConnection connection = new URL(download.getFileURL()).openConnection();
		return connection.getContentLength();
	}
	
	public void submitParallelDownloadTasks(Download download) throws MalformedURLException, IOException, InterruptedException {
		if(executorService == null) {
			executorService = Executors.newFixedThreadPool(PROCESSORS);
		}
		DOWNLOAD_SIZE = getDownloadSize(download);		
		int startPosition = 0;
		int sizePerThread = DOWNLOAD_SIZE / PROCESSORS;	
		for (int i = 0; i < PROCESSORS; i++) {
			final DownloadBlock downloadBlock = new DownloadBlock(i, startPosition, startPosition + sizePerThread,download.getFileURL(), download.getStorageLocation());
			executorService.submit(downloadBlock);
			startPosition += sizePerThread;
		}
		executorService.shutdown();
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);		
		final File file = new File(download.getStorageLocation());
		if(download.isFileUnzip()) {
			FileExtraction.unzip(download.getStorageLocation(), file.getParent() + System.getProperty("file.separator"));
			if(file.delete()) {
				System.out.println("Unneeded file cleared.");
			} else {
				System.out.println("Failed to clear unneeded file.");
			}
		}
	}

}
