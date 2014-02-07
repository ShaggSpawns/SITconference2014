package messageManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import controllerApplication.Controller;
import panel.About.InfoConsole;

/**
 * Manages the messages by sending them to the appropriate areas (Status Bar, Console Area, and/or the Log)
 * @author Jackson Wilson (c) 2014
 */
public class MessageLog {
	/**
	 * Formats a message that will be logged to the log file and displayed to the Info Console
	 * @param entryType
	 * @param message
	 */
	public MessageLog(final String entryType, final String message) {
		logMessage(entryType, message);
	}
	
	/**
	 * Adds a timestamp to the rawEntry string before pushing the new string to log() and the InfoConsole.consoleArea
	 * @param entryType
	 * @param rawEntry
	 */
	private void logMessage(final String entryType, final String rawEntry) {
		final String time = currentTime();
		final String entry = "[" + time + " " + entryType + "]: " + rawEntry;
				
		new Log(entry);
		if (Controller.isFirstLogMessage() == true) {
			InfoConsole.consoleArea.append(entry);
			Controller.setFirstLogMessage(false);
		} else {
			InfoConsole.consoleArea.append("\n" + entry);
		}
	}
	
	/**
	 * Used to display error messages without attempting to write a new entry into the log file.
	 * Adds a timestamp to the rawEntry string before being pushed to InfoConsole.consoleArea.
	 * @param entryType
	 * @param rawEntry
	 */
	public static void displayLogError(final String entryType, final String rawEntry) {
		final String time = currentTime();
		final String entry = "[" + time + " " + entryType + "]: " + rawEntry;
		
		InfoConsole.consoleArea.append(entry);
	}
	
	/**
	 * Initializes a string to be the timestamp using HH:mm:ss format
	 * @return time
	 */
	private static String currentTime() {
		final Date today = new Date();
		final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
		final String time = DATE_FORMAT.format(today);
		
		return time;
	}
}