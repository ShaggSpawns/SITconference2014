package tcpServer;

import javax.swing.JFrame;

public class RunClient {
	public static void main(String[] args) {
		TCPclient client = new TCPclient("127.0.0.1");
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.startRunning();
	}
}