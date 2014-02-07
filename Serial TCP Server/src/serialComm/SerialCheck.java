package serialComm;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

/*
 * Determine open serial ports
 */
public class SerialCheck {

	@SuppressWarnings("rawtypes")
	public static void main(final String[] args) {
		
		//Program start message
		System.out.print("Identifing Serial Connections...\nConnected to: ");
		
	    CommPortIdentifier serialPortId;
	    
	    Enumeration enumComm;
	    enumComm = CommPortIdentifier.getPortIdentifiers();
	    
	    // Comparison to determine if port is open
	    while (enumComm.hasMoreElements()) {
	     	serialPortId = (CommPortIdentifier) enumComm.nextElement();
	    
	     	if(serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	    		System.out.println(serialPortId.getName());
	     	}
	    }
	    
	    // Program end message
		System.out.println("Finished successfully");
	}
}