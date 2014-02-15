package jerryServer;

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
public class JerryComm extends JFrame {
	private static final long serialVersionUID = 1L;
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
	public JerryComm() {
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
			} finally {
				cleanUp();
			}
		}
	}
	
	/**
	 * Initializes the TCP server.
	 */
	private static void initializeServer() {
		try {
			server = new ServerSocket(6789, 5);
			System.out.println("Initialized server on port: " + 6789);
		} catch (final IOException ioE) {
			System.out.println("6789 is not an opened port.");
		}
	}
	
	/**
	 * Waits for a connection with a TCP client.
	 * @throws IOException
	 */
	private static void waitForConnection() {
		try {
			System.out.println("Waiting for someone to connect...");
			connection = server.accept();
			System.out.println("Now connected to " + connection.getInetAddress().getHostName());
		} catch (final IOException ioE) {
			System.out.println("Could not accept incoming connection");
		}
	}
	
	/**
	 * After finding a connection, the input and output stream is created.
	 * @throws IOException
	 */
	private static void setupStreams() {
		try {
			new JsscComm();
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			System.out.println("Streams are now setup!");
		} catch (final IOException ioE) {
			System.out.println("Failed to setup streams");
		}
	}
	
	/**
	 * Manages the chatting between the server and the client.
	 * @throws IOException
	 */
	private static void whileChatting() {
		String message = null;
		do {
			try {
				message = (String) input.readObject();
				if (JsscComm.serialPort.isOpened() == true) {
					switch (message) {
						case "STOP":
							JsscComm.writeData("0");
							break;
						case "FORWARD":
							JsscComm.writeData("1");
							break;
						case "REVERSE":
							JsscComm.writeData("2");
							break;
						case "RIGHT":
							JsscComm.writeData("3");
							break;
						case "LEFT":
							JsscComm.writeData("4");
							break;
						default:
							break;
					}
				} else {
					System.out.println(message);
				}
			} catch (final ClassNotFoundException | IOException e) {
				System.out.println("Unable to read input.");
			}
		} while (!message.equals("END"));
	}
	
	/**
	 * Closes the streams and the socket.
	 */
	private static void cleanUp() {
		try {
			output.writeObject("Jerry: END");
			System.out.println("Closing connection...");
			if (JsscComm.serialPort.isOpened()) {
				JsscComm.close();
			}
			output.close();
			input.close();
			connection.close();
			server.close();
			System.out.println("\n# # # # # # # # # #\n");
			Thread.sleep(100);
		} catch (final IOException e) {
			System.out.println("Unable to close Jerry server");
		} catch (final InterruptedException e) {
			System.out.println("Clean up was interrupted");
		}
	}
	
	/**
	 * Manages the sending of messages over TCP to the client.
	 * @param message
	 */
	public static void sendMessage(final String message) {
		try {
			output.writeObject("Jerry: " + message);
			output.flush();
			System.out.println("Sent: " + message);
		} catch (final IOException ioException) {
			System.out.println("Could not send message to Jerry controller");
			startRunning();
		}
	}
}