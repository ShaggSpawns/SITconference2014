package jerryServer;

import jssc.SerialPort;
import jssc.SerialPortException;
/**
 * Initializes a connection with a serial device and manages the connection.
 * @author Jackson Wilson (c) 2014
 */
public class JsscComm {
	
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