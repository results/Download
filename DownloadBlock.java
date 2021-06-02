import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public final class DownloadBlock implements Runnable {

	public DownloadBlock(int index, int startPosition, int endPosition, 
			String urlLink, String localPath) {
		this.index = index;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.urlLink = urlLink;
		this.localPath = localPath;
	}
	
	private final int index;
	
	private final int startPosition;
	
	private final int endPosition;
	
	private final String urlLink;
	
	private final String localPath;
	
	private HttpURLConnection connection;
	
	private RandomAccessFile randomAccessFile;
	
	private static int BYTES_READ = 0;

	/**
	 * Max download speed...1MB/s
	 */
	private static final int BUFFER_SIZE = 1024;
	
	public static int getBytesRead() {
		return BYTES_READ;
	}
	
	public int getIndex() {
		return index;
	}
	
	private void initiateConnection() throws IOException {
		connection = (HttpURLConnection) (new URL(urlLink).openConnection());
		connection.addRequestProperty("Range", "bytes=" + startPosition + "-" + endPosition);
		connection.connect();
		
		randomAccessFile = new RandomAccessFile(localPath, "rw");
		randomAccessFile.seek(startPosition);
	}

	@Override
	public void run() {
		try {
			initiateConnection();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		final byte[] buffer = new byte[BUFFER_SIZE];
		int currentBytes;
		try {
			final InputStream inputStream = connection.getInputStream();
			while ((currentBytes = inputStream.read(buffer)) != -1) {
				randomAccessFile.write(buffer, 0, currentBytes);
				BYTES_READ += currentBytes;
			}
			inputStream.close();
			randomAccessFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}