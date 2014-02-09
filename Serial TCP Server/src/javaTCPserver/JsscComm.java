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
	public JsscComm(final int dataRate) {
		startSerialComm(dataRate);
	}
	
	/**
	 * Starts the serial communication with the connected device with a given baud rate.
	 * @param DATA_RATE
	 */
	public void startSerialComm(final int DATA_RATE) {
		serialPort = new SerialPort("/dev/ttyUSB0");
		
		try {
			serialPort.openPort();
			serialPort.setParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			if (serialPort.isOpened() == true) {
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
		try {
			serialPort.writeBytes(data.getBytes());
		} catch (final SerialPortException e) {
			displayMessage(e.toString());
		}
	}
	
	/**
	 * Reads incoming data from the serial port and displays them to the user.
	 */
	@Override
	public void serialEvent(final SerialPortEvent event) {
		if (event.isRXCHAR()) {
			try {
				final byte[] inputMessage = serialPort.readBytes();
				displayMessage(inputMessage.toString());
			} catch (final SerialPortException e) {
				displayMessage(e.toString());
			}
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