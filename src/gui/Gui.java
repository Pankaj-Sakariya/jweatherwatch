package gui;

import gui.settings.SettingsDialog;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.SystemTray;

import javax.swing.JDialog;
import javax.swing.JFrame;

import net.ACCUWeatherFetcher;
import net.NotificationConnector;
import net.NotificationThread;
import net.SettingsReader;
import net.Utils;
import net.ACCUWeather.Location;
import net.ACCUWeather.LocationList;
import net.ACCUWeather.LocationListUser;

public class Gui extends JDialog implements LocationListUser {
	private NotificationThread notificationthread = null; // @jve:decl-index=0:

	private static final long serialVersionUID = 1L;
	private WeatherView view = null;
	private LocationList locations = new LocationList(); // @jve:decl-index=0:

	Utils utils = new Utils(); // @jve:decl-index=0:
	private Splash splash = null; // @jve:decl-index=0:visual-constraint="277,470"
	private WeatherTrayIcon trayIcon = null;
	private SettingsDialog settings = null; // @jve:decl-index=0:visual-constraint="-3,68"
	static {
		SettingsReader.load();
	}

	/**
	 * This is the default constructor
	 */
	public Gui(boolean visible) {
		super(null, ModalityType.TOOLKIT_MODAL);
		initialize();
		this.setVisible(visible);
		NotificationConnector.initialize(this, getTrayIcon());
		locations = ACCUWeatherFetcher.load(this);
		for (Location l : locations) {
			splash.setLoadingText("Loading Station: " + l);
			l.update();
		}
		view = WeatherView.getViewByName(SettingsReader.view, this);
		this.setContentPane(view);
		setNotificationthread(new NotificationThread(locations,
				SettingsReader.notificationInterval));
		/*
		 * JOptionPane.showMessageDialog(null,
		 * "This is a beta version please report all occuring errors.",
		 * "This is a beta", JOptionPane.INFORMATION_MESSAGE);
		 */
	}

	public void addLocation(Location l) {
		if (!locations.containsValue(l)) {
			l.update();
			System.out.println("Adding Location: " + l + " to location list.");
			locations.put(locations.size(), l);
			for (Location lo : locations) {
				System.out.println("[" + lo.getNr() + "] " + lo);
			}
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		try {
			SystemTray.getSystemTray().add(getTrayIcon());

		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setResizable(false);

		this.setSize(new Dimension(817, 448));
		this.setContentPane(getSplash());
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle(SettingsReader.name);
		this.setIconImage(utils.imageLodaer(SettingsReader.getIconpPath()
				+ "/01.png"));
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowIconified(java.awt.event.WindowEvent e) {
				setVisible(false);
			}

		});

	}

	public WeatherTrayIcon getTrayIcon() {
		if (trayIcon == null) {
			trayIcon = new WeatherTrayIcon(this, utils
					.imageLodaer(SettingsReader.getIconpPath() + "/01.png"));
		}

		return trayIcon;
	}

	/**
	 * This method initializes splash
	 * 
	 * @return gui.Splash
	 */
	private Splash getSplash() {
		if (splash == null) {
			splash = new Splash();
		}
		return splash;
	}

	public void close() {
		this.setContentPane(getSplash());
		splash.setLoadingText("Saving...");
		System.out.println("Saving....");
		ACCUWeatherFetcher.save(locations);
		SettingsReader.save();
		// ACCUWeatherFetcher.saveOfflineData(locations, splash);
		NotificationConnector.exit();
		SystemTray.getSystemTray().remove(trayIcon);

	}

	public LocationList getLocations() {
		return locations;
	}

	/**
	 * This method initializes settings
	 * 
	 * @return gui.Settings
	 */
	SettingsDialog getSettings() {
		if (settings == null) {
			settings = new SettingsDialog(this);
		}
		return settings;
	}

	public void setNotificationthread(NotificationThread notificationthread) {
		this.notificationthread = notificationthread;
	}

	public NotificationThread getNotificationthread() {
		return notificationthread;
	}

	public void setView(WeatherView view) {
		if (this.view != null)
			this.view.close();
		this.view = view;
		SettingsReader.view = view.getType();
		this.setContentPane(view);

	}

	public WeatherView getView() {
		return view;
	}

	public void locationUpdated(Location location) {
		if (view != null)
			view.locationUpdated();
	}

	@Override
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		super.setVisible(b);
		if (b)
			this.toFront();
	}

} // @jve:decl-index=0:visual-constraint="10,10"
