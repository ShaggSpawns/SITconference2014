package javaTCPserver;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;
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
		//serialPort = new SerialPort("/dev/ttyUSB0");
		serialPort = new SerialPort("/dev/tty.usbmodemfd121");
		
		try {
			serialPort.openPort();
			serialPort.setParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			if (serialPort.isOpened() == true) {
				//displayMessage("Serial port is opened on: " + serialPort.getPortName());
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
		displayMessage("Sending: " + data);
		try {
			//serialPort.writeBytes(data.getBytes());
			serialPort.writeString(data);
		} catch (final SerialPortException e) {
			displayMessage(e.toString());
		}
	}
	
	/**
	 * Reads incoming data from the serial port and displays them to the user.
	 */
	@Override
	public void serialEvent(final SerialPortEvent event) {
		try {
			displayMessage("R1: " + serialPort.readString(20, 20));
			//displayMessage("R2: " + serialPort.readBytes());
			//displayMessage("R3: " + serialPort.readHexString());
			//displayMessage("R4: Motors: " + serialPort.readString());
		} catch (SerialPortException | SerialPortTimeoutException e) {
			e.printStackTrace();
		}
		/*if (event.isRXCHAR()) {
			try {
				final byte[] inputMessage = serialPort.readBytes();
				displayMessage(inputMessage.toString());
			} catch (final SerialPortException e) {
				displayMessage(e.toString());
			}
		//}*/
	}
	
	/**
	 * Displays messages to the user.
	 * @param message
	 */
	private static void displayMessage(final String message) {
		TCPComm.displayMessage(message);
	}
}