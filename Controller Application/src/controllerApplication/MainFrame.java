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
	public MainFrame(final String title, final String OS) {
		super.setTitle(title);
		super.setResizable(true);
		//super.setLocationRelativeTo(null);
		super.setLocation(960, 200);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		switch(OS) {
		case "Windows":
			super.setMinimumSize(new Dimension(480, 731));
			break;
		case "Mac":
			super.setMinimumSize(new Dimension(520, 750));
			break;
		case "Unix":
			super.setMinimumSize(new Dimension(520, 750));
			break;
		case "Solaris":
			super.setMinimumSize(new Dimension(520, 750));
			break;
		case "Unknown":
			super.setMinimumSize(new Dimension(520, 750));
			break;
		}
		
		final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        
        final PanelTCP controlTab = new PanelTCP(OS);
        tabbedPane.addTab("Jerry Controller", controlTab);
         
        final PanelSSH consoleTab = new PanelSSH(OS);
        tabbedPane.addTab("SSH", consoleTab);
        
        final PanelAbout aboutTab = new PanelAbout();
        tabbedPane.addTab("About / Info", aboutTab);
        
        add(tabbedPane);
        
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		super.setVisible(true);
	}
}