package tcpServer;

public class NewM {
	public static void main(final String[] args) {
		final NewS tcpServer = new NewS(6789, 5);
		tcpServer.startServer();
	}
}