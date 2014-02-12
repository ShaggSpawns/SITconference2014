package panel.TCP;

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

import messageManager.MessageLog;
import messageManager.MessageStatusUpdate;
import controllerApplication.FileLogins;
import controllerApplication.LoadLogins;

/**
 * Creates the TCP Login area on the MotorControls tab
 * @author Jackson Wilson (c) 2014
 */
public class LoginTCP extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final JLabel hostIP;
	private final JLabel port;
	public static JTextField hostField;
	public static JTextField portField;
	public static JToggleButton connectBtn;
	public static JButton saveBtn;
	public static JComboBox<String> loadComboBox;
	private String address;
	
	/**
	 * Creates the TCP Login area on the MotorControls tab
	 */
	public LoginTCP() {
		setBorder(BorderFactory.createTitledBorder("TCP Login"));
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();

		hostIP = new JLabel("Host IP: ");
		hostIP.setToolTipText("Enter Vehical IP Address");
		gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 0;
		gc.gridy = 0;
		add(hostIP, gc);
		
		port = new JLabel("Port: ");
		port.setToolTipText("Enter Vehical Port Number");
		gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 0;
		gc.gridy = 1;
		add(port, gc);
		
		hostField = new JTextField();
		hostField.setToolTipText("Enter Vehical IP Address");
		gc.weightx = 0.0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 1;
		gc.gridy = 0;
		add(hostField, gc);
		
		portField = new JTextField();
		portField.setToolTipText("Enter Vehical Port Number");
		portField.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				connectBtn.doClick();
			}
		});
		gc.gridx = 1;
		gc.gridy = 1;
		add(portField, gc);
		
		connectBtn = new JToggleButton("Connect");
		connectBtn.setToolTipText("Connect / Disconnect to Vehical");
		connectBtn.setFocusable(false);
		connectBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent ev) {
				final String ip = hostField.getText();
				final String inPort = portField.getText();
				address = ip + ":" + inPort;
				int port = 0;
				int intport;
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					changeTCPguiState("Pending");
					if (!(ip.equals(""))) {
						try {
							if (!(inPort.equals(""))) {
								intport = Integer.parseInt(inPort);
								port = intport;
								(new Thread(new ConnectionTCP(ip, port))).start();
							} else {
								new MessageLog("Error", "Port field can not be empty!");
								new MessageLog("Info", "Entered address ( " + address + " )");
								new MessageStatusUpdate("Error", "Port field cannot be empty!");
								connectBtn.setSelected(true);
								connectBtn.doClick();
							}
						} catch (final NumberFormatException nFE) {
							new MessageLog("Error", "Port is not an Integer!");
							new MessageLog("Info", "Entered address ( " + address + " )");
							new MessageStatusUpdate("Error", "Port is not an Integer!");
							connectBtn.setSelected(true);
							connectBtn.doClick();
						}
					} else {
						new MessageLog("Error", "Host IP field can not be empty!");
						new MessageLog("Info", "Entered address ( " + address + " )");
						new MessageStatusUpdate("Error", "Host IP field cannot be empty!");
						connectBtn.setSelected(true);
						connectBtn.doClick();
					}
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					if (ConnectionTCP.connection.isConnected()) {
						MotorControls.stopBtn.doClick();
						ConnectionTCP.sendMessage("END");
					}
					changeTCPguiState("Disconnected");
					LoadLogins.addLogins("TCP");
				}
			}
		});
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridheight = 3;
		gc.ipadx = 0;
		gc.gridx = 2;
		gc.gridy = 0;
		add(connectBtn, gc);
		
		saveBtn = new JButton("Save");
		saveBtn.setFocusable(false);
		saveBtn.setToolTipText("Save Current Vehical Address");
		saveBtn.setEnabled(false);
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				saveBtn.setEnabled(false);
				new FileLogins(address, true);
			}
		});
		gc.gridheight = 1;
		gc.gridx = 0;
		gc.gridy = 2;
		add(saveBtn, gc);
		
		final String[] hostSaves = {"----------- Load Save ----------"};
		loadComboBox = new JComboBox<String>(hostSaves);
		LoadLogins.addLogins("TCP");
		loadComboBox.setToolTipText("Load Saved Vehical Addresses");
		loadComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				int selectedEntry = loadComboBox.getSelectedIndex();
				if (selectedEntry == 0) {
					emptyFields();
				} else {
					fillLoginFields(selectedEntry);
				}
			}
		});
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(0, 0, 0, 0);
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
			final FileInputStream fs = new FileInputStream(FileLogins.loginTCP.getAbsoluteFile());
			@SuppressWarnings("resource")
			final BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			
			for (int i = 1; i < line; ++i) {
				br.readLine();
			}
			
			final String entry = br.readLine();
			final String[] ar = entry.split(":");
			
			final String host = ar[0];
			final String port = ar[1];
			
			hostField.setText(host);
			portField.setText(port);
		} catch (final ArrayIndexOutOfBoundsException e) {
			new MessageLog("ERROR", "ArrayIndexOutOfBoundsException panel.TCP.LoginTCP.fillFields()");
		} catch (final FileNotFoundException e) {
			new MessageLog("ERROR", "FileNotFoundException panel.TCP.LoginTCP.fillFields()");
		} catch (final IOException e) {
			new MessageLog("ERROR", "IOException panel.TCP.LoginTCP.fillFields()");
		} catch (final NullPointerException e) {
			new MessageLog("ERROR", "NullPointerException panel.TCP.LoginTCP.fillFields()");
			e.printStackTrace();
		}
	}
	
	/**
	 * Empties the TCP login fields
	 */
	private void emptyFields() {
		hostField.setText("");
		portField.setText("");
	}
	
	/**
	 * Changes the availability of the different components on the Motor Control tab, defined by the state parameter
	 * @param state
	 */
	public static void changeTCPguiState(final String state) {
		switch(state) {
		case "Connected":
			hostField.setEditable(false);
			hostField.setFocusable(false);
			portField.setEditable(false);
			portField.setFocusable(false);
			saveBtn.setEnabled(true);
			loadComboBox.setEnabled(false);
			MotorControls.forwardBtn.setEnabled(true);
			MotorControls.forwardBtn.setFocusable(true);
			MotorControls.reverseBtn.setEnabled(true);
			MotorControls.reverseBtn.setFocusable(true);
			MotorControls.rightBtn.setEnabled(true);
			MotorControls.rightBtn.setFocusable(true);
			MotorControls.leftBtn.setEnabled(true);
			MotorControls.leftBtn.setFocusable(true);
			MotorControls.stopBtn.setEnabled(true);
			MotorControls.stopBtn.setFocusable(true);
			connectBtn.setText("Disconnect");
			break;
		case "Pending":
			hostField.setEditable(false);
			hostField.setFocusable(false);
			portField.setEditable(false);
			portField.setFocusable(false);
			saveBtn.setEnabled(false);
			loadComboBox.setEnabled(false);
			MotorControls.forwardBtn.setEnabled(false);
			MotorControls.forwardBtn.setFocusable(false);
			MotorControls.reverseBtn.setEnabled(false);
			MotorControls.reverseBtn.setFocusable(false);
			MotorControls.rightBtn.setEnabled(false);
			MotorControls.rightBtn.setFocusable(false);
			MotorControls.leftBtn.setEnabled(false);
			MotorControls.leftBtn.setFocusable(false);
			MotorControls.stopBtn.setEnabled(false);
			MotorControls.stopBtn.setFocusable(false);
			connectBtn.setText("Connecting");
			break;
		case "Disconnected":
			hostField.setEditable(true);
			hostField.setFocusable(true);
			portField.setEditable(true);
			portField.setFocusable(true);
			saveBtn.setEnabled(false);
			loadComboBox.setEnabled(true);
			MotorControls.forwardBtn.setEnabled(false);
			MotorControls.forwardBtn.setFocusable(false);
			MotorControls.reverseBtn.setEnabled(false);
			MotorControls.reverseBtn.setFocusable(false);
			MotorControls.rightBtn.setEnabled(false);
			MotorControls.rightBtn.setFocusable(false);
			MotorControls.leftBtn.setEnabled(false);
			MotorControls.leftBtn.setFocusable(false);
			MotorControls.stopBtn.setEnabled(false);
			MotorControls.stopBtn.setFocusable(false);
			connectBtn.setText("Connect");
			break;
		}
	}}