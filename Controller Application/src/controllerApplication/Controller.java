package controllerApplication;

import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import panel.About.PanelAbout;
import panel.SSH.PanelSSH;
import panel.TCP.PanelTCP;

/**
 * Main class that calls to create a frame and runs it on a new thread.
 * @author Jackson Wilson
 * @since 2014
 */
public class Controller extends JApplet implements Runnable {
	private static final long serialVersionUID = 1L; // Version identifier
	
	/**
	 * Initialize JApplet
	 */
	public void init() {
		final JTabbedPane tabbedPane = new JTabbedPane(); // Initializes a new JTabbedPane named tabbedPane
        tabbedPane.setFocusable(false); // Sets tabbedPane to non-highlight-able
        
        final PanelTCP controlTab = new PanelTCP(); // Initializes a new tab from the panel PanelController and names it controlTab
        tabbedPane.addTab("Controller", controlTab); // Adds controlTab to tabbedPane
         
        final PanelSSH consoleTab = new PanelSSH(); // Initializes a new tab from the panel PanelConsole and names it consoleTab
        tabbedPane.addTab("Console", consoleTab); // Adds consoleTab to tabbedPane
        
        final PanelAbout extraTab = new PanelAbout(); // Initializes a new tab from the panel PanelExtras and names it extraTab
        tabbedPane.addTab("Extras", extraTab); // Adds extraTab to tabbedPane
        
        add(tabbedPane); // Adds tabbedPane to the frame
        
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); // Enables to use scrolling tabs
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
	public static void main(final String[] args) { // Main
		(new Thread(new Controller())).start(); // Starts new thread
	}
	
	/**
	 * Calls to create a new JFrame from MainFrame and sets the attributes of the new frame
	 */
	public void run() {
		final JFrame frame = new MainFrame("Arduino Motor Program"); // Calls MainFrame with title parameter
		frame.setMinimumSize(new Dimension(520, 750)); // Sets frame's dimensions
		frame.setResizable(false); // Disables frame resizing
		frame.setLocationRelativeTo(null); // Place Frame in Middle of Screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set Default Close
		frame.setVisible(true); // Set Visible
	}
}