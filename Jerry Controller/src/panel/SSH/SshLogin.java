package panel.SSH;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import messageManager.LogMessage;
import messageManager.StatusUpdateMessage;
import jerryController.LoadJerry;
import jerryController.SaveJerry;

/**
 * Creates the Login SSH panel for the SSH tab
 * @author Jackson Wilson (c) 2014
 */
public class SshLogin extends JPanel implements ItemListener, ActionListener {
	private static final long serialVersionUID = 1L;
	
	private final JLabel hostL;
	private final JLabel hostCOLON;
	private final JLabel usernameL;
	private final JLabel passwordL;
	public static JTextField hostF;
	public static JTextField portF;
	public static JTextField usernameF;
	public static JPasswordField passwordF;
	public static JToggleButton connectBtn;
	public static JButton saveBtn;
	public static JComboBox<String> loadComboBox;
	private String address;
	private int fillLine = 0;
	
	/**
	 * Creates the Login SSH panel for the SSH tab
	 */
	public SshLogin(final String OS) {
		setBorder(BorderFactory.createTitledBorder("SSH Login"));
		setLayout(new GridBagLayout());
		
		final GridBagConstraints gc = new GridBagConstraints();
		
		hostL = new JLabel();
		switch(OS) {
		case "Windows":
			hostL.setText("SSH Host : ");
			break;
		case "Mac":
			hostL.setText("SSH Host:");
			break;
		case "Default":
			hostL.setText("SSH Host:");
			break;
		}
		hostL.setToolTipText("Enter the IP and port of SSH host");
		gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 0;
		gc.gridy = 0;
		add(hostL, gc);
		
		usernameL = new JLabel();
		switch(OS) {
		case "Windows":
			usernameL.setText("Username : ");
			break;
		case "Mac":
			usernameL.setText("Username:");
			break;
		case "Default":
			usernameL.setText("Username:");
			break;
		}
		usernameL.setToolTipText("Enter the username of the SSH host");
		gc.gridy = 1;
		add(usernameL, gc);
		
		passwordL = new JLabel();
		switch(OS) {
		case "Windows":
			passwordL.setText("Password : ");
			break;
		case "Mac":
			passwordL.setText("Password:");
			break;
		case "Default":
			passwordL.setText("Password:");
			break;
		}
		passwordL.setToolTipText("Enter the password of the SSH host");
		gc.gridy = 2;
		add(passwordL, gc);
		
		saveBtn = new JButton("Save");
		saveBtn.setToolTipText("Save current SSH host");
		saveBtn.setEnabled(false);
		saveBtn.addActionListener(this);
		switch(OS) {
		case "Windows":
			gc.anchor = GridBagConstraints.CENTER;
			break;
		case "Mac":
			break;
		case "Default":
			break;
		}
		gc.gridy = 3;
		add(saveBtn, gc);
		
		hostF = new JTextField("");
		hostF.setToolTipText("Host IP");
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.HORIZONTAL;
		switch(OS) {
		case "Windows":
			gc.ipadx = 275;
			gc.gridx = 1;
			gc.gridy = 0;
			break;
		case "Mac":
			gc.ipadx = 170;
			gc.gridx = 1;
			gc.gridy = 0;
			break;
		case "Default":
			gc.ipadx = 170;
			gc.gridx = 1;
			gc.gridy = 0;
			break;
		}
		add(hostF, gc);
		
		hostCOLON = new JLabel(": ");
		gc.fill = GridBagConstraints.NONE;
		gc.ipadx = 0;
		gc.gridx = 2;
		add(hostCOLON, gc);
		
		portF = new JTextField("22");
		portF.setToolTipText("Host Port");
		gc.fill = GridBagConstraints.HORIZONTAL;
		switch(OS) {
		case "Windows":
			gc.insets = new Insets(0,0,0,2);
			break;
		case "Mac":
			gc.insets = new Insets(0,0,0,3);
			break;
		case "Default":
			break;
		}
		gc.ipadx = 50;
		gc.gridx = 3;
		add(portF, gc);
		
		usernameF = new JTextField("");
		gc.insets = new Insets(0,0,0,0);
		gc.ipadx = 170;
		gc.gridx = 1;
		gc.gridy = 1;
		add(usernameF, gc);
		
		passwordF = new JPasswordField("peace1");
		passwordF.addActionListener(this);
		gc.gridy = 2;
		add(passwordF, gc);
		
		connectBtn = new JToggleButton("Connect");
		connectBtn.addItemListener(this);
		gc.fill = GridBagConstraints.BOTH;
		switch(OS) {
		case "Windows":
			gc.insets = new Insets(2,3,1,3);
			break;
		case "Mac":
			gc.insets = new Insets(0,3,0,3);
			break;
		case "Default":
			break;
		}
		gc.ipadx = 0;
		gc.gridwidth = 2;
		gc.gridheight = 3;
		gc.gridx = 2;
		gc.gridy = 1;
		add(connectBtn, gc);
		
		loadComboBox = new JComboBox<String>();
		LoadJerry.addLogins("SSH");
		loadComboBox.addActionListener(this);
		gc.fill = GridBagConstraints.HORIZONTAL;
		switch(OS) {
		case "Windows":
			gc.insets = new Insets(0,0,0,1);
			break;
		case "Mac":
			gc.insets = new Insets(0,0,0,0);
			break;
		case "Default":
			break;
		}
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.gridx = 1;
		gc.gridy = 3;
		add(loadComboBox, gc);
	}
	
