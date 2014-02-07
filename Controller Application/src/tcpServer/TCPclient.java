package tcpServer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class TCPclient extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private final JTextField userText;
	private final JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private final String serverIP;
	private Socket connection;
	
	public TCPclient(final String host) {
		super("Client Instant Messenger");
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				sendMessage(event.getActionCommand());
				userText.setText("");
			}
		});
		add(userText, BorderLayout.NORTH);
		
		chatWindow = new JTextArea();
		chatWindow.setEditable(false);
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		
		setSize(400,300);
		setVisible(true);
	}
	
	public void startRunning() {
		try {
			connectToServer();
			setupStreams();
			whileChatting();
		}catch(final EOFException eofException) {
			showMessage("\nClient terminated connection");
		}catch(final IOException ioException) {
			ioException.printStackTrace();
		}finally {
			closeCrap();
		}
	}
	
	private void connectToServer() throws IOException{
		showMessage("Attempting connection... \n");
		connection = new Socket(InetAddress.getByName(serverIP), 6789);
		showMessage("Connected to: " + connection.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\nStreams are created \n");
	}
	
	private void whileChatting() throws IOException {
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
				showMessage("\n" + message);
			}catch(final ClassNotFoundException classNotFoundException) {
				
			}
		}while(!message.equals("SERVER: END"));
	}
	
	private void closeCrap() {
		showMessage("\n Closing sockets...");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		}catch(final IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	private void sendMessage(final String message) {
		try {
			output.writeObject("CLIENT: " + message);
			output.flush();
			showMessage("\nCLIENT: " + message);
		}catch(final IOException ioException) {
			chatWindow.append("\nFailed to send!");
		}
	}
	
	private void showMessage(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatWindow.append(message);
			}
		});
	}
	
	private void ableToType(final boolean toggle) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				userText.setEditable(toggle);
			}
		});
	}
}