package panel.Jerry;

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
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import jerryController.LoadJerry;
import jerryController.SaveJerry;
import messageManager.LogMessage;
import messageManager.StatusUpdateMessage;

/**
 * Creates the TCP Login area on the MotorControls tab
 * @author Jackson Wilson (c) 2014
 */
public class JerryLogin extends JPanel implements ItemListener, ActionListener {
	private static final long serialVersionUID = 1L;
	
	private final JLabel hostL;
	private final JLabel portL;
	public static JTextField hostF;
	public static JTextField portF;
	public static JToggleButton connectBtn;
	public static JButton saveBtn;
	public static JComboBox<String> loadComboBox;
	private String address;
	
	/**
	 * Creates the TCP Login area on the MotorControls tab
	 */
	public JerryLogin(final String OS) {
		setBorder(BorderFactory.createTitledBorder("Jerry Login"));
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();

		hostL = new JLabel();
		hostL.setToolTipText("Enter Jerry's IP Address");
		switch(OS) {
		case "Windows":
			hostL.setText("Host IP : ");
			break;
		case "Mac":
			hostL.setText("Host IP: ");
			break;
		case "Default":
			hostL.setText("Host IP: ");
			break;
		}
		gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 0;
		gc.gridy = 0;
		add(hostL, gc);
		
		portL = new JLabel("Port: ");
		portL.setToolTipText("Enter Jerry's Port Number");
		switch(OS) {
		case "Windows":
			portL.setText("Port : ");
			break;
		case "Mac":
			portL.setText("Port: ");
			break;
		case "Default":
			portL.setText("Port: ");
			break;
		}
		gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 0;
		gc.gridy = 1;
		add(portL, gc);
		
		hostF = new JTextField();
		hostF.setToolTipText("Enter Jerry's IP Address");
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = 0.0;
		gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 1;
		gc.gridy = 0;
		add(hostF, gc);
		
		portF = new JTextField();
		portF.setToolTipText("Enter Jerry's Port Number");
		portF.addActionListener(this);
		gc.gridx = 1;
		gc.gridy = 1;
		add(portF, gc);
		
		connectBtn = new JToggleButton("Connect");
		connectBtn.setToolTipText("Connect / Disconnect to Vehical");
		connectBtn.setFocusable(false);
		connectBtn.addItemListener(this);
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		switch(OS) {
		case "Windows":
			gc.insets = new Insets(0,2,0,2);
			break;
		case "Mac":
			gc.insets = new Insets(0,0,0,0);
			break;
		case "Default":
			gc.insets = new Insets(0,0,0,0);
			break;
		}
		gc.gridheight = 3;
		gc.ipadx = 0;
		gc.gridx = 2;
		gc.gridy = 0;
		add(connectBtn, gc);
		
		saveBtn = new JButton("Save");
		saveBtn.setFocusable(false);
		saveBtn.setToolTipText("Save Current Vehical Address");
		saveBtn.setEnabled(false);
		saveBtn.addActionListener(this);
		switch(OS) {
		case "Windows":
			gc.insets = new Insets(0,3,0,3);
			break;
		case "Mac":
			gc.insets = new Insets(0,0,0,0);
			break;
		case "Default":
			gc.insets = new Insets(0,0,0,0);
			break;
		}
		gc.gridheight = 1;
		gc.gridx = 0;
		gc.gridy = 2;
		add(saveBtn, gc);
		
		loadComboBox = new JComboBox<String>();
		LoadJerry.addLogins("JERRY");
		loadComboBox.setToolTipText("Load Saved Vehical Addresses");
		loadComboBox.addActionListener(this);
		switch(OS) {
		case "Windows":
			gc.insets = new Insets(0,0,0,1);
			break;
		case "Mac":
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.insets = new Insets(0,0,0,0);
			break;
		case "Default":
			break;
		}
		gc.gridwidth = 1;
		gc.gridx = 1;
		gc.gridy = 2;
		add(loadComboBox, gc);
	}
	
	/**
	 * Auto-fills the login fields with saved entries
	 * @param line
	 */
	private void fillLoginFields(final int line) {
		try {
			final FileInputStream fs = new FileInputStream(SaveJerry.loginJERRY.getAbsoluteFile());
			@SuppressWarnings("resource")
			final BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			
			for (int i = 1; i < line; ++i) {
				br.readLine();
			}
			
			final String entry = br.readLine();
			final String[] ar = entry.split(":");
			
			final String host = ar[0];
			final String port = ar[1];
			
			hostF.setText(host);
			portF.setText(port);
		} catch (final ArrayIndexOutOfBoundsException e) {
			new StatusUpdateMessage("Error", "Failed to fill fields");
			new LogMessage("Error", "ArrayIndexOutOfBoundsException in JerryLogin.fillFields()");
		} catch (final FileNotFoundException e) {
			new StatusUpdateMessage("Error", "Failed to fill fields");
			new LogMessage("Error", "FileNotFoundException in JerryLogin.fillFields()");
		} catch (final IOException e) {
			new StatusUpdateMessage("Error", "Failed to fill fields");
			new LogMessage("Error", "IOException in JerryLogin.fillFields()");
		} catch (final NullPointerException e) {
			new StatusUpdateMessage("Error", "Failed to fill fields");
			new LogMessage("Error", "NullPointerException in JerryLogin.fillFields()");
			e.printStackTrace();
		}
	}
	