	/**
	 * Auto-fills SSH login fields with the stored entries
	 * @param line
	 */
	private void fillFields(final int line) {
		try {
			final FileInputStream fs = new FileInputStream(SaveJerry.loginSSH.getAbsoluteFile());
			@SuppressWarnings("resource")
			final BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			for (int i = 1; i < line; ++i) {
				br.readLine();
			}
			
			final String entry = br.readLine();
			final String[] ar1 = entry.split("@");
			final String[] ar2 = ar1[1].split(":");
			
			final String host = ar2[0];
			final String port = ar2[1];
			final String user = ar1[0];
			
			hostF.setText(host);;
			portF.setText(port);
			usernameF.setText(user);
		} catch (final ArrayIndexOutOfBoundsException e) {
			new StatusUpdateMessage("Error", "Failed to fill fields");
			new LogMessage("Error", "ArrayIndexOutOfBoundsException in LoginSSH.fillFields()");
		} catch (final FileNotFoundException e) {
			new StatusUpdateMessage("Error", "Failed to fill fields");
			new LogMessage("Error", "FileNotFoundException in LoginSSH.fillFields()");
		} catch (final IOException e) {
			new StatusUpdateMessage("Error", "Failed to fill fields");
			new LogMessage("Error", "IOException in LoginSSH.fillFields()");
		} catch (final NullPointerException e) {
			new StatusUpdateMessage("Error", "Failed to fill fields");
			new LogMessage("Error", "NullPointerException in LoginSSH.fillFields()");
			e.printStackTrace();
		}
	}
	
	/**
	 * Empties the SSH login fields
	 */
	private void emptyFields() {
		hostF.setText("");;
		portF.setText("");
		usernameF.setText("");
		passwordF.setText("");
	}
	
