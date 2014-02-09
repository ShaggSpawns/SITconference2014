package panel.SSH;

import java.io.IOException;
import java.net.InetAddress;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.TransportException;

/**
 * Handles the SSH connection of the application
 * @author Jackson Wilson (c) 2014
 */
public class ConnectionSSH2 implements Runnable {

	private final int serverPort;
	private final InetAddress serverIP;
	private final String serverUsername;
	private final String serverPassword;
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
		whileConnected();
		closeConnection();
	}
	
	/**
	 * Sets up the session with SSH
	 */
	private void setupSession() {
		try {
			@SuppressWarnings("resource")
			final SSHClient ssh = new SSHClient();
			ssh.loadKnownHosts();
			ssh.connect(serverIP, serverPort);
			try {
				ssh.authPassword(serverUsername, serverPassword);
				session = ssh.startSession();
				cmd.join();
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
	private void whileConnected() {
		while (true) {
			try {
				System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (cmd.isOpen() == false) {
				System.out.println("exit-status: "+cmd.getExitStatus());
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
			cmd = session.exec(message);
		} catch (ConnectionException | TransportException e) {
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
		} catch (TransportException | ConnectionException e) {
			e.printStackTrace();
		}
	}
}