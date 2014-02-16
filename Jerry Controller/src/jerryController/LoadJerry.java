package jerryController;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import messageManager.LogMessage;
import messageManager.StatusUpdateMessage;
import panel.Jerry.JerryLogin;
import panel.SSH.SshLogin;

/**
 * Manages the saving and loading of logins of JERRY and SSH.
 * @author Jackson Wilson (c) 2014
 */
public class LoadJerry {
	private static String calledFrom;
	private static String defaultJerryMessage;
	private static String defaultSshMessage;
	
	/**
	 * Reloads the appropriate JComboBox with an updated list of the logins for that JComboBox by removing all existing entries and replaces them with the ones in the login.txt file.
	 * @param calledFor
	 */
	public static void addLogins(final String calledFor) {
		calledFrom = calledFor;
		String[] lines;
		lines = readFile();
		switch(Jerry.getOperatingSystem()) {
		case "Windows":
			defaultJerryMessage = "---------------------- Load Save ----------------------";
			defaultSshMessage = "----------------------- Load Save -----------------------";
			break;
		case "Mac":
			defaultJerryMessage = "----------- Load Save ----------";
			defaultSshMessage = "---------- Load Save ----------";
			break;
		case "Default":
			defaultJerryMessage = "----------- Load Save ----------";
			defaultSshMessage = "---------- Load Save ----------";
			break;
		}
		String currentTcpHost = null;
		String currentTcpPort = null;
		String currentSshHost = null;
		String currentSshPort = null;
		String currentSshUser = null;
		char[] currentSshPass = null;
		
		switch(calledFrom) {
		case "JERRY":
			currentTcpHost = JerryLogin.hostF.getText();
			currentTcpPort = JerryLogin.portF.getText();
			JerryLogin.loadComboBox.removeAllItems();
			JerryLogin.loadComboBox.addItem(defaultJerryMessage);
			break;
		case "SSH":
			currentSshHost = SshLogin.hostF.getText();
			currentSshPort = SshLogin.portF.getText();
			currentSshUser = SshLogin.usernameF.getText();
			currentSshPass = SshLogin.passwordF.getPassword();
			SshLogin.loadComboBox.removeAllItems();
			SshLogin.loadComboBox.addItem(defaultSshMessage);
			break;
		}
		
		for (final String str: lines) {
			switch(calledFrom) {
			case "JERRY":
				JerryLogin.loadComboBox.addItem(str);
				break;
			case "SSH":
				SshLogin.loadComboBox.addItem(str);
				break;
			}
		}
		
		switch(calledFrom) {
		case "JERRY":
			JerryLogin.hostF.setText(currentTcpHost);
			JerryLogin.portF.setText(currentTcpPort);
			break;
		case "SSH":
			SshLogin.hostF.setText(currentSshHost);
			SshLogin.portF.setText(currentSshPort);
			SshLogin.usernameF.setText(currentSshUser);
			SshLogin.passwordF.setText(new String(currentSshPass));
			break;
		}
	}
	
	/**
	 * Reads the file and returns the contents of the file into a String.
	 * @return File entry
	 */
	private static String[] readFile() {
		final ArrayList<String> arr = new ArrayList<String>();
		FileInputStream fStream;
		BufferedReader bReader;
		String strLine;
		try {
			switch(calledFrom) {
			case "JERRY":
				fStream = new FileInputStream(SaveJerry.loginJERRY.getAbsoluteFile());
				bReader = new BufferedReader(new InputStreamReader(fStream));
				while ((strLine = bReader.readLine()) != null) {
					arr.add(strLine);
				}
				fStream.close();
				break;
			case "SSH":
				fStream = new FileInputStream(SaveJerry.loginSSH.getAbsoluteFile());
				bReader = new BufferedReader(new InputStreamReader(fStream));
				while ((strLine = bReader.readLine()) != null) {
					arr.add(strLine);
				}
				fStream.close();
				break;
			}
		} catch (final FileNotFoundException e) {
			SaveJerry.createLoginFile("JERRY");
		} catch (final IOException e) {
			new LogMessage("Error", "IOExcpetion in LeadJerry.readFile()");
			new StatusUpdateMessage("Error", "Could not load Jerrys");
		}
		return arr.toArray(new String[arr.size()]);
	}
}