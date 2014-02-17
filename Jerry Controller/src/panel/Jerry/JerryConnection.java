package panel.Jerry;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import messageManager.LogMessage;
import messageManager.StatusUpdateMessage;

/**
 * Handles the connection and communication with a TCP server.
 * @author Jackson Wilson (c) 2014
 */
public class JerryConnection implements Runnable {

	private final String serverIP;
	private final int serverPort;
	public static Socket connection;
	private static ObjectOutputStream output;
	private ObjectInputStream input;
	
	/**
	 * Handles the connection and communication with a TCP server
	 * @param hostIP
	 * @param port
	 */
	public JerryConnection(final String hostIP, final int port) {
		serverIP = hostIP;
		serverPort = port;
	}
	
	/**
	 * Connects to the server, sets up the streams / sockets, handles chatting with server, and closes sockets / streams when finished
	 */
	public void run() {
		try {
			connectToServer();
			setupStreams();
			whileChatting();
			cleanUp();
		} catch (final IOException ioException) {
			new LogMessage("Error", "Server not found!");
			new StatusUpdateMessage("Error", "Server not found!");
			JerryLogin.connectBtn.doClick();
		}
	}
	
	/**
	 * Connects to the Jerry Server and displays a confirmation message once connected
	 * @throws IOException
	 */
	private void connectToServer() throws IOException {
		new LogMessage("Info", "Attempting to connect to Jerry...");
		new StatusUpdateMessage("pending", "Attempting to connect...");
		connection = new Socket(InetAddress.getByName(serverIP), serverPort);
		new LogMessage("Info", "Connected to: " + connection.getInetAddress().getHostName());
		new StatusUpdateMessage("Connected", "Connected to Jerry at " + serverIP + ":" + serverPort);
		JerryControls.controlsEnabled(true);
	}
	
	/**
	 * Sets up the streams for the connection
	 */
	private void setupStreams() {
		try {
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			JerryLogin.changeTCPguiState("Connected");
			new LogMessage("Info", "Streams successfully setup");
		} catch (final IOException ioException) {
			new LogMessage("Error", "Failed to setup TCP streams!");
			new StatusUpdateMessage("Error", "Failed to setup Jerry streams!");
		} catch (final NullPointerException npException) {
			new LogMessage("Error", "NullPointerException in ConnectionTCP.setupStreams()");
		}
	}
	
	/**
	 * Handles the communication with the Jerry Server
	 */
	private void whileChatting() {
		String message = null;
		do {
			try {
				message = (String) input.readObject();
				new LogMessage("Info", message);
				new StatusUpdateMessage("Connected", message);
			} catch (final ClassNotFoundException classNotFoundException) {
				new LogMessage("Error", "Unable to read incoming message!");
				new StatusUpdateMessage("Error", "Unable to read incoming message!");
			} catch (final EOFException eofException) {
				new LogMessage("Error", "EOFException in ConnectionTCP.whileChatting()");
				new StatusUpdateMessage("Disconnected", "Disconnected from Jerry at " + serverIP + ":" + serverPort);
				JerryLogin.connectBtn.doClick();
				break;
			} catch (final IOException ioExection) {
				new LogMessage("Error", "IOException in ConnectionTCP.whileChatting()");
			} catch (final NullPointerException npE) {
				new LogMessage("Error", "NullPointerException in ConnctionTCP.whileChatting()");
			}
		} while(!message.equals("Jerry: END"));
	}
	
	/**
	 * Closes the sockets and streams to disconnect with the Jerry Server
	 */
	private final void cleanUp() {
		try {
			new LogMessage("Info", "Closing sockets and streams...");
			new StatusUpdateMessage("Pending", "Disconnecting from Jerry...");
			input.close();
			output.flush();
			output.close();
			if (connection.isConnected()) {
			connection.close();
			}
			new LogMessage("Info", "Successfully closed sockets and streams");
			new StatusUpdateMessage("Disconnected", "Successfully disconnected from Jerry");
		} catch (final IOException ioException) {
			new LogMessage("Error", "IOException in panel.TCP.ConnectionTCP.cleanUp()");
			new StatusUpdateMessage("Error", "Failed to disconnect to Jerry");
		} catch (final NullPointerException nullPointerException) {
			new LogMessage("Error", "NullPointerException in ConnectionTCP.cleanUp()");
			new StatusUpdateMessage("Error", "No sockets and streams to disconnect from");
		}
	}
	
	/**
	 * Sends a message to the Jerry Server
	 * @param message
	 */
	public static void sendMessage(final String message) {
		try {
			output.writeObject(message);
			output.flush();
			new LogMessage("Info", "Sent message: " + message);
			new StatusUpdateMessage("Connected",  "Sent message: " + message);
		} catch (final IOException ioException) {
			new LogMessage("Error", "IOException in ConnectionTCP.sendMessage()");
			new StatusUpdateMessage("Error", "Failed to send message");
		}
	}
}