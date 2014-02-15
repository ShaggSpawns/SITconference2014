package jerryController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import panel.Jerry.JerryLogin;
import panel.SSH.LoginSSH;

/**
 * Manages the files involved in storing login information for TCP and SSH.
 * @author Jackson Wilson
 * @since 2014
 */
public class SaveJerry {
	private final boolean callFromTCP;
	private BufferedWriter bufferedWriter;
	
	private static File loginFolder = new File("LOGINS");
	public static File loginTCP = new File(loginFolder, "TCP.txt");
	public static File loginSSH = new File(loginFolder, "SSH.txt");
	
	/**
	 * Opens/Creates, stores a login information, and closes a file specified by the parameters.
	 * @param address
	 * @param callFromTCP
	 */
	public SaveJerry(final String address, final boolean callFromTCP) {
		this.callFromTCP = callFromTCP;
		
		openFile();
		addRecords(address);
		closeFile();
	}
	
	/**
	 * Opens/Creates the appropriate file, then initializes the BufferedWriter with FileWriter as the parameter.
	 */
	private void openFile() {
		try {
			if (!loginFolder.exists()) {
				loginFolder.mkdir();
			}
			
			if (callFromTCP == true) {
				if (!loginTCP.exists()) {
					loginTCP.createNewFile();
				}
				
				final FileWriter fileWriter = new FileWriter(loginTCP.getAbsoluteFile(), true);
				bufferedWriter = new BufferedWriter(fileWriter);
			} else if (callFromTCP == false) {
				if (!loginSSH.exists()) {
					loginSSH.createNewFile();
				}
				
				final FileWriter fileWriter = new FileWriter(loginSSH.getAbsoluteFile(), true);
				bufferedWriter = new BufferedWriter(fileWriter);
			}
		} catch (final IOException e) {
			System.out.println("Failed to create login file!");
			
			if (callFromTCP == true) {
				JerryLogin.saveBtn.setEnabled(true);
			} else if (callFromTCP == false) {
				LoginSSH.saveBtn.setEnabled(true);
			}
		}
	}
	
	/**
	 * Adds the specified login information to the appropriate file determined by the initialized BufferedWriter.
	 * @param address
	 */
	private void addRecords(final String address) {
		try {
			bufferedWriter.write(address);
			bufferedWriter.newLine();
		} catch (final IOException e) {
			System.out.println("Failed to write to login file!");
			
			if (callFromTCP == true) {
				JerryLogin.saveBtn.setEnabled(true);
			} else if (callFromTCP == false) {
				LoginSSH.saveBtn.setEnabled(true);
			}
		}
	}
	
	/**
	 * Closes the appropriate opened file
	 */
	private void closeFile() {
		try {
			bufferedWriter.close();
		} catch (final IOException e) {
			System.out.println("Failed to close login file!");
			
			if (callFromTCP == true) {
				JerryLogin.saveBtn.setEnabled(true);
			} else if (callFromTCP == false) {
				LoginSSH.saveBtn.setEnabled(true);
			}
		}
	}
}