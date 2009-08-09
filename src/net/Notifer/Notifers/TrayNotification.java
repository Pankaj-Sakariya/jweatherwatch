package net.Notifer.Notifers;

import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

import net.Notifer.Notifer;

public class TrayNotification implements Notifer{
	private TrayIcon trayIcon=null;
public TrayNotification(TrayIcon trayIcon) {
	this.trayIcon=trayIcon;
}
	@Override
	public boolean laod() {
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

}
