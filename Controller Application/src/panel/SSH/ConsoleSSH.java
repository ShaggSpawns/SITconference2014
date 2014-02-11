package panel.SSH;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Creates the SSH console panel for the SSH tab
 * @author Jackson Wilson (c) 2014
 */
public class ConsoleSSH extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static JTextArea consoleArea;
	public static JTextField consoleInput;
	public static TextFieldInputStream streamer;
	
	/**
	 * Initializes the SSH console area for the SSH tab
	 */
	public ConsoleSSH() {
		setBorder(BorderFactory.createTitledBorder("SSH Console"));
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();

		consoleArea = new JTextArea(30, 38);
		consoleArea.setEditable(false);
		consoleArea.setAutoscrolls(isEnabled());
		consoleArea.setSize(getPreferredSize());
	    gc.anchor = GridBagConstraints.CENTER;
	    gc.insets = new Insets(2,0,0,0);
	    gc.gridx = 0;
	    gc.gridy = 1;
	    gc.weighty = 0.0;
	    add(new JScrollPane(consoleArea), gc);
	    
		consoleInput = new JTextField(38);
		consoleInput.setEnabled(true);
		streamer = new TextFieldInputStream(consoleInput);
		consoleInput.addActionListener(streamer);
		gc.anchor = GridBagConstraints.SOUTH;
	    gc.ipady = 0;
	    gc.gridx = 0;
	    gc.gridy = 2;
	    gc.weighty = 1.0;
		add(consoleInput, gc);
	}
}