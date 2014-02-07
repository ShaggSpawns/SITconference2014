package panel.TCP;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import panel.SSH.ConsoleSSH;

public class ConnectionTCP implements Runnable {

	private String message = "";
	private final String serverIP;
	private final int serverPort;
	private static Socket connection;
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	
	public ConnectionTCP(final String ip, final int port) {
		serverIP = ip;
		serverPort = port;
	}

	public void run() {
		try {
			connectToServer();
			setupStreams();
			whileChatting();
		} catch (final EOFException eofException) {
			MotorControls.sBar.setText("");
			TCPmessage(" [~] Client ended connection", 4, true);
		} catch (final IOException ioException) {
		} catch (final NullPointerException nullPointerException) {
		} finally {
			cleanUp();
		}
	}
	
	private void connectToServer() {
		try {
			MotorControls.sBar.setText("");
			TCPmessage(" [~] Attempting to connect...", 4, true);
			connection = new Socket(InetAddress.getByName(serverIP), serverPort);
			TCPmessage("Connected to: " + connection.getInetAddress().getHostName(), 2, true);
			LoginTCP.connectedGUIstate(true);
			MotorControls.controlsEnabled(true);
		} catch (final IOException ioException) {
			TCPmessage("Server not found!", 0, true);
		}
	}
	
	private void setupStreams() {
		try {
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
		} catch (final IOException ioException) {
		}
	}
	
	private void whileChatting() throws IOException {
		do {
			try {
				message = (String) input.readObject();
				TCPmessage(message, 2, true);
			} catch (final ClassNotFoundException classNotFoundException) {
				TCPmessage("Unable to read message", 3, false);
			}
		} while(!message.equals("SERVER: END"));
	}
	
	public final void cleanUp() {
		try {
			TCPmessage("Closing sockets...", 1, true);
			output.close();
			input.close();
			connection.close();
			MotorControls.controlsEnabled(false);
			TCPmessage("Successfully closed sockets.", 0, false);
			TCPmessage("Connection closed", 0, true);
		} catch (final IOException ioException) {
		} catch (final NullPointerException nullPointerException) {
			LoginTCP.connectBtn.setSelected(false);
		}
	}
	
	public static void sendMessage(final String message) {
		try {
			if ((message.equals("END"))) {
				output.writeObject("END");
				output.flush();
			} else {
				output.writeObject(message);
				output.flush();
				TCPmessage(message, 2, false);
			}
		} catch (final IOException ioException) {
		}
	}
	
	public static void TCPmessage(final String message, final int connectionState, final boolean toStatusBar) {
		if (toStatusBar == true) {
			MotorControls.statusBarUpdate(message, connectionState);
			//System.out.println(" --StatusBAR-- (" + connectionState + ") " + message);
			//PanelConsole.consoleArea.append(" --StatusBAR-- (" + connectionState + ") " + message + "\n");
		} else {
			//System.out.println(message);
			ConsoleSSH.consoleArea.append(message + "\n");
		}
	}
}