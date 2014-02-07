package panel.SSH;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ConsoleSSH extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static JTextArea consoleArea;
	public static JTextField consoleInput;
	private static String userInputText;
	public static ByteArrayInputStream UserInputStream;
	public static ByteArrayOutputStream SystemOutputStream;
	
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
		consoleInput.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				try {
					userInputText = consoleInput.getText();
					UserInputStream = new ByteArrayInputStream(userInputText.getBytes("UTF-8"));
				} catch (final UnsupportedEncodingException e1) {
					e1.printStackTrace();
				} finally {
					consoleInput.setText("");
				}
				
				
			}
		});
		gc.anchor = GridBagConstraints.SOUTH;
	    gc.ipady = 0;
	    gc.gridx = 0;
	    gc.gridy = 2;
	    gc.weighty = 1.0;
		add(consoleInput, gc);
	}
}