package panel.About;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class About extends JPanel {
	private static final long serialVersionUID = 1L;
	
		public About() {
			setBorder(BorderFactory.createTitledBorder("About / Help"));
			setLayout(new GridBagLayout());
			final GridBagConstraints gc = new GridBagConstraints();
			
			JTextArea about = new JTextArea(28, 40);
			about.setEditable(false);
			about.setAutoscrolls(isEnabled());
		    gc.anchor = GridBagConstraints.CENTER;
		    gc.insets = new Insets(2,1,2,2);
		    add(new JScrollPane(about), gc);
	}
}