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
 * Manages the saving and loading of logins of TCP and SSH.
 * @author Jackson Wilson (c) 2014
 */
public class LoadJerry {
	private static String from;
	private static String defaultTcpMessage;
	private static String defaultSshMessage;
	
	/**
	 * Reloads the appropriate JComboBox with an updated list of the logins for that JComboBox by removing all existing entries and replaces them with the ones in the login.txt file.
	 * @param istcp
	 */
	public static void addLogins(final String calledFor) {
		from = calledFor;
		String[] lines;
		lines = readFile();
		switch(Jerry.getOperatingSystem()) {
		case "Windows":
			defaultTcpMessage = "---------------------- Load Save ----------------------";
			defaultSshMessage = "----------------------- Load Save -----------------------";
			break;
		case "Mac":
			defaultTcpMessage = "----------- Load Save ----------";
			defaultSshMessage = "---------- Load Save ----------";
			break;
		case "Default":
			defaultTcpMessage = "----------- Load Save ----------";
			defaultSshMessage = "---------- Load Save ----------";
			break;
		}
		String currentTcpHost = null;
		String currentTcpPort = null;
		String currentSshHost = null;
		String currentSshPort = null;
		String currentSshUser = null;
		char[] currentSshPass = null;
		
		if (from.equals("TCP")) {
			currentTcpHost = JerryLogin.hostF.getText();
			currentTcpPort = JerryLogin.portF.getText();
			JerryLogin.loadComboBox.removeAllItems();
			JerryLogin.loadComboBox.addItem(defaultTcpMessage);
		} else if (from.equals("SSH")) {
			currentSshHost = SshLogin.hostF.getText();
			currentSshPort = SshLogin.portF.getText();
			currentSshUser = SshLogin.usernameF.getText();
			currentSshPass = SshLogin.passwordF.getPassword();
			SshLogin.loadComboBox.removeAllItems();
			SshLogin.loadComboBox.addItem(defaultSshMessage);
		}
		
		for (final String str: lines) {
			if (from.equals("TCP")) {
				JerryLogin.loadComboBox.addItem(str);
			} else if (from.equals("SSH")) {
				SshLogin.loadComboBox.addItem(str);
			}
		}
		
		if (from.equals("TCP")) {
			JerryLogin.hostF.setText(currentTcpHost);
			JerryLogin.portF.setText(currentTcpPort);
		} else if (from.equals("SSH")) {
			 SshLogin.hostF.setText(currentSshHost);
			 SshLogin.portF.setText(currentSshPort);
			 SshLogin.usernameF.setText(currentSshUser);
			 SshLogin.passwordF.setText(new String(currentSshPass));
		}
	}
	
	/**
	 * Reads the file and returns the contents of the file into a String.
	 * @return
	 * @throws FileNotFoundException 
	 */
	private static String[] readFile() {
		final ArrayList<String> arr = new ArrayList<String>();
		try {
			if (from.equals("TCP")) {
				final FileInputStream fstream = new FileInputStream(SaveJerry.loginTCP.getAbsoluteFile());
				final BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				
				String strLine;
				
				while ((strLine = br.readLine()) != null) {
					arr.add(strLine);
				}
				
				fstream.close();
			} else if (from.equals("SSH")) {
				final FileInputStream fstream = new FileInputStream(SaveJerry.loginSSH.getAbsoluteFile());
				final BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				
				String strLine;
				
				while ((strLine = br.readLine()) != null) {
					arr.add(strLine);
				}
				
				fstream.close();
			}
		} catch (final FileNotFoundException e) {
			new LogMessage("Error", "FileNotFoundException in LoadJerry.readFile()");
			new StatusUpdateMessage("Error", "Could not load Jerrys");
		} catch (final IOException e) {
			new LogMessage("Error", "IOExcpetion in LeadJerry.readFile()");
			new StatusUpdateMessage("Error", "Could not load Jerrys");
		}
		return arr.toArray(new String[arr.size()]);
	}
}