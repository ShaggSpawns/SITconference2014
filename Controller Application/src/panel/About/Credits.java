package panel.About;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Credits extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public Credits() {
		setBorder(BorderFactory.createTitledBorder("Credits"));
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();
		
		JTextArea credits = new JTextArea(10, 40);
		credits.setEditable(false);
		credits.setAutoscrolls(isEnabled());
	    gc.anchor = GridBagConstraints.CENTER;
	    gc.insets = new Insets(2,1,2,2);
	    add(new JScrollPane(credits), gc);
	}
}