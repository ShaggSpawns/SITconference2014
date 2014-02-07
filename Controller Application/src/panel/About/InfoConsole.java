package panel.About;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 * Creates an Information Console on the About tab
 * @author Jackson Wilson (c) 2014
 *
 */
public class InfoConsole extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static JTextArea consoleArea;
	
	/**
	 * Creates an Information Console on the About tab
	 */
	public InfoConsole() {
		setBorder(BorderFactory.createTitledBorder("Information Console"));
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();
		
		consoleArea = new JTextArea(11, 38);
		consoleArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret)consoleArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(2,1,2,2);
	    add(new JScrollPane(consoleArea), gc);
	}
}