package panel.SSH;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

import ssh.SSH.MyUserInfo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * Handles the SSH connection of the application
 * @author Jackson Wilson (c) 2014
 */
public class ConnectionSSH implements Runnable {

	private final int serverPort;
	private final String serverIP;
	private final String serverUsername;
	private final String serverPassword;
	private UserInfo ui;
	private static Session session;
	private static Channel channel;
	private InputStream inStream;
	private OutputStream outStream;
	
	/**
	 * Handles the SSH connection of the application
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 */
	public ConnectionSSH(final String ip, final int port, final String username, final char[] password) {
		serverIP = ip;
		serverPort = port;
		serverUsername = username;
		serverPassword = new String(password);
	}
	
	/**
	 * Sets up the streams / channel for the SSH, handles the communication with SSH, and closes the connection once finished
	 */
	public void run() {
		setupSession();
		setUserInfo();
		setupChannel();
		whileConnected();
		closeConnection();
	}
	
	/**
	 * Initializes the user info (gets permission to use it)
	 */
	private void setUserInfo() {
		ui = new MyUserInfo() {
			
			public void showMessage(final String message) { 
				JOptionPane.showMessageDialog(null, message);
			}
			
			public boolean promptYesNo(final String message) {
				final Object[] options = { "yes", "no" };
		 
				final int foo = JOptionPane.showOptionDialog(null, message, "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				 
				return foo == 0;
			}
		};
	}
	
	/**
	 * Sets up the session with SSH
	 */
	private void setupSession() {
		final JSch jsch = new JSch();
		
		try {
			session = jsch.getSession(serverUsername, serverIP, serverPort);
			session.setPassword(serverPassword);
			jsch.setKnownHosts("~/.ssh/known_hosts");
		    //jsch.addIdentity("~/.ssh/id_rsa");
		    session.setUserInfo(ui);
			session.connect(30000);
			LoginSSH.connectedGUIstate(true);
		} catch (final JSchException e) {
			e.printStackTrace();
			LoginSSH.connectedGUIstate(false);
		}
	}
	
	/**
	 * Sets up the SSH channel
	 */
	private void setupChannel() {
		try {
			channel = session.openChannel("shell");
			//channel.setInputStream(ConsoleSSH.UserInputStream, false);
	 		//channel.setOutputStream(ConsoleSSH.SystemOutputStream, false);
			channel.setInputStream(inStream);
			channel.setOutputStream(outStream);
	 		channel.connect(3*1000);
		} catch (final JSchException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles the communication over SSH
	 */
	private void whileConnected() {
		try {
			inStream = channel.getInputStream();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		
		final byte[] tmp = new byte[1024];
		
		while (true) {
			try {
				while (inStream.available() > 0 ) {
					final int i = inStream.read(tmp, 0, 1024);
					if (i < 0) break;
					System.out.print(new String(tmp, 0, i));
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
			
			if (channel.isClosed()) {
				System.out.println("exit-status: "+channel.getExitStatus());
				break;
			}
		}
	}
	
	/**
	 * Sends a message through SSH.
	 * @param message
	 */
	public static void sendMessage(String message) {
		try {
			channel.sendSignal(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the SSH session / channel
	 */
	public static void closeConnection() {
		try {
			Thread.sleep(1000);
		}catch (final Exception ee) {
			ee.printStackTrace();
		}
	      channel.disconnect();
	      session.disconnect();
	}
}