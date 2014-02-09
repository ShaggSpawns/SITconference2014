package panel.SSH;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class OutputStreamSSH extends OutputStream {
	private JTextArea textArea;
	
	public OutputStreamSSH(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	@Override
	public void write(int b) throws IOException {
		// redirects data to the text area
		textArea.append(String.valueOf((char) b));
	}
}