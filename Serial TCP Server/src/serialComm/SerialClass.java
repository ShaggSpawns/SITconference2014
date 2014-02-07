package serialComm;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

public class SerialClass implements SerialPortEventListener {
 
	/*
	 * Array of possible serial connections
	 */
	public SerialPort serialPort;
	private static final String PORT_NAMES[] = {
		"/dev/tty.usbmodem131", // Apple OS X
		"/dev/ttyUSB0", // Linux
		"COM3", // Windows
	};
	
	/*
	 * Declare Serial Communication Methods
	 */
	public static BufferedReader input;
	public static OutputStream output;
	public static final int TIME_OUT = 2000;
	public static final int DATA_RATE = 9600;
	
	/*
	 * Connecting to Serial Port
	 */
	public void initialize() {
		CommPortIdentifier portId = null;
		@SuppressWarnings("rawtypes")
		final
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		
		// Determine if a serial connection has been made
		while (portEnum.hasMoreElements()) {
			final CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (final String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					System.out.println("Serial Conmunication Connected: " + portName +
							"\nEnter '6' to show help.");
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Unable to find COM port. Restart to try again.");
			return;
		}
		
		/*
		 * Open the Serial Port
		 */
		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);
			
			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			
			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();
			final char ch = 1;
			output.write(ch);
			
			
			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (final Exception e) {
			System.err.println(e.toString());
		}
	}
	
	/*
	 * Close the Serial Port
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	
	/*
	 * Decode input and display to console
	 */
	public synchronized void serialEvent(final SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				final String inputLine=input.readLine();
				System.out.println(inputLine);
			} catch (final Exception e) {
				System.err.println(e.toString());
			}
		}
		
	}
}