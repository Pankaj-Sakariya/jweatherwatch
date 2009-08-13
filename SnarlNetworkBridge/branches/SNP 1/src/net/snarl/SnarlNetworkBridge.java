package net.snarl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * SnarlNetworkBridge for SNP v1.0
 * 
 * @author Patrick von Reth
 * @version 1.0
 */
public class SnarlNetworkBridge {
	private int timeout = 10;
	private double SNPVersion = 1.0;
	private String head = "type=SNP#?version=" + SNPVersion;
	private Socket sock = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String appName = null;
	private HashMap<String, Integer> alerts = new HashMap<String, Integer>();
	private boolean snarlIsRunning = true;

	/**
	 * Creates a new SnarlNetworkBridge to the localhost
	 * 
	 * @param applicationName
	 *            The Application you want to register with Snarl
	 */
	public SnarlNetworkBridge(String applicationName) {
		create(applicationName, "localhost");
	}

	/**
	 * Creates a new SnarlNetworkBridge to a remote host
	 * 
	 * @param applicationName
	 *            The Name of the Application you want to register with Snarl
	 * @param host
	 *            The Name/IP of the host to connect to
	 */
	public SnarlNetworkBridge(String applicationName, String host) {
		create(applicationName, host);
	}

	/**
	 * Has to be called after the Constructor
	 * 
	 * @return The Answer of the SnarlClient
	 */
	public String snRegisterConfig() {
		return send(head + "#?action=register#?app=" + appName);
	}

	/**
	 * Register a new Alert Class to Snarl
	 * 
	 * @param title
	 *            The title representing the Alert
	 * @return The Answer of the SnarlClient
	 */
	public String snRegisterAlert(String title) {
		alerts.put(title, alerts.size() + 1);
		return send(head + "#?action=add_class#?app=" + appName + "#?class="
				+ alerts.size() + "#?title=" + title);
	}

	/**
	 * Displays a Snarl Notification with the default timeout
	 * 
	 * @param alert
	 *            The name of an registered Alert
	 * @param title
	 *            The Title of the Notification
	 * @param content
	 *            The Content of the Notification
	 * @return The Answer of the SnarlClient
	 */
	public String snShowMessage(String alert, String title, String content) {
		return snShowMessage(alert, title, content, timeout);
	}

	/**
	 * Displays a Snarl Notification with a specific timeout
	 * 
	 * @param alert
	 *            The name of an registered Alert
	 * @param title
	 *            The Title of the Notification
	 * @param content
	 *            The Content of the Notification
	 * @param timeout
	 *            An integer value representing the timeout
	 * @return The Answer of the SnarlClient
	 */
	public String snShowMessage(String alert, String title, String content,
			int timeout) {
		if (snarlIsRunning)
			return send(head + "#?action=notification#?app=" + appName
					+ "#?class=" + alerts.get(alert) + "#?title=" + title
					+ "#?text=" + content + "#?timeout=" + timeout);
		return "Snarl not Running";
	}

	/**
	 * Sets the default timeout
	 * 
	 * @param timeout
	 */
	public void snSetTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * Unregister your Snarl Application with all alerts
	 * 
	 * @return The Answer of the SnarlClient
	 */
	public String snRevokeConfig() {
		String out = send(head + "#?action=unregister#?app=" + appName);
		close();
		return out;
	}

	/**
	 * Sets a different host to a registered SnarlApplication, currently
	 * registered Alerts will be registered to the new host
	 * 
	 * @param host
	 *            The Name/IP of the host to connect to
	 */
	public void setHost(String host) {
		Object oldAlerts[] = alerts.keySet().toArray();
		alerts.clear();
		create(appName, host);
		for (Object o : oldAlerts) {
			snRegisterAlert((String) o);
		}

	}

	/**
	 * 
	 * @return The ip of the host
	 */
	public String getHost() {
		System.out.println("HostName: " + sock.getInetAddress().toString());
		return sock.getInetAddress().getHostAddress();
	}

	/**
	 * Returns a boolean value representing the Snarl running Status
	 * 
	 * @return true if Snarl is running and listening to network Connections
	 *         otherwise false
	 */
	public boolean isRunnging() {
		return snarlIsRunning;
	}

	/**
	 * Called by the Constructor and setHost
	 * 
	 * @param appName
	 *            The Name of the Application you want to register with Snarl
	 * @param host
	 *            The Name/IP of the host to connect to
	 */
	private void create(String appName, String host) {
		this.appName = appName;
		try {
			sock = new Socket(InetAddress.getByName(host), 9887);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));

		} catch (ConnectException e) {
			snarlIsRunning = false;
			System.out.println("Snarl is not running");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Called by every send Method to receive the Snarl Answer
	 * 
	 * @return The Answer of the SnarlClient or "Snarl is not running"
	 */
	private String listen() {
		if (!snarlIsRunning) {
			return "Snarl is not running";
		}
		try {
			String line = in.readLine();
			System.out.println(line);
			return line;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Error");
		return "Error";

	}

	/**
	 * Called by the different sn Actions
	 * 
	 * @param s
	 *            the String to send
	 * @return The Answer of the SnarlClient or "Snarl is not running"
	 */
	private String send(String s) {
		if (!snarlIsRunning) {
			return "Snarl is not running";
		}
		// System.out.println("Sending: "+s);
		out.println(s);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listen();
	}

	/**
	 * Called by snRevokeConfig() closes the Socket and the Buffers
	 */
	private void close() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (sock != null)
				sock.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
