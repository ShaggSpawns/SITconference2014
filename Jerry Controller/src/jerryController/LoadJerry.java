package jerryController;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import panel.Jerry.JerryLogin;
import panel.SSH.LoginSSH;

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
		switch(IdentifyOS.getOperatingSystem()) {
		case "Windows":
			defaultTcpMessage = "----------- Load Jerry ----------";
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
			currentTcpHost = JerryLogin.hostField.getText();
			currentTcpPort = JerryLogin.portField.getText();
			JerryLogin.loadComboBox.removeAllItems();
			JerryLogin.loadComboBox.addItem(defaultTcpMessage);
		} else if (from.equals("SSH")) {
			currentSshHost = LoginSSH.hostIPF.getText();
			currentSshPort = LoginSSH.hostPortF.getText();
			currentSshUser = LoginSSH.usernameF.getText();
			currentSshPass = LoginSSH.passwordF.getPassword();
			LoginSSH.loadComboBox.removeAllItems();
			LoginSSH.loadComboBox.addItem(defaultSshMessage);
		}
		
		for (final String str: lines) {
			if (from.equals("TCP")) {
				JerryLogin.loadComboBox.addItem(str);
			} else if (from.equals("SSH")) {
				LoginSSH.loadComboBox.addItem(str);
			}
		}
		
		if (from.equals("TCP")) {
			JerryLogin.hostField.setText(currentTcpHost);
			JerryLogin.portField.setText(currentTcpPort);
		} else if (from.equals("SSH")) {
			 LoginSSH.hostIPF.setText(currentSshHost);
			 LoginSSH.hostPortF.setText(currentSshPort);
			 LoginSSH.usernameF.setText(currentSshUser);
			 LoginSSH.passwordF.setText(new String(currentSshPass));
		}
	}
	
	/**
	 * Reads the file and returns the contents of the file into a String.
	 * @return
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
		} catch (final Exception e) {
			
		}
		return arr.toArray(new String[arr.size()]);
	}
}