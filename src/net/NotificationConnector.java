package net;

import java.awt.TrayIcon;

import javax.swing.JFrame;

import net.Notifer.NetNotifer;
import net.Notifer.Notifer;
import net.Notifer.NotiferTypes;
import net.Notifer.Notifers.TrayNotification;

public class NotificationConnector {

	private static Notifer notifer;
	private static JFrame frame = null;
	public final static String[] notifications = new String[] { "Startup",
			"Forecast Weather Notification", "Current Weather Notification" };
	private static String host = "localhost";

	public static void initialize(JFrame frame, TrayIcon trayIcon) {
		NotificationConnector.frame = frame;
		if (SettingsReader.notifer != null
				&& setNotifer(NotiferTypes.getNotifer(SettingsReader.notifer,
						trayIcon)))
			return;

		if (notifer != null)
			return;

	/*	switch (Utils.getOS()) {
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
		}*/

		setNotifer(new TrayNotification(trayIcon));

	}

	public static void sendNotification(String alert, String title,
			String description, String iconPath) {

		if (notifer == null) {
			System.err
					.println("NotificationConnector is not initialized, please run \"NotificationConnector.initialize(trayicon);\" firs.");
			return;
		}
		if (iconPath == null || iconPath.equals("")) {
			notifer.send(alert, title, description);
		return;
		}
		if(iconPath.matches("[0-9][0-9]")){
		
			if (notifer instanceof NetNotifer )
				iconPath = "http://jweatherwatch.googlecode.com/svn/trunk/iconset/"
						+ iconPath + ".png";
			else
				iconPath = SettingsReader.getIconpPath()
						+ iconPath + ".png";
			notifer.send(alert, title, description, iconPath);
		}else
			notifer.send(alert, title, description, iconPath);
			
	}

	public static boolean setNotifer(Notifer notifer2) {
		if (notifer != null && notifer2.getName() == notifer.getName())
			return false;
		if (notifer != null)
			notifer.unload();
		if (notifer2 instanceof NetNotifer) {
			if (((NetNotifer) notifer2).load(notifications, host)) {
				notifer = notifer2;
				NotificationConnector.sendNotification("Startup", SettingsReader.name
						+ " " + SettingsReader.version, SettingsReader.name + " "
						+ SettingsReader.version + " succsessfully registered wit "
						+ notifer2.getName(), null);
				return true;
			}
		} else if (notifer2.load(notifications)) {
			notifer = notifer2;
			NotificationConnector.sendNotification("Startup", SettingsReader.name
					+ " " + SettingsReader.version, SettingsReader.name + " "
					+ SettingsReader.version + " succsessfully registered wit "
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
		if(NotificationConnector.host.equals(host))return;
		NotificationConnector.host = host;
		if (notifer instanceof NetNotifer) {
			((NetNotifer) NotificationConnector.getNotifer())
					.setHost(NotificationConnector.host);
		}

	}

	public static JFrame getFrame() {
		return frame;
	}

	public static void bringFrameToFront() {
		frame.setState(JFrame.NORMAL);
	}
}
