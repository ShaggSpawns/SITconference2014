package panel.SSH;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class PanelSSH extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public PanelSSH() {
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();

		LoginSSH sshLogin = new LoginSSH();
		gc.anchor = GridBagConstraints.NORTH;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 0;
		gc.weighty = 1.0;
		add(sshLogin, gc);
		
		ConsoleSSH sshConsole = new ConsoleSSH();
		gc.anchor = GridBagConstraints.NORTH;
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weighty = 0.0;
		add(sshConsole, gc);
	}
}