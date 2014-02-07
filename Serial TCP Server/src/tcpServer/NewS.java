package tcpServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import serialComm.NewComm;

public class NewS {
	private final int hostPort;
	private final int serverBacklog;
	private ServerSocket server;
	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public NewS(final int port, final int backlog) {
		hostPort = port;
		serverBacklog = backlog;
	}
	
	public void startServer() {
		while(true) {
			try {
				initializeServer();
				waitForClient();
				setupStreams();
				whileChatting();
			} finally {
				cleanUp();
			}
		}
	}
	
	private void initializeServer() {
		try {
			server = new ServerSocket(hostPort, serverBacklog);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	private void waitForClient() {
		displayMessage("Waiting for someone to connect...");
		try {
			connection = server.accept();
		} catch (final IOException e) {
			displayMessage("Failed to connect to the client.");
		}
		displayMessage("Now connected to " + connection.getInetAddress().getHostName());
	}
	
	private void setupStreams() {
		final NewComm main = new NewComm();
		main.initialize();
		try {
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			displayMessage("Streams are setup.");
		} catch (final IOException e) {
			displayMessage("Failed to setup streams.");
		}
	}
	
	private void whileChatting() {
		String inMessage = null;
		do {
			try {
				inMessage = (String) input.readObject();
			} catch (ClassNotFoundException | IOException e) {
				displayMessage("Unable to read incoming message");
			}
			displayMessage(inMessage);
		} while (!inMessage.equals("END"));
	}
	
	private void cleanUp() {
		displayMessage("Closing connection...");
		sendMessage("END");
		try {
			output.close();
			input.close();
			connection.close();
		} catch (final IOException e) {
			displayMessage("Unable to close TCP connection.");
		} catch (NullPointerException npE) {
			displayMessage("Null cleanup");
		}
	}
	
	private void sendMessage(final String message) {
		try {
			output.writeObject("SERVER: " + message);
			output.flush();
			displayMessage("SERVER: " + message);
		} catch (IOException ioE) {
			displayMessage("ioE sendMessage");
		}
	}
	private void displayMessage(final String message) {
		System.out.println(message);
	}
}