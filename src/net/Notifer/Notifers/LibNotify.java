package net.Notifer.Notifers;

import java.io.FileNotFoundException;

import net.NotificationConnector;
import net.SettingsReader;
import net.Utils;
import net.Notifer.Notifer;
import net.Notifer.NotiferTypes;
import net.Utils.OS;

import org.gnome.gdk.InterpType;
import org.gnome.gdk.Pixbuf;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Widget;
import org.gnome.notify.Notification;
import org.gnome.notify.Notify;

public class LibNotify implements Notifer {

	private static final Widget widgetNull = null;
	private static boolean initialized = false;
	private static boolean gtkInitialized = false;

	public NotiferTypes getName() {
		return NotiferTypes.LibNotify;
	}

	@Override
	public boolean load(String[] notifications) {
		if (Utils.getOS() != OS.LINUX)
			return false;
		if (!initialized) {
			if (!gtkInitialized)
				try {
					Gtk.init(new String[] {});
					Gtk.setProgramName(SettingsReader.name);
					gtkInitialized = true;
				} catch (UnsatisfiedLinkError e) {
					e.printStackTrace();
					return false;
				}
			new Thread("gtk") {
				public void run() {
					Gtk.main();
				};
			}.start();
			initialized = true;
			return Notify.init(SettingsReader.name);
		}
		return true;
	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath, final String url) {
		Notification not = new org.gnome.notify.Notification(title,
				description, SettingsReader.getInstance().getIconpPath()
						+ "01.png", widgetNull);
		if (url != null) {
			not.addAction("VisitForecast", "Visit Forecast",
					new org.gnome.notify.Notification.Action() {
						public void onAction(Notification arg0, String arg1) {
							Utils.visitURL(url);
						}
					});
		}
		not.addAction("BringToFront", "Show jWeatherWatch",
				new org.gnome.notify.Notification.Action() {
					public void onAction(Notification arg0, String arg1) {
						NotificationConnector.bringFrameToFront();
					}
				});
		if (iconPath != null)
			try {
				Pixbuf buf = new Pixbuf(iconPath);
				buf = buf.scale(40, 40, InterpType.BILINEAR);
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

}
