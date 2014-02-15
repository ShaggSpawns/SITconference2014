package panel.About;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Builds a JPanel to display the credits to the creation of this application.
 * This panel uses a JTextArea in a JScrollPane.
 * @author Jackson Wilson (c) 2014
 */
public class Credits extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Builds a JPanel to display the credits to the creation of this application
	 */
	public Credits() {
		setBorder(BorderFactory.createTitledBorder("Credits"));
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();
		
		final JTextArea credits = new JTextArea(5, 40);
		credits.setEditable(false);
		credits.setAutoscrolls(isEnabled());
	    gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(2,1,2,2);
		credits.setText("~ 2014 SIT Confrense - For allowing us to share this experience with others" + "\n"
				+ "~ 2013 SIT Confrense Audinece - For giving us the insperation for this year" + "\n"
				+ "~ Google! - Without, we would have never completed this project" + "\n"
				+ "~ Jackson Wilson - Software Developer, Product Manager" + "\n"
				+ "~ Cole Bahan - Hardware Engineer, Software Design Contributer");
	    add(new JScrollPane(credits), gc);
	}
}