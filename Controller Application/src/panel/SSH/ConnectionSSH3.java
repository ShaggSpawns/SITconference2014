package panel.SSH;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ssh.SSH.MyUserInfo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
 
public class ConnectionSSH3 implements Runnable {
	private static Session session;
	private static Channel channel;
	private static InputStream input;
	private final String HOST;
	private final int PORT;
	private final String USERNAME;
	private final String PASSWORD;
	
	public ConnectionSSH3(final String host, final int port, final String username, final char[] password) {
		HOST = host;
		PORT = port;
		USERNAME = username;
		PASSWORD = new String(password);
	}


	@Override
	public void run() {
		final JSch jsch = new JSch();
		try {
			jsch.setKnownHosts("~/.ssh/known_hosts");
		} catch (final JSchException e) {
			e.printStackTrace();
		}
		
		final MyUserInfo userInfo = new MyUserInfo() {
			public void showMessage(final String message) {
				JOptionPane.showMessageDialog(null, message);
			}
			
			public boolean promptYesNo(final String message) {
				final Object[] options = { "Yes", "No" };
				final int foo = JOptionPane.showOptionDialog(null, message, "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				return foo == 0;
			}
		};
		try {
			session = jsch.getSession(USERNAME, HOST, PORT);
			session.setPassword(PASSWORD);
			session.setUserInfo(userInfo);
			session.connect();
			channel = session.openChannel("shell");
			channel.setInputStream(input);
			OutputStream output = new OutputStream() {
				@Override
				public void write(int b) throws IOException {
					updateTextArea(String.valueOf((char) b));
				}
				@Override
				public void write(byte[] b, int off, int len) throws IOException {
					updateTextArea(new String(b, off, len));
				}
				@Override
				public void write(byte[] b) throws IOException {
					write(b, 0, b.length);
				}
			};
			channel.setOutputStream(output);
			channel.connect();
			LoginSSH.saveBtn.setEnabled(true);
		} catch (final JSchException e) {
			e.printStackTrace();
		}
	}
	
	private void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ConsoleSSH.consoleArea.append(text);
			}
		});
	}
	
	public static void sendMessage(String message) {
		try {
			input = new ByteArrayInputStream(message.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		/*try {
			channel.sendSignal(message);
		} catch (Exception e1) {
			e1.printStackTrace();
		}*/
		System.out.println(message);
		
		if (message.equals("exit")) {
			closeSSH();
		}
	}
	
	public static void closeSSH() {
		LoginSSH.connectedGUIstate(false);
		channel.disconnect();
		session.disconnect();
	}
}