package net;

import gui.Gui;

import java.awt.TrayIcon;
import java.io.Closeable;

import net.Notifer.NetNotifer;
import net.Notifer.Notifer;
import net.Notifer.NotiferTypes;
import net.Notifer.Notifers.TrayNotification;

public class NotificationConnector implements Closeable {
	private static Notifer notifer;
	private static Gui frame = null;
	public final static String[] notifications = new String[] { "Startup",
			"Forecast Weather Notification", "Current Weather Notification" };
	private static String host = "localhost";
	private static TrayIcon trayIcon = null;

	public static void initialize(Gui frame, TrayIcon trayIcon) {
		NotificationConnector.trayIcon = trayIcon;
		Main.thingsToClose.add(new NotificationConnector());
		NotificationConnector.frame = frame;
		if (SettingsReader.getInstance().notifer != null
				&& setNotifer(NotiferTypes.getNotifer(SettingsReader
						.getInstance().notifer, trayIcon)))
			return;

		if (notifer != null)
			return;

	

		setNotifer(new TrayNotification(trayIcon));

	}

	public static void sendNotification(String alert, String title,
			String description, String iconPath, String url) {
		if (notifer == null) {
			System.err
					.println("NotificationConnector is not initialized, please run \"NotificationConnector.initialize(trayicon);\" firs.");
			return;
		}
		if (iconPath == null || iconPath.equals("")) {
			notifer.send(alert, title, description, null, url);
			return;
		}
		if (iconPath.matches("[0-9][0-9]")) {

			if (notifer instanceof NetNotifer)
				iconPath = "http://jweatherwatch.googlecode.com/svn/trunk/iconset/"
						+ iconPath + ".png";
			else
				iconPath = SettingsReader.getInstance().getIconpPath()
						+ iconPath + ".png";
			notifer.send(alert, title, description, iconPath, url);
		} else
			notifer.send(alert, title, description, iconPath, url);

	}

	public static boolean setNotifer(Notifer notifer2) {
		if (notifer != null && notifer2.getName() == notifer.getName())
			return false;
		boolean ret=false;
		if (notifer2 instanceof NetNotifer) {

			if (((NetNotifer) notifer2).load(notifications, host)) {
				if (notifer != null)
					notifer.unload();
				notifer = notifer2;
				ret=true;		
			}
		} else if (notifer2.load(notifications)) {
			if (notifer != null)
				notifer.unload();
			notifer = notifer2;
			ret=true;
		} else
			System.err.println("Setting Notifer failed");
		if(ret)
			NotificationConnector.sendNotification("Startup",
					SettingsReader.name + " v"
							+ SettingsReader.getInstance().getVersion(),
					SettingsReader.name + " v"
							+ SettingsReader.getInstance().getVersion()
							+ " succsessfully registered wit "
							+ notifer2.getName(), null, null);
		return ret;
	}

	public void close() {
		notifer.unload();
	}

	public static Notifer getNotifer() {
		return notifer;
	}

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		if (NotificationConnector.host.equals(host))
			return;
		NotificationConnector.host = host;
		if (notifer != null && notifer instanceof NetNotifer)
			setNotifer(NotiferTypes.getNotifer(notifer.getName(), trayIcon));

	}

	public static Gui getFrame() {
		return frame;
	}

	public static void bringFrameToFront() {
		frame.setVisible(true);
	}
}
