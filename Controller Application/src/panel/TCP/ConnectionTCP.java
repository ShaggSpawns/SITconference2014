package panel.TCP;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import messageManager.MessageLog;
import messageManager.MessageStatusUpdate;

/**
 * Handles the connection and communication with a TCP server.
 * @author Jackson Wilson (c) 2014
 */
public class ConnectionTCP implements Runnable {

	private String message = "";
	private final String serverIP;
	private final int serverPort;
	public static Socket connection;
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	
	/**
	 * Handles the connection and communication with a TCP server
	 * @param hostIP
	 * @param port
	 */
	public ConnectionTCP(final String hostIP, final int port) {
		serverIP = hostIP;
		serverPort = port;
	}
	
	/**
	 * Connects to the server, sets up the streams / sockets, handles chatting with server, and closes sockets / streams when finished
	 */
	public void run() {
		connectToServer();
		setupStreams();
		whileChatting();
		cleanUp();
	}
	
	/**
	 * Connects to a TCP server and displays a confirmation message once connected
	 */
	private void connectToServer() {
		try {
			new MessageLog("Info", "Attempting to connect...");
			new MessageStatusUpdate("pending", "Attempting to connect...");
			connection = new Socket(InetAddress.getByName(serverIP), serverPort);
			new MessageLog("Info", "Connected to: " + connection.getInetAddress().getHostName());
			new MessageStatusUpdate("Connected", "Connected to: " + connection.getInetAddress().getHostName());
			MotorControls.controlsEnabled(true);
		} catch (final IOException ioException) {
			new MessageLog("Error", "Server not found!");
			new MessageStatusUpdate("Error", "Server not found!");
		}
	}
	
	/**
	 * Sets up the streams for the connection
	 */
	private void setupStreams() {
		try {
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			LoginTCP.changeTCPguiState("Connected");
			new MessageLog("Info", "Streams successfully setup");
		} catch (final IOException ioException) {
			new MessageLog("Error", "Failed to setup TCP streams!");
			new MessageStatusUpdate("Error", "Failed to setup TCP streams!");
		} catch (final NullPointerException npException) {
			new MessageLog("Error", "NullPointerException in panel.TCP.ConnectionTCP.setupStreams()");
		}
	}
	
	/**
	 * Handles the communication with the TCP server
	 */
	private void whileChatting() {
		message = null;
		do {
			try {
				message = (String) input.readObject();
				new MessageLog("Info", message);
				new MessageStatusUpdate("Connected", message);
			} catch (final ClassNotFoundException classNotFoundException) {
				new MessageLog("Error", "Unable to read incoming message!");
				new MessageStatusUpdate("Error", "Unable to read incoming message!");
			} catch (final EOFException eofException) {
				new MessageLog("Error", "EOFException in panel.TCP.ConnectionTCP.whileChatting()");
				new MessageStatusUpdate("Disconnected", "Disconnected from: " + connection.getInetAddress().getHostName());
				break;
			} catch (final IOException ioExection) {
				new MessageLog("Error", "IOException in panel.TCP.ConnectionTCP.whileChatting()");
			} catch (final NullPointerException npE) {
				new MessageLog("Error", "NullPointerException in panel.TCP.ConnctionTCP.whileChatting()");
			}
		} while(!message.equals("SERVER: END"));
	}
	
	/**
	 * Closes the sockets and streams to disconnect with the TCP server
	 */
	private final static void cleanUp() {
		if (connection.isConnected()) {
			MotorControls.buttonToggled("Disable");
			LoginTCP.connectBtn.doClick();
			try {
				new MessageLog("Info", "Closing sockets and streams...");
				new MessageStatusUpdate("Pending", "Closing sockets and streams...");
				input.close();
				new MessageStatusUpdate("Info", "TCP input stream closed");
				output.flush();
				output.close();
				new MessageStatusUpdate("Info", "TCP output stream closed");
				connection.close();
				new MessageStatusUpdate("Info", "TCP connecton closed");
				new MessageLog("Info", "Successfully closed sockets and streams");
				new MessageStatusUpdate("Disconnected", "Successfully closed sockets and streams");
			} catch (final IOException ioException) {
				new MessageLog("Error", "IOException in panel.TCP.ConnectionTCP.cleanUp()");
				new MessageStatusUpdate("Error", "Failed to close sockets and streams");
				return;
			} catch (final NullPointerException nullPointerException) {
				new MessageLog("Error", "NullPointerException in panel.TCP.ConnectionTCP:2");
				new MessageStatusUpdate("Error", "No sockets and streams to disconnect from");
			}
		}
	}
	
	/**
	 * Sends a message to the output stream
	 * @param message
	 */
	public static void sendMessage(final String message) {
		try {
			output.writeObject(message);
			output.flush();
			new MessageLog("Info", "Sent message: " + message);
			new MessageStatusUpdate("Connected",  "Sent message: " + message);
		} catch (final IOException ioException) {
			new MessageLog("Error", "IOException in panel.TCP.ConnectionTCP.sendMessage()");
			new MessageStatusUpdate("Error", "Failed to send message");
		}
	}
}