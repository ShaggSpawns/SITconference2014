package messageManager;

import panel.Jerry.JerryStatus;

/**
 * Manages the updating of the Jerry Status
 * @author Jackson Wilson (c) 2014
 */
public class StatusUpdateMessage {
	/**
	 * Updates the Status Bar with a message that uses a prefix defined by the messageType.
	 * @param messageType (Error, Warning, Important, Connected, Disconnected, Pending)
	 * @param message
	 */
	public StatusUpdateMessage(final String messageType, final String message) {
		statusBarUpdate(messageType, message);
	}
	
	/**
	 * Updates the Status Bar with a message that uses a prefix defined by the messageType.
	 * @param messageType (Error, Warning, Important, Connected, Disconnected, Pending)
	 * @param message
	 */
	private void statusBarUpdate(final String messageType, final String message) {
		String sBarMessage = null;
		
		if (messageType.equals("Error")) {
			sBarMessage = "[Error] " + message;
		} else if (messageType.equals("Warning")) {
			sBarMessage = "[Warning] " + message;
		} else if (messageType.equals("Important")) {
			sBarMessage = "[Important] " + message;
		} else if (messageType.equals("Connected")) {
			sBarMessage = "[Connected] " + message;
		} else if (messageType.equals("Disconnected")) {
			sBarMessage = "[Disconnected] " + message;
		} else if (messageType.equals("Pending")) {
			sBarMessage = "[Pending] " + message;
		} else {
			sBarMessage = "[" + messageType + "]" + message;
		}
		
		JerryStatus.jBar.setText("");
		JerryStatus.jBar.setText(sBarMessage);
		
		final String logType = "Status";
		new LogMessage(logType, sBarMessage);
	}
}