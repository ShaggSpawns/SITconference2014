package panel.TCP;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class MotorControls extends JPanel {
	private static final long serialVersionUID = 1L;

	private static JToggleButton forwardBtn;
	private static JToggleButton reverseBtn;
	private static JToggleButton rightBtn;
	private static JToggleButton leftBtn;
	private static JButton stopBtn;
	public static JTextField sBar;
	private boolean otherBtnToggled = false;
	private int currentBtn;
	
	public MotorControls() {
		setBorder(BorderFactory.createTitledBorder("Motor Controls"));
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();
		
		forwardBtn = new JToggleButton("Forward");
		forwardBtn.setFocusable(false);
		forwardBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					if (isOtherBtnToggled()) {
						changeBtnToggled();
						sendCommand("FORWARD");
						setOtherBtnToggled(true, 1);
					} else {
						setOtherBtnToggled(true, 1);
						sendCommand("FORWARD");
					}
				} else {
					setOtherBtnToggled(false, 1);
					stopBtn.doClick();
				}
			}
		});
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(2,0,0,2);
		gc.ipadx = 55;
		gc.ipady = 140;
		gc.gridwidth = 3;
		gc.gridx = 0;
		gc.gridy = 0;
		add(forwardBtn, gc);
		
		reverseBtn = new JToggleButton("Reverse");
		reverseBtn.setFocusable(false);
		reverseBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					if (isOtherBtnToggled()) {
						changeBtnToggled();
						sendCommand("REVERSE");
						setOtherBtnToggled(true, 2);
					} else {
						setOtherBtnToggled(true, 2);
						sendCommand("REVERSE");
					}
				} else {
					setOtherBtnToggled(false, 2);
					stopBtn.doClick();
				}
			}
		});
		gc.insets = new Insets(2,0,6,2);
		gc.gridx = 0;
		gc.gridy = 2;
		add(reverseBtn, gc);
		
		rightBtn = new JToggleButton("Curve Right");
		rightBtn.setFocusable(false);
		rightBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					if (isOtherBtnToggled()) {
						changeBtnToggled();
						sendCommand("RIGHT");
						setOtherBtnToggled(true, 3);
					} else {
						setOtherBtnToggled(true, 3);
						sendCommand("RIGHT");
					}
				} else {
					setOtherBtnToggled(false, 3);
					stopBtn.doClick();
				}
			}
		});
		gc.insets = new Insets(2,0,0,2);
		gc.gridwidth = 1;
		gc.gridx = 2;
		gc.gridy = 1;
		add(rightBtn, gc);
		
		leftBtn = new JToggleButton("Curve Left");
		leftBtn.setFocusable(false);
		leftBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					if (isOtherBtnToggled()) {
						changeBtnToggled();
						sendCommand("LEFT");
						setOtherBtnToggled(true, 4);
					} else {
						setOtherBtnToggled(true, 4);
						sendCommand("LEFT");
					}
				} else {
					setOtherBtnToggled(false, 4);
					stopBtn.doClick();
				}
			}
		});
		gc.gridx = 0;
		gc.gridy = 1;
		add(leftBtn, gc);
		
		stopBtn = new JButton("Stop");
		stopBtn.setFocusable(false);
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (isOtherBtnToggled()) {
					changeBtnToggled();
					setOtherBtnToggled(false, 5);
				} else {
					sendCommand("STOP");
				}
			}
		});
		gc.gridx = 1;
		gc.gridy = 1;
		add(stopBtn, gc);
		
		sBar = new JTextField(37);
		sBar.setFocusable(false);
		statusBarUpdate("Ready", 4);
		sBar.setEditable(false);
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 3;
		gc.ipady = 0;
		add(sBar, gc);
		
		controlsEnabled(false);
	}
	
	public static void controlsEnabled(final boolean e) {
		if (e == true) {
			forwardBtn.setEnabled(true);
			reverseBtn.setEnabled(true);
			rightBtn.setEnabled(true);
			leftBtn.setEnabled(true);
			stopBtn.setEnabled(true);
		} else {
			forwardBtn.setEnabled(false);
			reverseBtn.setEnabled(false);
			rightBtn.setEnabled(false);
			leftBtn.setEnabled(false);
			stopBtn.setEnabled(false);
		}
	}
	
	private void sendCommand(final String c) {
		ConnectionTCP.sendMessage(c);
		ConnectionTCP.TCPmessage(c, 2, true);
	}
	
	public static void statusBarUpdate(final String message, final int connectionState) {
		if (connectionState == 0) {
			sBar.setText("\n" + "[X] " + message);
		} else if (connectionState == 1) {
			sBar.setText("\n" + "[~] " + message);	
		} else if (connectionState == 2) {
			sBar.setText("\n" + "[O] " + message);
		} else if (connectionState == 3) {
			sBar.setText("\n" + "[#] " + message);
		} else {
			sBar.setText(message);
		}
	}
	
	private void changeBtnToggled() {
		if(currentBtn == 1) {
			forwardBtn.doClick();
		} else if (currentBtn == 2) {
			reverseBtn.doClick();
		} else if (currentBtn == 3) {
			rightBtn.doClick();
		} else if (currentBtn == 4) {
			leftBtn.doClick();
		} else {
			stopBtn.doClick();
		}
	}

	public boolean isOtherBtnToggled() {
		return otherBtnToggled;
	}

	public void setOtherBtnToggled(final boolean otherBtnToggled, final int currentBtn) {
		this.otherBtnToggled = otherBtnToggled;
		this.currentBtn = currentBtn;
	}
}