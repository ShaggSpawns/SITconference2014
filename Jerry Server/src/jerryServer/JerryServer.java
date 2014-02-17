package jerryServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * The server for Jerry that handles the communication between the controller and the hardware.
 * @author Jackson Wilson (c) 2014
 */
public class JerryServer {
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private SerialPort serialPort;
	private ServerSocket server;
	private Socket connection;
	private static int port = 6789;
	
	/**
	 * Main method, asks for a port that the Jerry Server will be set up on.
	 * Runs the Server on the given port.
	 * @param args
	 */
	public static void main(final String[] args) {
		/*while (true) {
			try {
				System.out.print("Enter a port: ");
				port = Integer.parseInt(new Scanner(System.in).next());
				break;
			} catch (final NumberFormatException e) {
				System.out.println("\nPort was not an Integer");
			}
		}*/
		new JerryServer();
	}
	
	/**
	 * Loops the running of the Jerry Server
	 */
	private JerryServer() {
		while(true) {
			try {
				runJerryServer();
				whileChatting();
			} finally {
				cleanUp();
			}
		}
	}
	
	/**
	 * Initializes the Jerry Server on the given port.
	 * Waits for a connection to be accepted.
	 * After accepting a connection, the streams are setup.
	 */
	private void runJerryServer() {
		// Initialize the Jerry Server
		try {
			server = new ServerSocket(port, 5);
			System.out.println("Initialized Jerry on port: " + port);
		} catch (final IOException ioE) {
			System.out.println(port + " is not an opened port.");
		}
		
		// Wait for connection
		try {
			System.out.println("Waiting for someone to connect...");
			connection = server.accept();
			//String input = new Scanner(System.in).next().toLowerCase();
			//if (input.equals("exit")){
				//System.exit(0);
			//}
			System.out.println("Now connected to " + connection.getInetAddress().getHostName());
		} catch (final IOException ioE) {
			System.out.println("Could not accept incoming connection");
		}
		
		// Setup streams
		try {
			startSerialComm();
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			System.out.println("Streams are now setup!");
		} catch (final IOException ioE) {
			System.out.println("Failed to setup streams");
		}
	}
	
	
	
	/**
	 * Manages the incoming messages from the Jerry Controller.
	 * Runs until the exit message is received "END".
	 */
	private void whileChatting() {
		String message = null;
		do {
			try {
				message = (String) input.readObject();
				if (serialPort.isOpened() == true) {
					switch (message) {
						case "STOP":
							writeDataToSerial("0");
							break;
						case "FORWARD":
							writeDataToSerial("1");
							break;
						case "REVERSE":
							writeDataToSerial("2");
							break;
						case "RIGHT":
							writeDataToSerial("3");
							break;
						case "LEFT":
							writeDataToSerial("4");
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
	 * Closes the Jerry Server
	 */
	private void cleanUp() {
		try {
			sendMessage("END");
			System.out.println("Closing connection...");
			if (serialPort.isOpened()) {
				closeSerialComm();
			}
			output.close();
			input.close();
			connection.close();
			server.close();
			System.out.println("Successfully closed Jerry Server, restarting...");
			System.out.println("\n# # # # # # # # # # # #\n");
			Thread.sleep(100);
		} catch (final IOException e) {
			System.out.println("Unable to close Jerry server");
		} catch (final InterruptedException e) {
			System.out.println("Clean up was interrupted");
		}
	}
	
	/**
	 * Manages the sending of messages to the Jerry Controller
	 * @param message
	 */
	private void sendMessage(final String message) {
		try {
			output.writeObject("Jerry: " + message);
			output.flush();
			System.out.println("Sent: " + message);
		} catch (final IOException ioException) {
			System.out.println("Could not send message to Jerry controller");
			new JerryServer();
		}
	}
	
	/**
	 * Starts the connection over the serial port
	 */
	private void startSerialComm() {
		serialPort = new SerialPort("/dev/ttyUSB0");
		try {
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0);
			if (serialPort.isOpened()) {
				System.out.println("Serial port is opened on: " + serialPort.getPortName());
			} else {
				System.out.println("Port could not be opened.");
				return;
			}
		} catch (final SerialPortException e) {
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Closes the serial port once finished
	 */
	private synchronized void closeSerialComm() {
		if (serialPort.isOpened()) {
			try {
				serialPort.removeEventListener();
				serialPort.closePort();
			} catch (final SerialPortException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Manages the sending of messages to the serial port
	 * @param data
	 */
	private synchronized void writeDataToSerial(final String data) {
		if (serialPort.isOpened()) {
			try {
				if (serialPort.writeBytes(data.getBytes())) {
					System.out.println("Sent: " + data);
				} else {
					System.out.println("Failed to send: " + data);
				}
			} catch (final SerialPortException e) {
				e.printStackTrace();
			}
		}
	}
}