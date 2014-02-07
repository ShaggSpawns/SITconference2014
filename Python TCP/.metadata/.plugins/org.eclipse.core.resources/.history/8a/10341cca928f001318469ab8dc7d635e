package ssh;

import javax.swing.JOptionPane;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class SSH {

     public static void main(final String[] arg) {
    	 
    	 try {
    		 final JSch jsch = new JSch();
    		 
    		 String host = null;
    		 
    		 if (arg.length > 0) { 
    			 host = arg[0]; 
    		 } else {
    			 host = JOptionPane.showInputDialog("Enter username@hostname", "jacksonwilson"+"@10.0.0.3"); 
    		 }
    		 
    		 
    		 final String user = host.substring(0, host.indexOf('@'));
    		 
    		 host = host.substring(host.indexOf('@') + 1);
    		 
    		 final Session session = jsch.getSession(user, host, 22); 
    		 
    		 final String password = JOptionPane.showInputDialog("Enter password"); 
    		 
    		 session.setPassword(password); 
    		 
    		 final UserInfo ui = new MyUserInfo() {
    			 
    			 public void showMessage(final String message) { 
    				 JOptionPane.showMessageDialog(null, message);
    			 }
    			 
    			 public boolean promptYesNo(final String message) {
    				 final Object[] options = { "yes", "no" }; 
    				 
    				 final int foo = JOptionPane.showOptionDialog(null, message, "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
    				 
    				 return foo == 0;
    			 }
    		 };
    		 
    		 session.setUserInfo(ui); 
    		 
    		 session.connect(30000); 
    		 
    		 final Channel channel = session.openChannel("shell");
    		 channel.setInputStream(System.in);
    		 channel.setOutputStream(System.out); 
    		 channel.connect(3*1000);
    		 
    	 } catch (final Exception e) {
    		 System.out.println(e);
    	 }
     }
     
     public static abstract class MyUserInfo implements UserInfo, UIKeyboardInteractive {
    	 
    	 public String getPassword() {
    		 return null;
    	 }
    	 public boolean promptYesNo(final String str) {
    		 return false;
    	 }
    	 public String getPassphrase() {
    		 return null;
    	 }
    	 public boolean promptPassphrase(final String message) {
    		 return false;
    	 }
    	 public boolean promptPassword(final String message) {
    		 return false;
    	 }
    	 public void showMessage(final String message) {
				
    	 }
    	 public String[] promptKeyboardInteractive(final String destination, final String name, final String instruction, final String[] prompt, final boolean[] echo) {
    		 return null;
    	 }
     }
}