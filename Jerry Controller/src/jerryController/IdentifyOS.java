package jerryController;

public class IdentifyOS {
	private static String OS = System.getProperty("os.name").toLowerCase();
 
	public static String getOperatingSystem() {
		if (isWindows()) {
			return("Windows");
		} else if (isMac()) {
			return("Mac");
		} else {
			return("Default");
		}
	}
 
	private static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}
 
	private static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}
}