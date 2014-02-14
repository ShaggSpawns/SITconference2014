package controllerApplication;

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
	public MainFrame(final String title, final String OS) {
		super(title);
		
	        final JTabbedPane tabbedPane = new JTabbedPane();
	        tabbedPane.setFocusable(false);
	        
	        final PanelTCP controlTab = new PanelTCP(OS);
	        tabbedPane.addTab("Jerry Controller", controlTab);
	         
	        final PanelSSH consoleTab = new PanelSSH(OS);
	        tabbedPane.addTab("SSH", consoleTab);
	        
	        final PanelAbout aboutTab = new PanelAbout(OS);
	        tabbedPane.addTab("About / Info", aboutTab);
	        
	        add(tabbedPane);
	        
	        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
}