package panel.SSH;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JTextField;

public class TextFieldInputStream extends InputStream implements ActionListener {

    private final JTextField textField;
    private String input = null;
    private int position = 0;

    public TextFieldInputStream(final JTextField JTextField) {
        textField = JTextField;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
    	input = textField.getText() + "\n";
    	position = 0;
        textField.setText("");
        synchronized (this) {
            this.notifyAll();
        }
    }

    @Override
    public int read() throws IOException {
    	if (input.equals("exit")) {
    		ConnectionSSH.closeSSH();
    		return 0;
    	}
    	if (input != null && position == input.length()) {
        	input = null;
            return java.io.StreamTokenizer.TT_EOF;
        }
        while (input == null || position >= input.length()) {
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (final InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return input.charAt(position++);
    }
}