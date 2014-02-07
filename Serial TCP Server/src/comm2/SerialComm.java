package comm2;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

public class SerialComm implements SerialPortEventListener {
	private static SerialPort serialPort;
	private BufferedReader serialInput;
	public static OutputStream serialOutput;
	private final int TIME_OUT;
	private final int DATA_RATE;
	private static boolean serialIsConnected = false;
	
	private final String PORT_NAMES[] = {
			"/dev/tty.usbserial-A9007UX1",
			"/dev/ttyUSB0",
			"COM3",
	};
	
	SerialComm(final int timeOut, final int dataRate) {
		TIME_OUT = timeOut;
		DATA_RATE = dataRate;
	}

	public void startSerialComm() {
		CommPortIdentifier portId = null;
		final Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
		
		while (portEnum.hasMoreElements()) {
			final CommPortIdentifier currPortId = ((CommPortIdentifier) portEnum.nextElement());
			for (final String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)); {
					portId = currPortId;
					break;
				}
			}
		}
		
		if (portId == null) {
			displayMessage("Unable to find COM port.");
			setSerialIsConnected(false);
			return;
		}
		
		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
			
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			
			serialInput = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			serialOutput = serialPort.getOutputStream();
			setSerialIsConnected(true);
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (final Exception e) {
			displayMessage(e.toString());
			setSerialIsConnected(false);
		}
	}
	
	public synchronized static void close() {
		setSerialIsConnected(false);
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	
	public synchronized static void writeData(final String data) {
		try {
			serialOutput.write(data.getBytes());
		} catch (final IOException ioE) {
			displayMessage(ioE.toString());
		}
	}

	@Override
	public void serialEvent(final SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				final String inputMessage = serialInput.readLine();
				displayMessage("ARDUINO: " + inputMessage);
			} catch (final IOException ioE) {
				displayMessage(ioE.toString());
			}
		}
	}
	
	private static void displayMessage(final String message) {
		TCPComm.displayMessage(message);
	}

	public static boolean isSerialIsConnected() {
		return serialIsConnected;
	}

	public static void setSerialIsConnected(final boolean serialIsConnected) {
		SerialComm.serialIsConnected = serialIsConnected;
		TCPComm.sendMessage("Serial was unable to be established.");
	}
}