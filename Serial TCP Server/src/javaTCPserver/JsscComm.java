package javaTCPserver;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
/**
 * Initializes a connection with a serial device and manages the connection.
 * @author Jackson Wilson (c) 2014
 */
public class JsscComm implements SerialPortEventListener {
	
	public static SerialPort serialPort;
	
	/**
	 * Starts the serial communication with the connected device with a given baud rate.
	 * @param dataRate
	 */
	public JsscComm() {
		startSerialComm();
	}
	
	/**
	 * Starts the serial communication with the connected device with a given baud rate.
	 * @param DATA_RATE
	 */
	public void startSerialComm() {
		//serialPort = new SerialPort("/dev/ttyUSB0");
		serialPort = new SerialPort("/dev/tty.usbmodemfd1231");
		
		try {
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0);
			if (serialPort.isOpened() == true) {
				displayMessage("Serial port is opened on: " + serialPort.getPortName());
				serialPort.addEventListener(this);
			} else {
				displayMessage("Port could not be opened.");
				return;
			}
		} catch (final SerialPortException e) {
			displayMessage(e.toString());
		}
	}
	
	/**
	 * Closes the serial port.
	 */
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

	/**
	 * Manages the writing of data to the serial port.
	 * @param data
	 */
	public synchronized static void writeData(final String data) {
		if (serialPort.isOpened()) {
			try {
				if (serialPort.writeBytes(data.getBytes())) {
					displayMessage("Sent: " + data);
				} else {
					displayMessage("Failed to send: " + data);
				}
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Reads incoming data from the serial port and displays them to the user.
	 */
	@Override
	public void serialEvent(final SerialPortEvent event) {
		if (serialPort.isOpened()) {
	        //try {
	            //displayMessage("Received: " + serialPort.readString());
	        //} catch (final SerialPortException e) {
	            //System.out.println(e);
	        //}
		}
	}
	
	/**
	 * Displays messages to the user.
	 * @param message
	 */
	private static void displayMessage(final String message) {
		TCPComm.displayMessage(message);
	}
}