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

public class SnarlNetworkBridge {
	int timeout = 10;
	double SNPVersion = 1.0;
	String head = "type=SNP#?version=" + SNPVersion;
	Socket sock = null;
	PrintWriter out = null;
	BufferedReader in = null;
	String appName = null;
	private HashMap<String, Integer> alerts = new HashMap<String, Integer>();
	private boolean snarlIsRunning = true;

	public SnarlNetworkBridge(String appName) {
		create(appName, "localhost");
	}

	public SnarlNetworkBridge(String appName, String host) {
		create(appName, host);
	}

	public String snRegisterConfig() {
		return send(head + "#?action=register#?app=" + appName);
	}

	public String snRegisterAlert(String title) {
		alerts.put(title, alerts.size() + 1);
		return send(head + "#?action=add_class#?app=" + appName + "#?class="
				+ alerts.size() + "#?title=" + title);

	}

	public String snShowMessage(String alert, String title, String description) {
		return snShowMessage(alert, title, description, timeout);
	}

	public String snShowMessage(String alert, String title, String description,
			int timeout) {
		if (snarlIsRunning)
			return send(head + "#?action=notification#?app=" + appName
					+ "#?class=" + alerts.get(alert) + "#?title=" + title
					+ "#?text=" + description + "#?timeout=" + timeout);
		return "Snarl not Running";
	}

	public void snSetTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String snRevokeConfig() {
		String out = send(head + "#?action=unregister#?app=" + appName);
		close();
		return out;
	}

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

	public static void main(String[] args) {
		SnarlNetworkBridge test = new SnarlNetworkBridge("Nerver"/* ,"philipp-pc" */);
		test.snRegisterConfig();
		test.snRegisterAlert("Nerv");
		test.snShowMessage("Nerv1", "Hallo", "Du Dusssel");
		test.snRevokeConfig();
	}
	public boolean SnarlIsRunnging(){
		return snarlIsRunning;
	}

}
