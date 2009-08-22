package net.snarl;

/**
 * A Snarl Notification, Containing Reply, Action and Notification ID
 * 
 * @author Patrick von Reth
 * 
 * 
 */

public class Notification extends Message {
	private int id = -1;
	private Action action = null;

	/**
	 * Creates a new Default Notification with SnarlNetworkBridge default
	 * timeout
	 * 
	 * @param alert
	 *            the name of the alert class
	 * @param title
	 *            the title of the Notification
	 * @param content
	 *            the content of the Notification
	 */
	public Notification(String alert, String title, String content) {
		super(SnarlNetworkBridge.head + "#?action=notification#?app="
				+ SnarlNetworkBridge.appName + "#?class="
				+ SnarlNetworkBridge.alerts.get(alert) + "#?title=" + title
				+ "#?text=" + content + "#?timeout="
				+ SnarlNetworkBridge.snGetTimeout());
	}

	/**
	 * Creates a new Default Notification
	 * 
	 * @param alert
	 *            the name of the alert class
	 * @param title
	 *            the title of the Notification
	 * @param content
	 *            the content of the Notification
	 * @param timeout
	 *            the timeout of the Notification
	 */
	public Notification(String alert, String title, String content, int timeout) {
		super(SnarlNetworkBridge.head + "#?action=notification#?app="
				+ SnarlNetworkBridge.appName + "#?class="
				+ SnarlNetworkBridge.alerts.get(alert) + "#?title=" + title
				+ "#?text=" + content + "#?timeout=" + timeout);
	}

	/**
	 * Sets the action which the user applied, should be overwritten to set user
	 * specific actions
	 * 
	 * @param action
	 *            the Action to be set
	 */
	protected void setAction(Action action) {
		this.action = action;
		if (action == Action.Closed || action == Action.LeftClicked
				|| action == Action.Timed_Out)
			SnarlNetworkBridge.removeNotification(this);
	}

	/**
	 * Get the Action which the user applied, or null if nothing set
	 * 
	 * @return the Action of the Notification
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * Sets the id of the Notification called by SnarlNetworkBridge
	 * 
	 * @param id
	 *            the id to be set
	 */
	void setId(String id) {
		this.id = Integer.valueOf(id);
	}

	/**
	 * Returns the Snarl Notification Id
	 * 
	 * @return id of the Notification
	 */
	public int getId() {
		return id;
	}

}
