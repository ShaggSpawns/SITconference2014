package jerryController;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import panel.About.AboutPanel;
import panel.Jerry.JerryPanel;
import panel.SSH.SshPanel;

/**
 * Main Frame: A frame with 3 tabs in a scroll tab layout
 * @author Jackson Wilson (c) 2014
 */
public class JerryFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor that builds a Jerry frame with a title.
	 * @param title
	 */
	public JerryFrame(final String title, final String OS) {
		super.setTitle(title);
		super.setResizable(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		switch(OS) {
		case "Windows":
			super.setLocation(600, 200);
			super.setMinimumSize(new Dimension(480, 600));
			break;
		case "Mac":
			super.setLocationRelativeTo(null);
			super.setMinimumSize(new Dimension(520, 750));
			break;
		case "Default":
			super.setLocationRelativeTo(null);
			super.setMinimumSize(new Dimension(520, 750));
			break;
		}
		
		final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        
        final JerryPanel controlTab = new JerryPanel(OS);
        tabbedPane.addTab("Jerry Controller", controlTab);
         
        final SshPanel consoleTab = new SshPanel(OS);
        tabbedPane.addTab("SSH", consoleTab);
        
        final AboutPanel aboutTab = new AboutPanel();
        tabbedPane.addTab("About / Info", aboutTab);
        
        add(tabbedPane);
        
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		super.setVisible(true);
	}
}