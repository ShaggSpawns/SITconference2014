package controllerApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JApplet;

import messageManager.Log;

/**
 * Main class that calls to create a frame and runs it on a new thread.
 * @author Jackson Wilson (c) 2014
 */
public class Controller extends JApplet implements Runnable {
	private static final long serialVersionUID = 1L;
	private static boolean firstLogMessage = true;
	private final String OS = IdentifyOS.getOperatingSystem();
	
	/**
	 * Runs the application by calling run() in a new thread
	 * @param args
	 */
	public static void main(final String[] args) {
		(new Thread(new Controller())).start();
		createOpenLog();
	}
	
	/**
	 * Calls to create a new MainFrame and sets the properties of the new frame
	 */
	public void run() {
		new MainFrame("Jerry Controller", OS);
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
		Controller.firstLogMessage = firstLogMessage;
	}
}