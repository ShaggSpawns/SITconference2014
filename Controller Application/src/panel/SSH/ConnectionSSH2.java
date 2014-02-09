package panel.SSH;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;

/**
 * Handles the SSH connection of the application
 * @author Jackson Wilson (c) 2014
 */
public class ConnectionSSH2 implements Runnable {

	private final int serverPort;
	private final InetAddress serverIP;
	private final String serverUsername;
	private final String serverPassword;
	private static SSHClient sshClient = null;
	private static Session session;
	private static Command cmd;
	
	/**
	 * Handles the SSH connection of the application
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 */
	public ConnectionSSH2(final InetAddress ip, final int port, final String username, final char[] password) {
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
	}
	
	/**
	 * Sets up the session with SSH
	 */
	private void setupSession() {
		try {
			sshClient = new SSHClient();
			//ssh.loadKnownHosts();
			sshClient.connect(serverIP, serverPort);
			try {
				sshClient.authPassword(serverUsername, serverPassword);
				session = sshClient.startSession();
				LoginSSH.connectedGUIstate(true);
			} catch (Exception e) {
				e.printStackTrace();
				LoginSSH.connectedGUIstate(false);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			LoginSSH.connectedGUIstate(false);
		}
	}
	
	/**
	 * Handles the communication over SSH
	 */
	/*private void whileConnected() {
		while (true) {
			try {
				System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (cmd.isOpen() == false) {
				System.out.println("exit-status: " + cmd.getExitStatus());
				break;
			}
		}
	}*/
	
	/**
	 * Sends a message through SSH.
	 * @param message
	 */
	public static void sendMessage(String message) {
		try {
			cmd = session.exec(message);
			System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
			cmd.join(5, TimeUnit.SECONDS);
			//cmd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the SSH session / channel
	 */
	public static void closeConnection() {
		try {
			Thread.sleep(1000);
		}catch (final InterruptedException e) {
			e.printStackTrace();
		}
		try {
			cmd.sendEOF();
			cmd.close();
			session.sendEOF();
			session.close();
			sshClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/*public static void main(final String[] arg) {

try {
	final JSch jsch = new JSch();
	jsch.setKnownHosts("~/.ssh/known_hosts");
	String host = null;
	
	if (arg.length > 0) {
		host = arg[0];
	} else {
		host = JOptionPane.showInputDialog("Enter username@hostname", System.getProperty("user.name") + "@localhost");
	}
	
	final String user = host.substring(0, host.indexOf('@'));
	host = host.substring(host.indexOf('@') + 1);
	final Session session = jsch.getSession(user, host, 22);
	final String passwd = JOptionPane.showInputDialog("Enter password");
	session.setPassword(passwd);
	
	final UserInfo ui = new MyUserInfo() {
		public void showMessage(final String message) {
			JOptionPane.showMessageDialog(null, message);
		}
		
		public boolean promptYesNo(final String message) {
			final Object[] options = { "yes", "no" };
			final int foo=JOptionPane.showOptionDialog(null, message, "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			return foo == 0;
		}
	};
	session.setUserInfo(ui);
	session.connect(30000);   // making a connection with timeout.
	final Channel channel=session.openChannel("shell");
	channel.setInputStream(System.in);
	channel.setOutputStream(System.out);
	channel.connect(3*1000);
} catch(final Exception e) {
	System.out.println(e);
}
}

public static abstract class MyUserInfo implements UserInfo, UIKeyboardInteractive {
	public String getPassword() { return null; }
	public boolean promptYesNo(final String str){ return false; }
	public String getPassphrase(){ return null; }
	public boolean promptPassphrase(final String message){ return false; }
	public boolean promptPassword(final String message){ return false; }
	public void showMessage(final String message){ }
	public String[] promptKeyboardInteractive(final String destination, final String name, final String instruction, final String[] prompt, final boolean[] echo) {
		return null;
	}
}*/