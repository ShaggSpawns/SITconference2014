package originalFiles;

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
	private SerialPort serialPort;
	
	private final String PORT_NAMES[] = {
			"/dev/tty.usbserial-A9007UX1",
			"/dev/ttyUSB0",
			"COM3",
	};
	
	private BufferedReader input;
	public static OutputStream output;
	private final int TIME_OUT = 2000;
	private final int DATA_RATE = 9600;
	
	public void initialize() {
		CommPortIdentifier portId = null;
		final Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
		
		while (portEnum.hasMoreElements()) {
			final CommPortIdentifier currPortId = ((CommPortIdentifier) portEnum.nextElement());
			for (final String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		
		if (portId == null) {
			System.out.println("Could not find COM port");
			return;
		}
		
		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
			
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();
			
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (final Exception e) {
			System.err.println(e.toString());
		}
	}
	
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	
	public static synchronized void writeData(final String data) {
        try {
            output.write(data.getBytes());
        } catch (final IOException ioE) {
            System.out.println("Insufficient connection with port.");
            System.out.println("Closing Program. Restart to Reconnect.");
        }
	}
	
	public synchronized void serialEvent(final SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				final String inputLine = input.readLine();
				System.out.println("ARDUINO: " + inputLine);
			} catch (final IOException ieException) {
				System.err.println(ieException.toString());
			}
		}
	}
}