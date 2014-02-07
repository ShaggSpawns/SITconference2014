package panel.TCP;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class PanelTCP extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public PanelTCP() {
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();
		
		LoginTCP tcpLogin = new LoginTCP();
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridy = 0;
		gc.weighty = 1.0;
		add(tcpLogin, gc);
		
		MotorControls controls = new MotorControls();
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridy = 1;
		gc.weighty = 0.0;
		add(controls, gc);
	}
}