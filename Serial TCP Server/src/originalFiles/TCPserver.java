package originalFiles;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class TCPserver extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private final JTextField userText;
	private final JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	public TCPserver() {
		super("Server Instant Messenger");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
			new ActionListener() {
				public void actionPerformed(final ActionEvent event) {
					sendMessage(event.getActionCommand());
					userText.setText("");
				}
			});
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		chatWindow.setEditable(false);
		add(new JScrollPane(chatWindow));
		setSize(400, 300);
		setVisible(true);
	}
	
	public void startRunning() {
		try {
			server = new ServerSocket(6789, 5);
			while(true) {
				try {
					waitForConnection();
					setupStreams();
					whileChatting();
				}catch(final EOFException eofException) {
					showMessage("Server ended the connection!");
				}finally {
					closeCrap();
					chatWindow.setText("");
				}
			}
		}catch(final IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	private void waitForConnection() throws IOException {
		showMessage("Waiting for someone to connect...");
		connection = server.accept();
		showMessage("Now connected to " + connection.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("Streams are now setup!");
	}
	
	private void whileChatting() throws IOException {
		String message = null;
		//sendMessage(message);
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
				showMessage(message);
			}catch(final ClassNotFoundException classNotFoundException) {
				showMessage("idk wtf that user sent! ");
			}
		}while(!message.equals("END"));
	}
	
	private void closeCrap() {
		showMessage("Closing connection...");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		}catch(final IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	private void sendMessage(final String message) {
		try {
			output.writeObject("SERVER: " + message);
			output.flush();
			showMessage("SERVER: " + message);
		}catch(final IOException ioException) {
			chatWindow.append("CAN'T SEND");
			startRunning();
		}
	}
	
	private void showMessage(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatWindow.append("\n" + text);
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
	
	public static void main(final String[] args) {
		final TCPserver server = new TCPserver();
		server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		server.startRunning();
	}
}