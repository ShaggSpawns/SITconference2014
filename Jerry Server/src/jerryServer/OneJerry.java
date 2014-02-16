package jerryServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import jssc.SerialPort;
import jssc.SerialPortException;

public class OneJerry {
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	private static ServerSocket server;
	private static Socket connection;
	private static SerialPort serialPort;
	private static String message = null;
	private static int port = 0;
	
	public static void main(String[] args) {
		while (true) {
			try {
				System.out.print("Enter a port: ");
				port = Integer.parseInt(new Scanner(System.in).next());
				break;
			} catch (NumberFormatException e) {
				System.out.println("\nPort was not an Integer");
			}
		}
		do {
			try {
				runJerry(port);
				whileChatting();
			} finally {
				cleanUp();
			}
		} while (!System.in.equals("exit"));
	}
	
	private static void runJerry(int port) {
		try {
			server = new ServerSocket(port, 5);
			System.out.println("Initialized server on port: " + port);
		} catch (final IOException ioE) {
			System.out.println(port + " is not an opened port.");
		}
		try {
			System.out.println("Waiting for someone to connect...");
			connection = server.accept();
			System.out.println("Now connected to " + connection.getInetAddress().getHostName());
		} catch (final IOException ioE) {
			System.out.println("Could not accept incoming connection");
		}
		try {
			startSerialComm();
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			System.out.println("Streams are now setup!");
			whileChatting();
		} catch (final IOException ioE) {
			System.out.println("Failed to setup streams");
		}
	}
	
	private static void whileChatting() {
		do {
			try {
				message = (String) input.readObject();
				if (serialPort.isOpened() == true) {
					switch (message) {
						case "STOP":
							writeData("0");
							break;
						case "FORWARD":
							writeData("1");
							break;
						case "REVERSE":
							writeData("2");
							break;
						case "RIGHT":
							writeData("3");
							break;
						case "LEFT":
							writeData("4");
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
	
	private static void sendMessage(final String message) {
		try {
			output.writeObject("Jerry: " + message);
			output.flush();
			System.out.println("Sent: " + message);
		} catch (final IOException ioException) {
			System.out.println("Could not send message to Jerry controller");
		}
	}
	
	private static void cleanUp() {
		try {
			output.writeObject("Jerry: END");
			System.out.println("Closing connection...");
			if (serialPort.isOpened()) {
				close();
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
	
	private static void startSerialComm() {
		serialPort = new SerialPort("/dev/ttyUSB0");
		
		try {
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0);
			if (serialPort.isOpened() == true) {
				System.out.println("Serial port is opened on: " + serialPort.getPortName());
			} else {
				System.out.println("Port could not be opened.");
				return;
			}
		} catch (final SerialPortException e) {
			System.out.println(e.toString());
		}
	}
	
	public synchronized static void close() {
		if (serialPort.isOpened() == true) {
			try {
				serialPort.removeEventListener();
				serialPort.closePort();
			} catch (final SerialPortException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized static void writeData(final String data) {
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