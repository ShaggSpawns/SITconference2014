package panel.SSH;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * Initializes the SSH Panel onto the SSH tab
 * @author Jackson Wilson (c) 2014
 */
public class SshPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initializes the SSH Panel onto the SSH tab
	 */
	public SshPanel(final String OS) {
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();

		final SshLogin sshLogin = new SshLogin(OS);
		gc.anchor = GridBagConstraints.NORTH;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 0;
		gc.weighty = 0.0;
		add(sshLogin, gc);
		
		final SshConsole sshConsole = new SshConsole(OS);
		gc.anchor = GridBagConstraints.NORTH;
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weighty = 1.0;
		add(sshConsole, gc);
	}
}