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
		//serialPort = new SerialPort("/dev/ttyUSB0");
		serialPort = new SerialPort("/dev/tty.usbmodemfd121");
		
		try {
			serialPort.openPort();
			serialPort.setParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;
            serialPort.setEventsMask(mask);
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
	public synchronized static void writeData(final int data) {
		displayMessage("Sending: " + data);
		try {
			serialPort.writeInt(data);
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
                final byte buffer[] = serialPort.readBytes();
                displayMessage("Arduino: " + buffer);
            } catch (final SerialPortException ex) {
                System.out.println(ex);
            }
        } else if (event.isCTS()) { //If CTS line has changed state
            if (event.getEventValue() == 1) {//If line is ON
                System.out.println("CTS - ON");
            } else {
                System.out.println("CTS - OFF");
            }
        } else if (event.isDSR()) { ///If DSR line has changed state
            if (event.getEventValue() == 1) { //If line is ON
                System.out.println("DSR - ON");
            } else {
                System.out.println("DSR - OFF");
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