package panel.TCP;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * Initializes the TCP panel for the MotorControls tab
 * @author Jackson Wilson (c) 2014
 */
public class PanelTCP extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initializes the TCP panel for the MotorControls tab
	 */
	public PanelTCP() {
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();
		
		final LoginTCP tcpLogin = new LoginTCP();
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridy = 0;
		gc.weighty = 1.0;
		add(tcpLogin, gc);
		
		final MotorControls controls = new MotorControls();
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridy = 1;
		add(controls, gc);
		
		final StatusBar sBar = new StatusBar();
		gc.anchor = GridBagConstraints.SOUTH;
		gc.gridy = 2;
		gc.weighty = 0.0;
		add(sBar, gc);
	}
}