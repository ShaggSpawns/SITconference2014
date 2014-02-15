package panel.Jerry;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Initializes the status bar on the Control tab
 * @author Jackson Wilson (c) 2014
 */
public class JerryStatus extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public static JTextField sBar;
	
	/**
	 * The constructor of SatusBar that initializes the status bar.
	 * Uses a GridBagLayout and a JTextField as the bar.
	 */
	public JerryStatus(final String OS) {
		setBorder(BorderFactory.createTitledBorder("Jerry Status"));
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();
		
		sBar = new JTextField(38);
		sBar.setFocusable(false);
		sBar.setText("[Disconnected]");
		sBar.setEditable(false);
		switch(OS) {
		case "Windows":
			gc.ipadx = 27;
			break;
		case "Mac":
			gc.ipadx = 0;
			break;
		case "Default":
			gc.ipadx = 0;
			break;
		}
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 3;
		gc.ipady = 0;
		add(sBar, gc);
	}
}