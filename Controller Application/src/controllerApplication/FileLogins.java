package controllerApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import panel.SSH.LoginSSH;
import panel.TCP.LoginTCP;

/**
 * Manages the files involved in storing login information for TCP and SSH.
 * @author Jackson Wilson
 * @since 2014
 */
public class FileLogins {
	private boolean callFromTCP; // Initializes the callFromTCP variable
	private BufferedWriter bufferedWriter; // Initializes the Buffered Writer
	
	static File loginFolder = new File("LOGINS"); // Initializes the folder "LOGINS"
	public static File loginTCP = new File(loginFolder, "TCP.txt"); // Initializes the file "TCP.txt"
	public static File loginSSH = new File(loginFolder, "SSH.txt"); // Initializes the file "SSH.txt"
	
	/**
	 * Opens/Creates, stores a login information, and closes a file specified by the parameters.
	 * @param address
	 * @param callFromTCP
	 */
	public FileLogins(String address, boolean callFromTCP) {
		this.callFromTCP = callFromTCP; // Initializes the callFromTCP variable
		
		openFile(); // Runs the openFile method
		addRecords(address); // Runs the addRecords method
		closeFile(); // Runs the closeFile method
	}
	
	/**
	 * Opens/Creates a file then initializes the BufferedWriter with FileWriter as the parameter.
	 */
	private void openFile() {
		FileWriter fileWriter = null; // Initializes the FileWriter with the name fileWriter
		
		try { // Attempts to open the appropriate file determined by callFromTCP
			if (!loginFolder.exists()) { // Skips the creation of the login folder if it already exists
				loginFolder.mkdir(); // Creates the login folder "LOGINS" if it doesn't exist
			}
			
			if (callFromTCP == true) { // Checks to see if TCP is being updated
				if (!loginTCP.exists()) { // Skips the creation of the login file for TCP logins "TCP.txt" if it already exists
					loginTCP.createNewFile(); // Creates the TCP login file if it doesn't exist
				}
				
				fileWriter = new FileWriter(loginTCP.getAbsoluteFile(), true); // Initializes the FileWriter "fileWriter" with the location of the TCP.txt as the parameter
			} else if (callFromTCP == false) { // Checks to see if SSH is being updated
				if (!loginSSH.exists()) { // Skips the creation of the login file for SSH logins "SSH.txt" if it already exists
					loginSSH.createNewFile(); // Creates the SSH login file if it doesn't exist
				}
				
				fileWriter = new FileWriter(loginSSH.getAbsoluteFile(), true); // Initializes the FileWriter "fileWriter" with the location of the SSH.txt as the parameter
			}
		
			bufferedWriter = new BufferedWriter(fileWriter); // Initializes the BufferedWriter "bufferedWriter" with fileWriter
		} catch (IOException e) { // Catches an IOException while initializing the FileWriter 
			System.out.println("Failed to create login file!"); // Prints the error message
			
			if (callFromTCP == true) { // Checks to see if TCP is being updated
				LoginTCP.saveBtn.setEnabled(true); // Enables the TCP save button after an error
			} else if (callFromTCP == false) { // Checks to see if SSH is being updated
				LoginSSH.saveBtn.setEnabled(true); // Enables the SSH save button after an error
			}
		}
	}
	
	/**
	 * Adds specified login information to the appropriate file determined by the initialized BufferedWriter.
	 * @param address
	 */
	private void addRecords(String address) {
		try { // Attempts to write "address" to the opened file
			bufferedWriter.write(address); // Writes "address" to opened file
			bufferedWriter.newLine(); // Writes a new line
		} catch (IOException e) { // Catches an error while writing to the opened file
			System.out.println("Failed to write to login file!"); // Prints error message
			
			if (callFromTCP == true) { // Checks to see if TCP is being updated
				LoginTCP.saveBtn.setEnabled(true); // Enables the TCP save button after an error
			} else if (callFromTCP == false) { // Checks to see if SSH is being updated
				LoginSSH.saveBtn.setEnabled(true); // Enables the SSH save button after an error
			}
		}
	}
	
	/**
	 * Closes the opened file
	 */
	private void closeFile() {
		try { // Attempts to close the BufferedWriter
			bufferedWriter.close(); // Closes the BufferedWriter "bufferedWriter"
		} catch (IOException e) { // Catches an error while closing the BufferedWriter
			System.out.println("Failed to close login file!"); // Prints error message
			
			if (callFromTCP == true) { // Checks to see if TCP is being updated
				LoginTCP.saveBtn.setEnabled(true); // Enables the TCP save button after an error
			} else if (callFromTCP == false) { // Checks to see if SSH is being updated
				LoginSSH.saveBtn.setEnabled(true); // Enables the SSH save button after an error
			}
		}
	}
}