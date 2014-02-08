package javaTCPserver;

/**
 * Runs the TCP server.
 * @author Jackson Wilson (c) 2014
 */
public class MainComm {
	public static void main(final String[] args) {
		new TCPComm(6787, 6789, 5);
	}
}