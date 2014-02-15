package messageManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manages the log file for the program
 * @author Jackson Wilson (c) 2014
 */
public class LogFileManager {
	private final File logFolder = new File("LOGS");
	private final File log = new File(logFolder, "LOG.txt");
	
	private BufferedWriter bufferedWriter;
	
	/**
	 * Opens / creates a log file, appends an entry, and closes the file
	 * @param entry
	 */
	public LogFileManager(final String entry) {
		openLog();
		appendLog(entry);
		closeLog();
	}
	
	/**
	 * Opens/creates log folder/file
	 */
	private void openLog() {
		try {
			if (!logFolder.exists()) {
				logFolder.mkdir();
				new LogMessage("Info", "Created log folder");
			}
			if (!log.exists()) {
				log.createNewFile();
				new LogMessage("Info", "Created log file");
			}
				
			final FileWriter fileWriter = new FileWriter(log.getAbsoluteFile(), true);
			bufferedWriter = new BufferedWriter(fileWriter);
		} catch (final IOException e) {
			LogMessage.displayLogError("ERROR", "Failed to create log file!");
		}
	}
	
	/**
	 * Appends entry to the opened log file
	 * @param entry
	 */
	private void appendLog(final String entry) {
		try {
			bufferedWriter.write(entry);
			bufferedWriter.newLine();
		} catch (final IOException e) {
			LogMessage.displayLogError("ERROR", "Failed to write to log file!");
			
		}
	}
	
	/**
	 * Closes the log file
	 */
	private void closeLog() {
		try {
			bufferedWriter.close();
		} catch (final IOException e) {
			LogMessage.displayLogError("ERROR", "Failed to close log file!");
		}
	}
}