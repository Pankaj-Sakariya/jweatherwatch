package net;

import java.awt.TrayIcon;

import net.Notifer.Notifer;

public class NotificationConnector {

	private static Notifer notifer;

	public static void initialize(TrayIcon trayIcon) {
		notifer = new net.Notifer.Notifers.Snarl();
		if (!notifer.laod())
			notifer = new net.Notifer.Notifers.NetSnarl();
		if (!notifer.laod())
			notifer = new net.Notifer.Notifers.TrayNotification(trayIcon);
	}

	public static void sendNotification(String alert, String title,
			String description, String iconPath) {
		if (notifer == null) {
			System.err
					.println("NotificationConnector is not initialized, please run \"NotificationConnector.initialize(trayicon);\" firs.");
			return;
		}	
		notifer.send(alert, title, description, System.getProperty("user.dir")+"/iconset/"+iconPath+".png");
	}

	public static void exit() {
		notifer.unload();
	}

}
