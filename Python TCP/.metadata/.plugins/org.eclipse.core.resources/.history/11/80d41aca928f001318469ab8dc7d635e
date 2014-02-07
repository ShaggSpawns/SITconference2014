package controllerApplication;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import messageManager.Log;
import panel.About.PanelAbout;
import panel.SSH.PanelSSH;
import panel.TCP.PanelTCP;

/**
 * Main class that calls to create a frame and runs it on a new thread.
 * @author Jackson Wilson (c) 2014
 */
public class Controller extends JApplet implements Runnable {
	private static final long serialVersionUID = 1L;
	private static boolean firstLogMessage = true;
	
	/**
	 * Initialize JApplet
	 * Adds:
	 * -PanelTCP "controlTab"
	 * -PanelSSH "consoleTab"
	 * -PanelAbout "aboutTab"
	 * to the JTabbedPane "tabbedPane"
	 */
	public void init() {
		final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        
        final PanelTCP controlTab = new PanelTCP();
        tabbedPane.addTab("Controller", controlTab);
         
        final PanelSSH consoleTab = new PanelSSH();
        tabbedPane.addTab("SSH / Console", consoleTab);
        
        final PanelAbout aboutTab = new PanelAbout();
        tabbedPane.addTab("About", aboutTab);
        
        add(tabbedPane);
        
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
	
	/**
	 * Start JApplet
	 */
	public void start() {
		
	}
	
	/**
	 * Stop JApplet
	 */
	public void stop() {
		
	}
	
	/**
	 * Destroy JApplet
	 */
	public void destroy() {
		
	}
	
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
		final JFrame frame = new MainFrame("Arduino Motor Program");
		frame.setMinimumSize(new Dimension(520, 750));
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
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
	
	public static boolean isFirstLogMessage() {
		return firstLogMessage;
	}

	public static void setFirstLogMessage(final boolean firstLogMessage) {
		Controller.firstLogMessage = firstLogMessage;
	}
}