package panel.About;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class PanelAbout extends JPanel {
	private static final long serialVersionUID = 1L;
	
	//LoginSSH sshLogin;
	//KeyBindings keyBinds;
	//About about;
	//Help help;
	
	public PanelAbout() {
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();
		
		About about = new About();
		gc.anchor = GridBagConstraints.NORTH;
		//gc.fill = GridBagConstraints.BOTH;
		gc.weighty = 1.0;
		add(about, gc);
		
		Credits help = new Credits();
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridy = 1;
		add(help, gc);
	}
}