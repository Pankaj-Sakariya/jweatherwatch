package net.Notifer.Notifers;

import java.io.FileNotFoundException;

import net.NotificationConnector;
import net.SettingsReader;
import net.Utils;
import net.Notifer.Notifer;
import net.Notifer.NotiferTypes;

import org.gnome.gdk.InterpType;
import org.gnome.gdk.Pixbuf;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.StatusIcon;
import org.gnome.notify.Notification;
import org.gnome.notify.Notify;

public class LibNotify implements Notifer {

	private static StatusIcon icon = null;
	private static boolean initialized = false;
	private static boolean gtkInitialized = false;

	public NotiferTypes getName() {

		return NotiferTypes.LibNotify;
	}

	@Override
	public boolean load(String[] notifications) {

		if (!initialized) {
			if (!gtkInitialized)
				try {
					Gtk.init(new String[] {});
					new Thread("gtk") {				
						public void run() {
							Gtk.mainQuit();
							Gtk.main();
						};
					}.start();
					gtkInitialized=true;
				} catch (UnsatisfiedLinkError e) {
					return false;
				}
				

			initialized = true;
			return Notify.init(SettingsReader.name);
		}
		return true;
	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath, final String url) {
		Notification not = new org.gnome.notify.Notification(title,
				description, null, getIcon());
		if (url != null) {
			not.addAction("b", "Visit Forecast",
					new org.gnome.notify.Notification.Action() {
						public void onAction(Notification arg0, String arg1) {
							Utils.visitURL(url);
						}
					});
		}
		not.addAction("a", "Show jWeatherWatch",
				new org.gnome.notify.Notification.Action() {
					public void onAction(Notification arg0, String arg1) {
						NotificationConnector.bringFrameToFront();
					}
				});
		if (iconPath != null)
			try {
				Pixbuf buf = new Pixbuf(iconPath);
				buf=buf.scale(40, 40, InterpType.BILINEAR);
				not.setIcon(buf);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		not.show();
	}

	@Override
	public void unload() {
		if (initialized) {
			Notify.uninit();
			Gtk.mainQuit();
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
