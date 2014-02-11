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

/**
 * Create the MotorContorls panel for the Controller tab
 * @author Jackson Wilson (c) 2014
 */
public class MotorControls extends JPanel {
	private static final long serialVersionUID = 1L;

	public static JToggleButton forwardBtn;
	public static JToggleButton reverseBtn;
	public static JToggleButton rightBtn;
	public static JToggleButton leftBtn;
	public static JButton stopBtn;
	public static JTextField sBar;
	public static boolean otherBtnToggled = false;
	private static String currentBtnPressed;
	
	/**
	 * Initializes the controls for the Controller tab
	 */
	public MotorControls() {
		setBorder(BorderFactory.createTitledBorder("Motor Controls"));
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();
		
		forwardBtn = new JToggleButton("Forward");
		forwardBtn.setFocusable(false);
		forwardBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					buttonToggled("Forward");
				} else {
					otherBtnToggled = false;
					ConnectionTCP.sendMessage("STOP");
				}
			}
		});
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(2,0,0,2);
		gc.ipadx = 55;
		gc.ipady = 125;
		gc.gridwidth = 3;
		gc.gridx = 0;
		gc.gridy = 0;
		add(forwardBtn, gc);
		
		reverseBtn = new JToggleButton("Reverse");
		reverseBtn.setFocusable(false);
		reverseBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					buttonToggled("Reverse");
				} else {
					otherBtnToggled = false;
					ConnectionTCP.sendMessage("STOP");
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
					buttonToggled("Right");
				} else {
					otherBtnToggled = false;
					ConnectionTCP.sendMessage("STOP");
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
					buttonToggled("Left");
				} else {
					otherBtnToggled = false;
					ConnectionTCP.sendMessage("STOP");
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
				if (otherBtnToggled == true) {
					disableCurrentBtnToggled();
					otherBtnToggled = false;
				} else {
					ConnectionTCP.sendMessage("STOP");
				}
			}
		});
		gc.gridx = 1;
		gc.gridy = 1;
		add(stopBtn, gc);
		
		controlsEnabled(false);
	}
	
	/**
	 * Enables / disables the controls, defined by the enabled parameter
	 * @param enabled
	 */
	public static void controlsEnabled(final boolean enabled) {
		if (enabled == true) {
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
	
	private static String getCurrentBtnPressed() {
		return currentBtnPressed;
	}
	
	/**
	 * Sets the currently toggled button
	 * @param isOtherBtnToggled
	 * @param setCurrentBtnPressed
	 */
	public static void setCurrentBtnToggled(final boolean isOtherBtnToggled, final String setCurrentBtnPressed) {
		otherBtnToggled = isOtherBtnToggled;
		currentBtnPressed = setCurrentBtnPressed;
	}
	
	/**
	 * Called when a button is toggled to disable the currently toggled button (if their is one), sets the current button toggled, and sends
	 * the message 'STOP' to the TCP output stream
	 * @param button
	 */
	public static void buttonToggled(final String button) {
		if (button.equals("Disable")) {
			stopBtn.doClick();
			return;
		}
		if (otherBtnToggled == true) {
			disableCurrentBtnToggled();
			setCurrentBtnToggled(true, button);
		} else if (otherBtnToggled == false) {
			setCurrentBtnToggled(true, button);
		}
		ConnectionTCP.sendMessage(button.toUpperCase());
	}
	
	/**
	 * Changes the currently selected toggled button to a new one
	 */
	public static void disableCurrentBtnToggled() {
		switch (getCurrentBtnPressed()) {
			case "Forward":
				forwardBtn.doClick();
				break;
			case "Reverse":
				reverseBtn.doClick();
				break;
			case "Right":
				rightBtn.doClick();
				break;
			case "Left":
				leftBtn.doClick();
				break;
		}
		try {
			Thread.sleep(400);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}
}