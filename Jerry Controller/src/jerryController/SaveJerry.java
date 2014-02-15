package jerryController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import messageManager.LogMessage;
import messageManager.StatusUpdateMessage;
import panel.Jerry.JerryLogin;
import panel.SSH.SshLogin;

/**
 * Manages the files involved in storing login information for TCP and SSH.
 * @author Jackson Wilson
 * @since 2014
 */
public class SaveJerry {
	private static boolean callFromTCP;
	private static BufferedWriter bufferedWriter;
	
	private static File loginFolder = new File("LOGINS");
	public static File loginTCP = new File(loginFolder, "TCP.txt");
	public static File loginSSH = new File(loginFolder, "SSH.txt");
	
	/**
	 * Opens/Creates, stores a login information, and closes a file specified by the parameters.
	 * @param address
	 * @param callFromTCP
	 */
	public SaveJerry(final String address, final boolean callFromTCP) {
		SaveJerry.callFromTCP = callFromTCP;
		openFile();
		addRecords(address);
		closeFile();
	}
	
	public static void createLoginFile() {
		openFile();
		closeFile();
	}
	
	/**
	 * Opens/Creates the appropriate file, then initializes the BufferedWriter with FileWriter as the parameter.
	 */
	private static void openFile() {
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
			new LogMessage("Error", "IOException in SaveJerry.openFile()");
			new StatusUpdateMessage("Error", "Could not create save file");
			
			if (callFromTCP == true) {
				JerryLogin.saveBtn.setEnabled(true);
			} else if (callFromTCP == false) {
				SshLogin.saveBtn.setEnabled(true);
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
			new LogMessage("Error", "IOException in SaveJerry.addRecords()");
			new StatusUpdateMessage("Error", "Failed to write to save file");
			
			if (callFromTCP == true) {
				JerryLogin.saveBtn.setEnabled(true);
			} else if (callFromTCP == false) {
				SshLogin.saveBtn.setEnabled(true);
			}
		}
	}
	
	/**
	 * Closes the appropriate opened file
	 */
	private static void closeFile() {
		try {
			bufferedWriter.close();
		} catch (final IOException e) {
			new LogMessage("Error", "IOException in SaveJerry.closeFile()");
			new StatusUpdateMessage("Error", "Failed to close save file");
			
			if (callFromTCP == true) {
				JerryLogin.saveBtn.setEnabled(true);
			} else if (callFromTCP == false) {
				SshLogin.saveBtn.setEnabled(true);
			}
		}
	}
}