package jerryController;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JApplet;

import messageManager.Log;

/**
 * Main class that calls to create a frame and runs it on a new thread.
 * @author Jackson Wilson (c) 2014
 */
public class Jerry extends JApplet implements Runnable {
	private static final long serialVersionUID = 1L;
	private static boolean firstLogMessage = true;
	public String OS = getOperatingSystem();
	
	/**
	 * Runs the application by calling run() in a new thread
	 * @param args
	 */
	public static void main(final String[] args) {
		(new Thread(new Jerry())).start();
		createOpenLog();
	}
	
	/**
	 * Calls to create a new MainFrame and sets the properties of the new frame
	 */
	public void run() {
		new JerryFrame("Jerry Controller", OS);
	}
	
	/**
	 * Creates a new log to mark the launch of the program 
	 */
	private static void createOpenLog() {
		final Date today = new Date();
		final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM/dd/yy hh:mm:ss a");

		final String time = DATE_FORMAT.format(today);
		
		new Log("+++++++++++++++ Program opened at [" + time + "] +++++++++++++++");
	}
	
	/**
	 * Returns if a log will be the first message displayed.
	 * @return
	 */
	public static boolean isFirstLogMessage() {
		return firstLogMessage;
	}
	
	/**
	 * Sets if a log will be the first message displayed.
	 * @param firstLogMessage
	 */
	public static void setFirstLogMessage(final boolean firstLogMessage) {
		Jerry.firstLogMessage = firstLogMessage;
	}
	
	public static String getOperatingSystem() {
		final String system = System.getProperty("os.name").toLowerCase();
		if (system.indexOf("win") >= 0) {
			return("Windows");
		} else if (system.indexOf("mac") >= 0) {
			return("Mac");
		} else {
			return("Default");
		}
	}
}