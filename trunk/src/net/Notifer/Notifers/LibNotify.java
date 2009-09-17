package net.Notifer.Notifers;

import java.awt.TrayIcon;

import net.SettingsReader;
import net.Notifer.Notifer;
import net.Notifer.NotiferTypes;

import org.gnome.gtk.Gtk;
import org.gnome.gtk.StatusIcon;
import org.gnome.notify.Notify;

public class LibNotify implements Notifer{

	private StatusIcon icon=null;
	public LibNotify(TrayIcon ico) {
		
	}
	@Override
	public NotiferTypes getName() {

		
		return NotiferTypes.LibNotify;
	}

	@Override
	public boolean load(String[] notifications) {
		
		Gtk.init(new String []{});
		
		Notify.init(SettingsReader.name);
		
		icon=new StatusIcon();
		icon.setVisible(false);
		
		return true;
	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath) {
		new org.gnome.notify.Notification(title,description,iconPath,icon).show();
		
	}

	@Override
	public void send(String alert, String title, String description) {
		new org.gnome.notify.Notification(title,description,"",icon).show();
		
	}

	@Override
	public void unload() {
		Notify.uninit();
		Gtk.mainQuit();
		
	}

}
