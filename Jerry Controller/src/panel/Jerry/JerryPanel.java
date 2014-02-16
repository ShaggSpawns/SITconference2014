package panel.Jerry;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * Initializes the Jerry panel for the Jerry Controller tab
 * @author Jackson Wilson (c) 2014
 */
public class JerryPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initializes the TCP panel for the MotorControls tab
	 */
	public JerryPanel(final String OS) {
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();
		
		final JerryLogin jLogin = new JerryLogin(OS);
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridy = 0;
		gc.weighty = 1.0;
		add(jLogin, gc);
		
		final JerryControls jControls = new JerryControls(OS);
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridy = 1;
		add(jControls, gc);
		
		final JerryStatus jBar = new JerryStatus(OS);
		gc.anchor = GridBagConstraints.SOUTH;
		gc.gridy = 2;
		gc.weighty = 0.0;
		add(jBar, gc);
	}
}