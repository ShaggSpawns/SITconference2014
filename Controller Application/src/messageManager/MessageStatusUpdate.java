package messageManager;

import panel.Jerry.JerryStatus;

public class MessageStatusUpdate {
	/**
	 * Updates the Status Bar with a message that uses a prefix defined by the messageType.
	 * @param messageType (Error, Warning, Important, Connected, Disconnected, Pending)
	 * @param message
	 */
	public MessageStatusUpdate(final String messageType, final String message) {
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
			sBarMessage = message;
		}
		
		JerryStatus.sBar.setText("");
		JerryStatus.sBar.setText(sBarMessage);
		
		final String logType = "Status";
		new MessageLog(logType, sBarMessage);
	}
}