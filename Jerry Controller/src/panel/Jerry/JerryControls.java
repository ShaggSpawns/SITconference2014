package panel.Jerry;

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
public class JerryControls extends JPanel {
	private static final long serialVersionUID = 1L;

	public static JToggleButton forwardBtn;
	public static JToggleButton reverseBtn;
	public static JToggleButton rightBtn;
	public static JToggleButton leftBtn;
	public static JButton stopBtn;
	public JTextField sBar;
	public boolean otherBtnToggled = false;
	private String currentBtnPressed;
	
	/**
	 * Initializes the controls for the Controller tab
	 */
	public JerryControls(final String OS) {
		setBorder(BorderFactory.createTitledBorder("Jerry Controls"));
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();
		
		forwardBtn = new JToggleButton("Jerry Forward");
		forwardBtn.setFocusable(false);
		forwardBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					buttonToggled("Forward");
				} else {
					JerryConnection.sendMessage("STOP");
				}
			}
		});
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(2,0,0,2);
		switch(OS) {
		case "Windows":
			gc.ipady = 95;
			break;
		case "Mac":
			gc.ipady = 132;
			break;
		case "Default":
			gc.ipady = 125;
			break;
		}
		gc.ipadx = 55;
		gc.gridwidth = 3;
		gc.gridx = 0;
		gc.gridy = 0;
		add(forwardBtn, gc);
		
		reverseBtn = new JToggleButton("Jerry Reverse");
		reverseBtn.setFocusable(false);
		reverseBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					buttonToggled("Reverse");
				} else {
					JerryConnection.sendMessage("STOP");
				}
			}
		});
		gc.insets = new Insets(2,0,6,2);
		gc.gridx = 0;
		gc.gridy = 2;
		add(reverseBtn, gc);
		
		rightBtn = new JToggleButton("Jerry Right");
		rightBtn.setFocusable(false);
		rightBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					buttonToggled("Right");
				} else {
					JerryConnection.sendMessage("STOP");
				}
			}
		});
		gc.insets = new Insets(2,0,0,2);
		gc.gridwidth = 1;
		gc.gridx = 2;
		gc.gridy = 1;
		add(rightBtn, gc);
		
		leftBtn = new JToggleButton("Jerry Left");
		leftBtn.setFocusable(false);
		leftBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					buttonToggled("Left");
				} else {
					JerryConnection.sendMessage("STOP");
				}
			}
		});
		gc.gridx = 0;
		gc.gridy = 1;
		add(leftBtn, gc);
		
		stopBtn = new JButton("Jerry Stop");
		stopBtn.setFocusable(false);
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (otherBtnToggled == true) {
					disableCurrentBtnToggled();
				} else {
					JerryConnection.sendMessage("STOP");
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
	
	/**
	 * Gets the current button pressed
	 * @return currentBtnPressed
	 */
	private String getCurrentBtnPressed() {
		return currentBtnPressed;
	}
	
	/**
	 * Sets the currently toggled button
	 * @param isOtherBtnToggled
	 * @param setCurrentBtnPressed
	 */
	public void setCurrentBtnToggled(final boolean isOtherBtnToggled, final String setCurrentBtnPressed) {
		otherBtnToggled = isOtherBtnToggled;
		currentBtnPressed = setCurrentBtnPressed;
	}
	
	/**
	 * Called when a button is toggled to disable the currently toggled button (if their is one), sets the current button toggled, and sends
	 * the message 'STOP' to the TCP output stream
	 * @param button
	 */
	public void buttonToggled(final String button) {
		if (otherBtnToggled == true) {
			disableCurrentBtnToggled();
			try {
				Thread.sleep(400);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			setCurrentBtnToggled(true, button);
		} else if (otherBtnToggled == false) {
			setCurrentBtnToggled(true, button);
		}
		JerryConnection.sendMessage(button.toUpperCase());
	}
	
	/**
	 * Changes the currently selected toggled button to a new one
	 */
	public void disableCurrentBtnToggled() {
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
		otherBtnToggled = false;
	}
}