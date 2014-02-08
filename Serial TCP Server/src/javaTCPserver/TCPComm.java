package javaTCPserver;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class TCPComm extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static int firstPort;
	private static int lastPort;
	private static int backlog;
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	private static ServerSocket server;
	private static Socket connection;
	
	public TCPComm(final int portRangeStart, final int portRangeEnd, final int serverBacklog) {
		firstPort = portRangeStart;
		lastPort = portRangeEnd;
		backlog = serverBacklog;
	}
	
	public static void startRunning() {
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
	
	private static void waitForConnection() throws IOException {
		displayMessage("Waiting for someone to connect...");
		connection = server.accept();
		displayMessage("Now connected to " + connection.getInetAddress().getHostName());
	}
	
	private static void setupStreams() throws IOException {
		//final SerialComm serialComm = new SerialComm(2000, 9600);
		//serialComm.startSerialComm();
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		displayMessage("Streams are now setup!");
	}
	
	private static void whileChatting() throws IOException {
		String message = null;
		do {
			try {
				message = (String) input.readObject();
				displayMessage(message);
				if (SerialComm.isSerialIsConnected() == true) {
					switch (message) {
						case "FORWARD":
							SerialComm.writeData("F");
							break;
						case "REVERSE":
							SerialComm.writeData("B");
							break;
						case "RIGHT":
							SerialComm.writeData("R");
							break;
						case "LEFT":
							SerialComm.writeData("L");
							break;
						case "STOP":
							SerialComm.writeData("S");
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
	
	private static void cleanUp() {
		displayMessage("Closing connection...");
		sendMessage("END");
		try {
			//SerialComm.close();
			output.close();
			input.close();
			connection.close();
		}catch(final IOException ioException){
			ioException.printStackTrace();
		}
	}
	
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
	
	public static void displayMessage(final String message) {
		System.out.println(message);
	}
}