package javaTCPserver;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

/**
 * Initializes the TCP server that will receive commands, then pass them on to the Arduino.
 * @author Jackson Wilson (c) 2014
 */
public class TCPComm extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static int firstPort;
	private static int lastPort;
	private static int backlog;
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	private static ServerSocket server;
	private static Socket connection;
	
	/**
	 * Initializes the TCP server that will receive commands, then pass them on to the Arduino.
	 * @param portRangeStart
	 * @param portRangeEnd
	 * @param serverBacklog
	 */
	public TCPComm(final int portRangeStart, final int portRangeEnd, final int serverBacklog) {
		firstPort = portRangeStart;
		lastPort = portRangeEnd;
		backlog = serverBacklog;
		startRunning();
	}
	
	/**
	 * Manages the running processes for the TCP server.
	 */
	private static void startRunning() {
		while(true) {
			try {
				initializeServer();
				waitForConnection();
				setupStreams();
				whileChatting();
			} catch (final EOFException eofException) {
				displayMessage("Server ended the connection!");
			} catch (final IOException ioException) {
				ioException.printStackTrace();
			} finally {
				cleanUp();
			}
		}
	}
	
	/**
	 * Initializes the TCP server.
	 */
	private static void initializeServer() {
		for (int currentPort = firstPort; currentPort <= lastPort; currentPort++) {
			try {
				server = new ServerSocket(currentPort, backlog);
				displayMessage("Initialized server on port: " + currentPort);
				break;
			} catch (final IOException ioE) {
				displayMessage(currentPort + " is not an opened port.");
			}
		}
	}
	
	/**
	 * Waits for a connection with a TCP client.
	 * @throws IOException
	 */
	private static void waitForConnection() throws IOException {
		displayMessage("Waiting for someone to connect...");
		connection = server.accept();
		displayMessage("Now connected to " + connection.getInetAddress().getHostName());
	}
	
	/**
	 * After finding a connection, the input and output stream is created.
	 * @throws IOException
	 */
	private static void setupStreams() throws IOException {
		new JsscComm(9600);
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		displayMessage("Streams are now setup!");
	}
	
	/**
	 * Manages the chatting between the server and the client.
	 * @throws IOException
	 */
	private static void whileChatting() throws IOException {
		String message = null;
		do {
			try {
				message = (String) input.readObject();
				displayMessage(message);
				if (JsscComm.serialPort.isOpened() == true) {
					switch (message) {
						case "FORWARD":
							JsscComm.writeData("F");
							break;
						case "REVERSE":
							JsscComm.writeData("B");
							break;
						case "RIGHT":
							JsscComm.writeData("R");
							break;
						case "LEFT":
							JsscComm.writeData("L");
							break;
						case "STOP":
							JsscComm.writeData("S");
							break;
						default:
							break;
					}
				}
			} catch (final ClassNotFoundException classNotFoundException) {
				displayMessage("Unable to read input.");
			}
		} while (!message.equals("END"));
	}
	
	/**
	 * Closes the streams and the socket.
	 */
	private static void cleanUp() {
		displayMessage("Closing connection...");
		sendMessage("END");
		try {
			if (JsscComm.serialPort.isOpened() == true) {
				JsscComm.close();
			}
			output.close();
			input.close();
			connection.close();
			Thread.sleep(200);
		}catch(final IOException ioException){
			ioException.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Manages the sending of messages over TCP to the client.
	 * @param message
	 */
	public static void sendMessage(final String message) {
		try {
			output.writeObject("SERVER: " + message);
			output.flush();
			displayMessage("SERVER: " + message);
		} catch (final IOException ioException) {
			displayMessage("CAN'T SEND");
			startRunning();
		}
	}
	
	/**
	 * Displays message to the user.
	 * @param message
	 */
	public static void displayMessage(final String message) {
		System.out.println(message);
	}
}