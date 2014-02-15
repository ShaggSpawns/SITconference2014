package panel.About;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * Builds a JPanel to combine the About and Help panels.
 * @author Jackson Wilson (c) 2014
 */
public class AboutPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public AboutPanel() {
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();

		final Credits credit = new Credits();
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridy = 0;
		add(credit, gc);
		
		final About about = new About();
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridy = 1;
		add(about, gc);
		
		final InfoConsole console = new InfoConsole();
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridy = 2;
		add(console, gc);
	}
}