package comm2;

public class MainComm {
	public static void main(final String[] args) {
		new TCPComm(6787, 6789, 5);
		TCPComm.startRunning();
	}
}