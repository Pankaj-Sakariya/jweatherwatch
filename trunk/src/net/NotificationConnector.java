package net;

import java.awt.TrayIcon;

import net.Notifer.NetNotifer;
import net.Notifer.Notifer;
import net.Notifer.Notifers.KNotify;
import net.Notifer.Notifers.NetGrowl;
import net.Notifer.Notifers.NetSnarl;
import net.Notifer.Notifers.Snarl;
import net.Notifer.Notifers.TrayNotification;

public class NotificationConnector {

	private static Notifer notifer;
	public final static String[] notifications = new String[] { "Startup",
			"Forecast Weather Notification", "Current Weather Notification" };
	private static String host = "localhost";

	public static void initialize(TrayIcon trayIcon) {
		if (notifer != null)
			return;

		switch (Utils.getOS()) {
		case WINDOWS:
			if (setNotifer(new Snarl()))
				return;
			if (setNotifer(new NetSnarl()))
				return;
		case MAC:			
			if (setNotifer(new NetGrowl()))
				return;
			break;
		case LINUX:			
			if (setNotifer(new KNotify()))
				return;
		}

		setNotifer(new TrayNotification(trayIcon));

	}

	public static void sendNotification(String alert, String title,
			String description, String iconPath) {

		if (notifer == null) {
			System.err
					.println("NotificationConnector is not initialized, please run \"NotificationConnector.initialize(trayicon);\" firs.");
			return;
		}
		if (iconPath != null && !iconPath.equals("")) {
			if (notifer instanceof NetNotifer && !host.equals("localhost")
					&& !host.equals("127.0.0.1"))
				iconPath = "http://jweatherwatch.googlecode.com/svn/trunk/iconset/"
						+ iconPath + ".png";
			else
				iconPath = Settings.getWorkindirectory() + "/iconset/"
						+ iconPath + ".png";
			System.out.println(iconPath);
			notifer.send(alert, title, description, iconPath);
		}

		else
			notifer.send(alert, title, description);
	}

	public static boolean setNotifer(Notifer notifer2) {
		if (notifer != null && notifer2.getName() == notifer.getName())
			return false;
		if (notifer != null)
			notifer.unload();
		if (notifer2 instanceof NetNotifer) {
			if (((NetNotifer) notifer2).load(notifications, host)) {
				notifer = notifer2;
				NotificationConnector.sendNotification("Startup", Settings.name
						+ " " + Settings.version, Settings.name + " "
						+ Settings.version + " succsessfully registered wit "
						+ notifer2.getName(), null);
				return true;
			}
		} else if (notifer2.laod(notifications)) {
			notifer = notifer2;
			NotificationConnector.sendNotification("Startup", Settings.name
					+ " " + Settings.version, Settings.name + " "
					+ Settings.version + " succsessfully registered wit "
					+ notifer2.getName(), null);
			return true;
		} else
			System.err.println("Setting Notifer failed");
		return false;
	}

	public static void exit() {
		notifer.unload();
	}

	public static Notifer getNotifer() {
		return notifer;
	}

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		NotificationConnector.host = host;
		if (notifer instanceof NetNotifer) {
			((NetNotifer) NotificationConnector.getNotifer())
					.setHost(NotificationConnector.host);
		}

	}

}
