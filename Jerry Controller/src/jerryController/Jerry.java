package jerryController;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JApplet;

import messageManager.LogFileManager;

/**
 * Main class that calls to create a frame and runs it on a new thread.
 * @author Jackson Wilson
 */
public class Jerry extends JApplet implements Runnable {
	private static final long serialVersionUID = 1L;
	private static boolean firstLogMessage = true;
	public static String OS = getOperatingSystem();
	
	/**
	 * Runs the application by calling run() in a new thread
	 * @param args
	 */
	public static void main(final String[] args) {
		if (OS.equals("Mac")) {
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Jerry Controller");
		}
		(new Thread(new Jerry())).start();
	}
	
	/**
	 * Calls to create a new MainFrame and sets the properties of the new frame
	 */
	public void run() {
		new JerryFrame("Jerry Controller", OS);
		createOpenLog();
	}
	
	/**
	 * Creates a new log to mark the launch of the program 
	 */
	public void createOpenLog() {
		final Date today = new Date();
		final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM/dd/yy hh:mm:ss a");
		final String time = DATE_FORMAT.format(today);
		new LogFileManager("+++++++++++++++ Program opened at [" + time + "] +++++++++++++++");
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
	
	/**
	 * Returns the operating system of the system running the program
	 * @return String of operating system
	 */
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