package controllerApplication;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import panel.About.PanelAbout;
import panel.SSH.PanelSSH;
import panel.TCP.PanelTCP;

/**
 * Main Frame: A frame with 3 tabs in a scroll tab layout
 * @author Jackson Wilson
 * @since 2014
 */
public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor that builds a frame with a title.
	 * @param title
	 */
	public MainFrame(final String title) {
		super(title);
		
	        final JTabbedPane tabbedPane = new JTabbedPane();
	        tabbedPane.setFocusable(false);
	        
	        final PanelTCP controlTab = new PanelTCP();
	        tabbedPane.addTab("Controller", controlTab);
	         
	        final PanelSSH consoleTab = new PanelSSH();
	        tabbedPane.addTab("SSH", consoleTab);
	        
	        final PanelAbout aboutTab = new PanelAbout();
	        tabbedPane.addTab("About / Info", aboutTab);
	        
	        add(tabbedPane);
	        
	        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
	
	/**
	 * Runs the application by calling run() in a new thread
	 * @param args
	 */
	public static void main(final String[] args) {
		(new Thread(new Controller())).start();
	}
	
	/**
	 * Calls to create a new JFrame from MainFrame and sets the attributes of the new frame
	 */
	public void run() {
		final JFrame frame = new MainFrame("Arduino Motor Program");
		frame.setMinimumSize(new Dimension(400, 525));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}