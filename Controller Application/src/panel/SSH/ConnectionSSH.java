package panel.SSH;

import javax.swing.JOptionPane;

import panel.TCP.ConnectionTCP;
import ssh.SSH.MyUserInfo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class ConnectionSSH implements Runnable {
	private final int serverPort;
	private final String serverIP;
	private final String serverUsername;
	private final String serverPassword;
	private String message;
	private static Session session;
	private static Channel channel;
	
	public ConnectionSSH(final String ip, final int port, final String username, final char[] password) {	
		serverIP = ip;
		serverPort = port;
		serverUsername = username;
		serverPassword = new String(password);
	}
	
	public void run() {
		try {
			connectToServer();
			setupStreams();
			whileChatting();
		} catch (final NullPointerException nullPointerException) {
		} finally {
			cleanUp();
		}
	}
	
	private void connectToServer() {
		try {
			final JSch jsch = new JSch();
			session = jsch.getSession(serverUsername, serverIP, serverPort);
			session.setPassword(serverPassword);
		} catch (final JSchException jschException) {
			System.out.println("Server not found!");
		}
	}
	
	 UserInfo ui = new MyUserInfo() {
		 
		 public void showMessage(final String message) { 
			 JOptionPane.showMessageDialog(null, message);
		 }
		 
		 public boolean promptYesNo(final String message) {
			 final Object[] options = { "yes", "no" }; 
	 
			 final int foo = JOptionPane.showOptionDialog(null, message, "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			 
			 return foo == 0;
		 }
	 };
	 
	private void setupStreams() {
	
	 session.setUserInfo(ui);
	 
	 
	 	try {
	 		session.connect(30000);
	 		channel = session.openChannel("shell");
	 		channel.setInputStream(ConsoleSSH.UserInputStream, false);
	 		channel.setOutputStream(ConsoleSSH.SystemOutputStream, false);
	 		channel.connect(3*1000);
	 		LoginSSH.connectedGUIstate(true);
	 	} catch (final JSchException e) {
	 	}
	}
	
	private void whileChatting() {
		do {
			message = ConsoleSSH.SystemOutputStream.toString();
			ConnectionTCP.TCPmessage(message, 2, true);
			System.out.println(message);
		} while (!message.equals("SERVER: END"));
	}
	
	public static void cleanUp() {
		channel.disconnect();
		session.disconnect();
	}
}