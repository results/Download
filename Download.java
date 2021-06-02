
public class Download {
	
	public enum Priority {
		HIGH,
		NORMAL,
		LOW;
	}
		
	private String fileURL;
	private String fileName;
	private String storageLocation;
	private boolean fileUnzip;
	private Priority downloadPriority;

	public Download(String url, String filename, String storeLoc) {
		setFileURL(url);
		setFileName(filename);
		setStorageLocation(storeLoc);
		setDownloadPriority(Priority.NORMAL);
	}
	
	public Download(String url, String filename, String storeLoc, boolean unzip) {
		setFileURL(url);
		setFileName(filename);
		setStorageLocation(storeLoc);
		setFileUnzip(unzip);
		setDownloadPriority(Priority.NORMAL);
	}
	
	public Download(String url, String filename, String storeLoc, Priority priority) {
		setFileURL(url);
		setFileName(filename);
		setStorageLocation(storeLoc);
		setDownloadPriority(priority);
	}
	
	public Download(String url, String filename, String storeLoc, boolean unzip, Priority priority) {
		setFileURL(url);
		setFileName(filename);
		setStorageLocation(storeLoc);
		setFileUnzip(unzip);
		setDownloadPriority(priority);
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isFileUnzip() {
		return fileUnzip;
	}

	public void setFileUnzip(boolean fileUnzip) {
		this.fileUnzip = fileUnzip;
	}

	public Priority getDownloadPriority() {
		return downloadPriority;
	}

	public void setDownloadPriority(Priority downloadPriority) {
		this.downloadPriority = downloadPriority;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}
	
}
