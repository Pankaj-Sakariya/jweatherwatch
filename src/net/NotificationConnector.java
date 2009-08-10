package net;

import java.awt.TrayIcon;

import net.Notifer.Notifer;

public class NotificationConnector {

	private static Notifer notifer;

	public static void initialize(TrayIcon trayIcon) {
		switch (Utils.getOS()) {
		case WINDOWS:
			notifer = new net.Notifer.Notifers.Snarl();
			if (!notifer.laod())
				return;
				notifer = new net.Notifer.Notifers.NetSnarl();
				if(notifer.laod())
					return;
				break;
		case MAC:
			notifer=new net.Notifer.Notifers.Growl();	
			if(notifer.laod())
				return;
			break;
		}

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
	public static boolean setNotifer(Notifer notifer2){
		if(notifer!=null)
			notifer.unload();
		if(notifer2.laod()){
				notifer=notifer2;
				return true;
		}
		else
			System.err.println("Setting Notifer failed");
		return false;
	}
	public static void exit() {
		notifer.unload();
	}
	public static Notifer getNotifer() {
		return notifer;
	}

}