	/**
	 * Empties the TCP login fields
	 */
	private void emptyFields() {
		hostF.setText("");
		portF.setText("");
	}
	
	/**
	 * Changes the availability of the different components on the Motor Control tab, defined by the state parameter
	 * @param state
	 */
	public static void changeTCPguiState(final String state) {
		switch(state) {
		case "Connected":
			hostF.setEditable(false);
			hostF.setFocusable(false);
			portF.setEditable(false);
			portF.setFocusable(false);
			saveBtn.setEnabled(true);
			loadComboBox.setEnabled(false);
			JerryControls.forwardBtn.setEnabled(true);
			JerryControls.forwardBtn.setFocusable(true);
			JerryControls.reverseBtn.setEnabled(true);
			JerryControls.reverseBtn.setFocusable(true);
			JerryControls.rightBtn.setEnabled(true);
			JerryControls.rightBtn.setFocusable(true);
			JerryControls.leftBtn.setEnabled(true);
			JerryControls.leftBtn.setFocusable(true);
			JerryControls.stopBtn.setEnabled(true);
			JerryControls.stopBtn.setFocusable(true);
			connectBtn.setText("Disconnect");
			break;
		case "Pending":
			hostF.setEditable(false);
			hostF.setFocusable(false);
			portF.setEditable(false);
			portF.setFocusable(false);
			saveBtn.setEnabled(false);
			loadComboBox.setEnabled(false);
			JerryControls.forwardBtn.setEnabled(false);
			JerryControls.forwardBtn.setFocusable(false);
			JerryControls.reverseBtn.setEnabled(false);
			JerryControls.reverseBtn.setFocusable(false);
			JerryControls.rightBtn.setEnabled(false);
			JerryControls.rightBtn.setFocusable(false);
			JerryControls.leftBtn.setEnabled(false);
			JerryControls.leftBtn.setFocusable(false);
			JerryControls.stopBtn.setEnabled(false);
			JerryControls.stopBtn.setFocusable(false);
			connectBtn.setText("Connecting");
			break;
		case "Disconnected":
			hostF.setEditable(true);
			hostF.setFocusable(true);
			portF.setEditable(true);
			portF.setFocusable(true);
			saveBtn.setEnabled(false);
			loadComboBox.setEnabled(true);
			JerryControls.forwardBtn.setEnabled(false);
			JerryControls.forwardBtn.setFocusable(false);
			JerryControls.reverseBtn.setEnabled(false);
			JerryControls.reverseBtn.setFocusable(false);
			JerryControls.rightBtn.setEnabled(false);
			JerryControls.rightBtn.setFocusable(false);
			JerryControls.leftBtn.setEnabled(false);
			JerryControls.leftBtn.setFocusable(false);
			JerryControls.stopBtn.setEnabled(false);
			JerryControls.stopBtn.setFocusable(false);
			connectBtn.setText("Connect");
			break;
		}
	}
	
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == loadComboBox) {
			final int selectedEntry = loadComboBox.getSelectedIndex();
			if (selectedEntry == 0) {
				emptyFields();
			} else {
				fillLoginFields(selectedEntry);
			}
		} else if (e.getSource() == portF) {
			connectBtn.doClick();
		} else if (e.getSource() == saveBtn) {
			saveBtn.setEnabled(false);
			new SaveJerry(address, "JERRY");
		}
	}
	
	public void itemStateChanged(final ItemEvent ev) {
		final String ip = hostF.getText();
		final String inPort = portF.getText();
		address = ip + ":" + inPort;
		int port = 0;
		int intport;
		if (ev.getStateChange() == ItemEvent.SELECTED) {
			if (!ip.equals("")) {
				try {
					if (!inPort.equals("")) {
						intport = Integer.parseInt(inPort);
						port = intport;
						changeTCPguiState("Pending");
						(new Thread(new JerryConnection(ip, port))).start();
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
		} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
			try {
				if (JerryConnection.connection.isConnected()) {
					JerryControls.stopBtn.doClick();
					JerryConnection.sendMessage("END");
				}
			} catch (final NullPointerException npE) {
				new LogMessage("Info", "Not connected to Jerry, skipping close method");
			}
			changeTCPguiState("Disconnected");
			LoadJerry.addLogins("TCP");
		}
	}
}