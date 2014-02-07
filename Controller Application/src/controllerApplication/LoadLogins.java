package controllerApplication;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import panel.SSH.LoginSSH;
import panel.TCP.LoginTCP;

/**
 * Manages the saving and loading of logins of TCP and SSH.
 * @author Jackson Wilson (c) 2014
 */
public class LoadLogins {
	static boolean isTCP;
	
	/**
	 * Reloads the appropriate JComboBox with an updated list of the logins for that JComboBox by removing all existing entries and replaces them with the ones in the login.txt file.
	 * @param istcp
	 */
	public static void addLogins(final boolean istcp) {
		isTCP = istcp;
		String[] lines;
		lines = readFile();
		final String defaultTcpMessage = "----------- Load Save ----------";
		final String defaultSshMessage = "---------- Load Save ----------";
		
		if (isTCP == true) {
			LoginTCP.loadComboBox.removeAllItems();
			LoginTCP.loadComboBox.addItem(defaultTcpMessage);
		} else if(isTCP == true) {
			LoginSSH.loadComboBox.removeAllItems();
			LoginSSH.loadComboBox.addItem(defaultSshMessage);
		}
		
		for (final String str: lines) {
			if (isTCP == true) {
				LoginTCP.loadComboBox.addItem(str);
			} else if (isTCP == false) {
				LoginSSH.loadComboBox.addItem(str);
			}
		}
	}
	
	/**
	 * Reads the file and returns the contents of the file into a String.
	 * @return
	 */
	private static String[] readFile() {
		final ArrayList<String> arr = new ArrayList<String>();
		
		try {
			if (isTCP == true) {
				final FileInputStream fstream = new FileInputStream(FileLogins.loginTCP.getAbsoluteFile());
				final BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				
				String strLine;
				
				while ((strLine = br.readLine()) != null) {
					arr.add(strLine);
				}
				
				fstream.close();
			} else if (isTCP == false) {
				final FileInputStream fstream = new FileInputStream(FileLogins.loginSSH.getAbsoluteFile());
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