	/**
	 * Changes the availability of the components on the SSH tab, defined by the state parameter
	 * @param state
	 */
	public static void changeTCPguiState(final String state) {
		switch(state) {
		case "Disconnected":
			hostF.setEditable(true);
			hostF.setFocusable(true);
			portF.setEditable(true);
			portF.setFocusable(true);
			usernameF.setEditable(true);
			usernameF.setFocusable(true);
			passwordF.setEditable(true);
			passwordF.setFocusable(true);
			saveBtn.setEnabled(false);
			loadComboBox.setEnabled(true);
			SshConsole.consoleArea.setEnabled(false);
			SshConsole.consoleInput.setEnabled(false);
			connectBtn.setText("Connect");
			break;
		case "Pending":
			hostF.setEditable(false);
			hostF.setFocusable(false);
			portF.setEditable(false);
			portF.setFocusable(false);
			usernameF.setEditable(false);
			usernameF.setFocusable(false);
			passwordF.setEditable(false);
			passwordF.setFocusable(false);
			saveBtn.setEnabled(false);
			loadComboBox.setEnabled(false);
			SshConsole.consoleArea.setText("");
			SshConsole.consoleArea.setEnabled(false);
			SshConsole.consoleInput.setEnabled(false);
			connectBtn.setText("Pending");
			break;
		case "Connected":
			hostF.setEditable(false);
			hostF.setFocusable(false);
			portF.setEditable(false);
			portF.setFocusable(false);
			usernameF.setEditable(false);
			usernameF.setFocusable(false);
			passwordF.setEditable(false);
			passwordF.setFocusable(false);
			saveBtn.setEnabled(true);
			loadComboBox.setEnabled(false);
			SshConsole.consoleArea.setEnabled(true);
			SshConsole.consoleInput.setEnabled(true);
			connectBtn.setText("Disconnect");
			break;
		}
	}
	
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == loadComboBox) {
			fillLine = loadComboBox.getSelectedIndex();
			if (fillLine == 0) {
				emptyFields();
			} else {
				fillFields(fillLine);
			}
		} else if (e.getSource() == passwordF) {
			connectBtn.doClick();
		} else if (e.getSource() == saveBtn) {
			saveBtn.setEnabled(false);
			new SaveJerry(address, "SSH");
		}
	}
	
	public void itemStateChanged(final ItemEvent ev) {
		final String ip = hostF.getText();
		final String inPort = portF.getText();
		final String username = usernameF.getText();
		final char[] password = passwordF.getPassword();
		int port = 0;
		int intport;
		address = username + "@" + ip + ":" + inPort;
		if (ev.getStateChange() == ItemEvent.SELECTED) {
			connectBtn.setText("Disconnect");
			changeTCPguiState("Pending");
			if (!(ip.equals(""))) {
				try {
					if (!(inPort.equals(""))) {
						intport = Integer.parseInt(inPort);
						port = intport;
							if (!(username.equals(""))) {
								if (!(password.equals(""))){
									new Thread(new SshConnection(ip, port, username, password)).start();
								} else {
									new StatusUpdateMessage("Error", "Password field can not be empty!");
									new LogMessage("Error", "Could not connect with (" + address + ")");
									connectBtn.setSelected(true);
									connectBtn.doClick();
								}
							} else {
								new StatusUpdateMessage("Error", "Username field can not be empty!");
								new LogMessage("Error", "Could not connect with (" + address + ")");
								connectBtn.setSelected(true);
								connectBtn.doClick();
							}
						} else { 
							new StatusUpdateMessage("Error", "Port field can not be empty!");
							new LogMessage("Error", "Could not connect with (" + address + ")");
							connectBtn.setSelected(true);
							connectBtn.doClick();	
						}
				} catch (final NumberFormatException nFE) {
					new StatusUpdateMessage("Error", "Port is not a number!");
					new LogMessage("Error", "Could not connect with (" + address + ")");
						connectBtn.setSelected(true);
						connectBtn.doClick();
				}
			} else {
				new StatusUpdateMessage("Error", "Host field can not be empty!");
				new LogMessage("Error", "Could not connect with (" + address + ")");
				connectBtn.setSelected(true);
				connectBtn.doClick();
			}
		} else {
			try {
				SshConnection.closeSSH();
			} catch (final NullPointerException npE) {
				new LogMessage("Info", "Not connected, skipping close method");
			}
			SshLogin.changeTCPguiState("Disconnected");
			LoadJerry.addLogins("SSH");
		}
	}
}