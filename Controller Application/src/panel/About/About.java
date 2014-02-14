package panel.About;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Initializes the About panel on the About tab
 * @author Jackson Wilson (c) 2014
 */
public class About extends JPanel {
	private static final long serialVersionUID = 1L;
		
	/**
	 * The constructor that initializes the About panel.
	 * Uses a GridBagLayout with a JTextArea to display the text.
	 */
	public About(final String OS) {
		setBorder(BorderFactory.createTitledBorder("About / Help"));
		setLayout(new GridBagLayout());
		final GridBagConstraints gc = new GridBagConstraints();
		
		final JTextArea about = new JTextArea(19, 38);
		about.setEditable(false);
		about.setAutoscrolls(isEnabled());
	    gc.anchor = GridBagConstraints.CENTER;
	    gc.insets = new Insets(2,1,2,2);
	    gc.ipadx = 8;
	    about.setText("** Hovering over componets (buttons, text, textfields, ect.) **" + "\n"
	    		+ "** gives more detailed information about it. **" + "\n"
	    		+ "\n"
	    		+ "~~~~~~~~~~~~~~~~~~~~~~~~ Controller ~~~~~~~~~~~~~~~~~~~~~~~" + "\n"
	    		+ "(About)" + "\n"
	    		+ "~ Used to connect to Jerry, once connected, used to control him." + "\n"
	    		+ "(Help)" + "\n"
	    		+ "~ Login with the Jerry's ip adress and port, then connect." + "\n"
	    		+ "~ Connecting will enable features, while also disabling others." + "\n"
	    		+ "~ Controls will sometimes auto-click \"Stop\" to prevent Jerry" + "\n"
	    		+ "   from crashing." + "\n"
	    		+ "~ At the bottem of the page, a status bar is located to give updates" + "\n"
	    		+ "   about the application." + "\n"
	    		+ "\n"
	    		+ "~~~~~~~~~~~~~~~~~~~~~~~~~~ SSH ~~~~~~~~~~~~~~~~~~~~~~~~~~" + "\n"
	    		+ "(About)" + "\n"
	    		+ "~ Used to SSH into Jerry to gain full controll over him." + "\n"
	    		+ "(Help)" + "\n"
	    		+ "~ Login with the Jerry's ip, port, username, and password, then" + "\n"
	    		+ "   connect." + "\n"
	    		+ "~ Connecting will enable features, while disabling others." + "\n"
	    		+ "~ SSH commands are typed into the field at the bottem of the program." + "\n"
	    		+ "\n"
	    		+ "~~~~~~~~~~~~~~~~~~~~~~~~ About / Info ~~~~~~~~~~~~~~~~~~~~~~" + "\n"
	    		+ "(About)" + "\n"
	    		+ "~ Used to display credits, help, and a logging console of information." + "\n"
	    		+ "(Help)" + "\n"
	    		+ "~ Shouldn't really need help to understand.");
	    add(new JScrollPane(about), gc);
	}
}