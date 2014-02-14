package controllerApplication;

public class IdentifyOS {
	private static String OS = System.getProperty("os.name").toLowerCase();
 
	public static String getOperatingSystem() {
		if (isWindows()) {
			return("Windows");
		} else if (isMac()) {
			return("Mac");
		} else if (isUnix()) {
			return("Unix");
		} else if (isSolaris()) {
			return("Solaris");
		} else {
			return("Unknown");
		}
	}
 
	private static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}
 
	private static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}
 
	private static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}

	private static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}
}