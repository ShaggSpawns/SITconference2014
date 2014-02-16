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
 * Manages the files involved in storing login information for JERRY and SSH.
 * @author Jackson Wilson (c) 2014
 */
public class SaveJerry {
	private static String calledFrom;
	private static FileWriter fileWriter;
	private static BufferedWriter bufferedWriter;
	
	private static File jerryFolder = new File("JERRYS");
	public static File loginJERRY = new File(jerryFolder, "JERRY.txt");
	public static File loginSSH = new File(jerryFolder, "SSH.txt");
	
	/**
	 * Opens/Creates, stores a login information, and closes a file specified by the parameters.
	 * @param address
	 * @param callFrom
	 */
	public SaveJerry(final String address, final String callFrom) {
		calledFrom = callFrom;
		openFile();
		addRecords(address);
		closeFile();
	}
	
	public static void createLoginFile(final String callFrom) {
		calledFrom = callFrom;
		openFile();
		closeFile();
	}
	
	/**
	 * Opens/Creates the appropriate file, then initializes the BufferedWriter with FileWriter as the parameter.
	 */
	private static void openFile() {
		try {
			if (!jerryFolder.exists()) {
				jerryFolder.mkdir();
			}
			switch(calledFrom) {
			case "JERRY":
				if (!loginJERRY.exists()) {
					loginJERRY.createNewFile();
				}
				fileWriter = new FileWriter(loginJERRY.getAbsoluteFile(), true);
				bufferedWriter = new BufferedWriter(fileWriter);
				break;
			case "SSH":
				if (!loginSSH.exists()) {
					loginSSH.createNewFile();
				}
				fileWriter = new FileWriter(loginSSH.getAbsoluteFile(), true);
				bufferedWriter = new BufferedWriter(fileWriter);
				break;
			}
		} catch (final IOException e) {
			new LogMessage("Error", "IOException in SaveJerry.openFile()");
			new StatusUpdateMessage("Error", "Could not create save file");
			switch(calledFrom) {
			case "JERRY":
				JerryLogin.saveBtn.setEnabled(true);
				break;
			case "SSH":
				SshLogin.saveBtn.setEnabled(true);
				break;
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
			switch(calledFrom) {
			case "JERRY":
				JerryLogin.saveBtn.setEnabled(true);
				break;
			case "SSH":
				SshLogin.saveBtn.setEnabled(true);
				break;
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
			switch(calledFrom) {
			case "JERRY":
				JerryLogin.saveBtn.setEnabled(true);
				break;
			case "SSH":
				SshLogin.saveBtn.setEnabled(true);
				break;
			}
		}
	}
}