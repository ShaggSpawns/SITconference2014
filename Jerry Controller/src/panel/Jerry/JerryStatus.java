package panel.Jerry;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Initializes the Jerry status bar on the Jerry Controller tab
 * @author Jackson Wilson (c) 2014
 */
public class JerryStatus extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public static JTextField jBar;
	
	/**
	 * The constructor of JerryStatus that initializes the Jerry status bar.
	 * Uses a GridBagLayout and a JTextField as the bar.
	 */
	public JerryStatus(final String OS) {
		setBorder(BorderFactory.createTitledBorder("Jerry Status"));
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();
		
		jBar = new JTextField(38);
		jBar.setFocusable(false);
		jBar.setText("[Disconnected]");
		jBar.setEditable(false);
		switch(OS) {
		case "Windows":
			gc.ipadx = 25;
			break;
		case "Mac":
			gc.ipadx = 14;
			break;
		case "Default":
			gc.ipadx = 14;
			break;
		}
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 3;
		gc.ipady = 0;
		add(jBar, gc);
	}
}