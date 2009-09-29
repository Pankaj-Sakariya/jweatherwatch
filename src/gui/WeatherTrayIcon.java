package gui;

import gui.defaultview.DefaultView;
import gui.minimalview.MinimumView;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.Main;
import net.NotificationConnector;
import net.SettingsReader;
import net.ACCUWeather.Location;

public class WeatherTrayIcon extends TrayIcon {

	PopupMenu popupMenu = null;
	Gui parent = null;

	public WeatherTrayIcon(final Gui parent, Image image) {
		super(image, SettingsReader.name);
		this.parent = parent;

		this.setImageAutoSize(true);
		this.setToolTip(SettingsReader.name + " " + SettingsReader.getInstance().getVersion());
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				NotificationConnector.bringFrameToFront();
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
					parent.dispose();
					Main.close();
				}
			});
			MenuItem bringToFront = new MenuItem("Show/Hide");
			bringToFront.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					parent.setVisible(!parent.isVisible());

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
												.getWeathericon(),l.getCurrentWeather().getUrl());
							}
						}
					});

			MenuItem settings = new MenuItem("Settings");
			settings.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					parent.getSettings().setVisible(true);
				}
			});
			MenuItem minimalView = new MenuItem("Minimal View");
			minimalView.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					parent.setView(new MinimumView(parent));
				}
			});

			MenuItem stadartView = new MenuItem("Default View");
			stadartView.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					parent.setView(new DefaultView(parent));
				}
			});
			popupMenu.add(new MenuItem(SettingsReader.name + " " + SettingsReader.getInstance().getVersion()));
			popupMenu.add(new MenuItem("-"));
			popupMenu.add(settings);
			popupMenu.add(new MenuItem("-"));
			popupMenu.add(stadartView);
			popupMenu.add(minimalView);
			popupMenu.add(new MenuItem("-"));
			popupMenu.add(bringToFront);
			popupMenu.add(notifyCurrent);
			popupMenu.add(new MenuItem("-"));
			popupMenu.add(exit);

		}

		return popupMenu;
	}
}
