package panel.SSH;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.SwingUtilities;

import messageManager.LogMessage;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
 
public class SshConnection implements Runnable {
	private static Session session;
	public static Channel channel;
	public static InputStream input;
	private final String HOST;
	private final int PORT;
	private final String USERNAME;
	private final String PASSWORD;
	
	public SshConnection(final String host, final int port, final String username, final char[] password) {
		HOST = host;
		PORT = port;
		USERNAME = username;
		PASSWORD = new String(password);
	}


	@Override
	public void run() {
		SshConsole.consoleArea.setText("");
		final JSch jsch = new JSch();
		try {
			jsch.setKnownHosts("~/.ssh/known_hosts");
			new LogMessage("Info", "SSH: loaded known_hosts");
		} catch (final JSchException e) {
			e.printStackTrace();
			new LogMessage("Error", "SSH: failed to load known_hosts");
		}
		
		try {
			session = jsch.getSession(USERNAME, HOST, PORT);
			session.setPassword(PASSWORD);
			session.connect();
			new LogMessage("Info", "SSH: session connected");
			channel = session.openChannel("shell");
			input = new InputStream() {
				String input = null;
				int position = 0;
				@Override
				public int read() throws IOException {
					if (input.equals("exit")) {
			    		SshLogin.connectBtn.doClick();
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
			};
			channel.setInputStream(input);
			final OutputStream output = new OutputStream() {
				@Override
				public void write(final int b) throws IOException {
					updateTextArea(String.valueOf((char) b));
				}
				@Override
				public void write(final byte[] b, final int off, final int len) throws IOException {
					updateTextArea(new String(b, off, len));
				}
				@Override
				public void write(final byte[] b) throws IOException {
					write(b, 0, b.length);
				}
			};
			channel.setOutputStream(output);
			channel.connect();
			new LogMessage("Info", "SSH: channel connected");
			SshLogin.changeTCPguiState("Connected");
		} catch (final JSchException e) {
			SshLogin.changeTCPguiState("Disconnected");
			new LogMessage("Error", "SSH: failed to set up SSH connection");
		}
	}
	
	private void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SshConsole.consoleArea.append(text);
			}
		});
	}
	
	public static void sendMessage(final String message) {
		System.out.println(message);
		if (message.equals("exit")) {
			SshLogin.connectBtn.doClick();
		} else {
			try {
				channel.getInputStream().read(message.getBytes());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeSSH() {
		SshConsole.consoleInput.setEnabled(false);
		if (channel.isConnected()) {
			try {
				channel.sendSignal("exit");
				channel.getOutputStream().close();
				channel.getInputStream().close();
				new LogMessage("Info", "SSH: channel streams were successfully closed");
			} catch (final IOException e) {
				e.printStackTrace();
				new LogMessage("Error", "SSH: failed to close channel streams");
			} catch (final Exception e) {
				e.printStackTrace();
			}
			channel.disconnect();
			new LogMessage("Info", "SSH: channel successfully closed");
		}
		if (session.isConnected()) {
			session.disconnect();
			new LogMessage("Info", "SSH: session successfully closed");
		}
	}
}