package controllerApplication;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import panel.SSH.LoginSSH;
import panel.TCP.LoginTCP;

public class LoadLogins {
	static boolean isTCP;
	
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
			} else if (isTCP == true) {
				LoginSSH.loadComboBox.addItem(str);
			}
		}
	}
	
	private static String [] readFile() {
		final ArrayList<String> arr = new ArrayList<>();
		
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