package javaTCPserver;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class JsscComm implements SerialPortEventListener {
	
	public static SerialPort serialPort;
	
	public JsscComm(final int dataRate) {
		startSerialComm(dataRate);
	}
	
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
		try {
			serialPort.writeBytes(data.getBytes());
		} catch (final SerialPortException e) {
			displayMessage(e.toString());
		}
	}
	
	@Override
	public void serialEvent(final SerialPortEvent event) {
		if (event.isRXCHAR()) {
			try {
				final byte[] inputMessage = serialPort.readBytes();
				displayMessage("ARDUINO: " + inputMessage.toString());
			} catch (final SerialPortException e) {
				displayMessage(e.toString());
			}
		}
	}
	
	private static void displayMessage(final String message) {
		TCPComm.displayMessage(message);
	}
}