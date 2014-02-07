package panel.TCP;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

import controllerApplication.FileLogins;
import controllerApplication.LoadLogins;

public class LoginTCP extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final JLabel hostIP;
	private final JLabel port;
	private static JTextField hostField;
	private static JTextField portField;
	public static JToggleButton connectBtn;
	public static JButton saveBtn;
	public static JComboBox<String> loadComboBox;
	private String address;
	
	public LoginTCP() {
		setBorder(BorderFactory.createTitledBorder("TCP Login"));
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();

		hostIP = new JLabel("Host IP: ");
		hostIP.setToolTipText("Enter Decive IP Address");
		gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 0;
		gc.gridy = 0;
		add(hostIP, gc);
		
		port = new JLabel("Port: ");
		port.setToolTipText("Enter Device Port");
		gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 0;
		gc.gridy = 1;
		add(port, gc);
		
		hostField = new JTextField();
		gc.weightx = 0.0;
		//gc.ipadx = 150;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 1;
		gc.gridy = 0;
		add(hostField, gc);
		
		portField = new JTextField();
		portField.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				connectBtn.doClick();
			}
		});
		gc.gridx = 1;
		gc.gridy = 1;
		add(portField, gc);
		
		connectBtn = new JToggleButton("Connect");
		connectBtn.setToolTipText("Connect/Disconnect to Device");
		connectBtn.setFocusable(false);
		connectBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent ev) {
				final String ip = hostField.getText();
				final String inPort = portField.getText();
				address = ip + ":" + inPort;
				int port = 0;
				int intport;
				
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					connectBtn.setText("Disconnect");
					if (!(ip.equals(""))) {
						try {
							if (!(inPort.equals(""))) {
								intport = Integer.parseInt(inPort);
								port = intport;
								(new Thread(new ConnectionTCP(ip, port))).start();
							} else {
								System.out.println("Port field can not be empty!");
								System.out.println("Entered address ( " + address + " )\n");
								connectBtn.setSelected(true);
								connectBtn.doClick();
							}
						} catch (final NumberFormatException nFE) {
							displayMessage("Port is not an Integer!", 0);
							System.out.println("Entered address ( " + address + " )\n");
							connectBtn.setSelected(true);
							connectBtn.doClick();
						}
					} else {
						System.out.println("Host IP field can not be empty!");
						System.out.println("Entered address ( " + address + " )\n");
						connectBtn.setSelected(true);
						connectBtn.doClick();
					}
				} else {
					try {
						connectedGUIstate(false);
						ConnectionTCP.sendMessage("END");
						LoadLogins.addLogins(true);
					} catch (final NullPointerException nullPointerException) {
					}
				}
			}
		});
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridheight = 3;
		//gc.weightx = 1.0;
		gc.ipadx = 0;
		gc.gridx = 2;
		gc.gridy = 0;
		add(connectBtn, gc);
		
		saveBtn = new JButton("Save");
		saveBtn.setFocusable(false);
		saveBtn.setToolTipText("Save Current Host Address");
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
		loadComboBox = new JComboBox<>(hostSaves);
		LoadLogins.addLogins(true);
		loadComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				final int temp = loadComboBox.getSelectedIndex();
				
				if (temp == 0) {
					emptyFields();
				} else {
					fillFields(temp);
				}
			}
			
			private void fillFields(final int line) {
				try {
					final FileInputStream fs = new FileInputStream(FileLogins.loginTCP.getAbsoluteFile());
					@SuppressWarnings("resource")
					final
					BufferedReader br = new BufferedReader(new InputStreamReader(fs));
					
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
					System.out.println("ArrayIndexOutOfBoundsException 'fillFields'");
				} catch (final FileNotFoundException e) {
					System.out.println("FileNotFoundException 'fillFields'");
				} catch (final IOException e) {
					System.out.println("IOException 'fillFields'");
				} catch (final NullPointerException e) {
					System.out.println("NullPointerException 'fillFields'");
					e.printStackTrace();
				}
			}
			
			private void emptyFields() {
				hostField.setText("");
				portField.setText("");
			}
		});
		gc.fill = GridBagConstraints.HORIZONTAL;
		//gc.anchor = GridBagConstraints.NONE;
		gc.gridwidth = 1;
		gc.gridx = 1;
		gc.gridy = 2;
		add(loadComboBox, gc);
	}
	
	public static void connectedGUIstate(final boolean s) {
		if (s == true) {
			hostField.setEditable(false);
			hostField.setFocusable(false);
			portField.setEditable(false);
			portField.setFocusable(false);
			saveBtn.setEnabled(true);
			loadComboBox.setEnabled(false);
			connectBtn.setText("Disconnect");
		} else {
			hostField.setEditable(true);
			hostField.setFocusable(true);
			portField.setEditable(true);
			portField.setFocusable(true);
			saveBtn.setEnabled(false);
			loadComboBox.setEnabled(true);
			connectBtn.setText("Connect");
		}
	}
	
	private void displayMessage (final String message, final int connectionState) {
		MotorControls.statusBarUpdate(message, connectionState);
	}
}