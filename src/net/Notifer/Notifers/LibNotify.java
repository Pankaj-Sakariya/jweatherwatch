package net.Notifer.Notifers;

import java.awt.TrayIcon;

import net.NotificationConnector;
import net.SettingsReader;
import net.Notifer.Notifer;
import net.Notifer.NotiferTypes;

import org.gnome.gtk.Gtk;
import org.gnome.gtk.StatusIcon;
import org.gnome.notify.Notification;
import org.gnome.notify.Notify;

public class LibNotify implements Notifer {

	private StatusIcon icon = null;
	private static boolean initialized = false;
	private static boolean gtkInitialized = false;

	public NotiferTypes getName() {

		return NotiferTypes.LibNotify;
	}

	@Override
	public boolean load(String[] notifications) {

		if (!initialized) {
			if (!gtkInitialized)
				Gtk.init(new String[] {});

			Notify.init(SettingsReader.name);

			// Gtk.main();
			gtkInitialized = true;
			initialized = true;
		}
		return true;
	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath) {
		Notification not = new org.gnome.notify.Notification(title,
				description, iconPath, getIcon());
		not.addAction("a", "Show jWeatherWatch",
				new org.gnome.notify.Notification.Action() {

					public void onAction(Notification arg0, String arg1) {
						System.out.println("ia");
						NotificationConnector.bringFrameToFront();
					
					}

				});
		not.show();
	}

	@Override
	public void send(String alert, String title, String description) {
		send(alert, title, description, "");
	}

	@Override
	public void unload() {
		if (initialized) {
			Notify.uninit();
			// Gtk.mainQuit();
			initialized = false;
		}
	}

	private StatusIcon getIcon() {
		if (icon == null) {
			icon = new StatusIcon();
			icon.setVisible(false);
		}
		return icon;
	}
}
