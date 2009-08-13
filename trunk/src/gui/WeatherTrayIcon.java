package gui;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import net.NotificationConnector;
import net.Settings;
import net.ACCUWeather.Location;

public class WeatherTrayIcon extends TrayIcon {

	PopupMenu popupMenu = null;
	Gui parent = null;

	public WeatherTrayIcon(final Gui parent, Image image) {
		super(image, Settings.name);
		this.parent = parent;

		this.setImageAutoSize(true);
		this.setToolTip(Settings.name + " " + Settings.version);
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (parent.getState() == JFrame.ICONIFIED) {
					parent.setVisible(true);
					parent.setState(JFrame.NORMAL);
					parent.toFront();
				}
			}
		});

		this.setPopupMenu(getPopupMenu());

	}

	@Override
	public PopupMenu getPopupMenu() {
		if (popupMenu == null) {
			popupMenu = new PopupMenu();
			MenuItem exit = new MenuItem("Exit");
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parent.close();
					parent.dispose();
					System.exit(0);
				}
			});
			MenuItem bringToFront = new MenuItem("Show/Hide");
			bringToFront.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (parent.getState() == JFrame.ICONIFIED) {
						parent.setVisible(true);
						parent.setState(JFrame.NORMAL);
						parent.toFront();
					} else if (parent.getState() == JFrame.NORMAL)
						parent.setState(JFrame.ICONIFIED);

				}
			});
			MenuItem notifyCurrent = new MenuItem("Notify");
			notifyCurrent
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							for (Location l : parent.getLocations()) {
								NotificationConnector.sendNotification(
										"Current Weather Notification", l
												.toString(), l
												.getCurrentWeather()
												.getNotification(), l
												.getCurrentWeather()
												.getWeathericon());
							}
						}
					});
			
			
			MenuItem settings = new MenuItem("Settings");
			settings.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					parent.getSettings().setVisible(true);
				}
			});

			popupMenu.add(new MenuItem(Settings.name + " " + Settings.version));
			popupMenu.add(new MenuItem("-"));
			popupMenu.add(settings);
			popupMenu.add(new MenuItem("-"));
			popupMenu.add(bringToFront);
			popupMenu.add(notifyCurrent);
			popupMenu.add(new MenuItem("-"));
			popupMenu.add(exit);

		}

		return popupMenu;
	}
}
