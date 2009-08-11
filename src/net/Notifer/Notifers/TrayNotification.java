package net.Notifer.Notifers;

import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

import net.Notifer.Notifer;
import net.Notifer.NotiferTypes;

public class TrayNotification implements Notifer {
	private TrayIcon trayIcon = null;

	public TrayNotification(TrayIcon trayIcon) {
		this.trayIcon = trayIcon;
	}

	@Override
	public boolean laod(String[] notifications) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath) {
		trayIcon.displayMessage(title, description, MessageType.NONE);

	}

	@Override
	public void unload() {
		// TODO Auto-generated method stub

	}

	@Override
	public void send(String alert, String title, String description) {
	send(alert, title, description, null);
		
	}
	@Override
	public NotiferTypes getName() {
		return NotiferTypes.TrayIcon;
	}

}
