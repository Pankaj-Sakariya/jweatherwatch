package gui;

import gui.defaultview.DefaultView;
import gui.minimalview.MinimumView;

import javax.swing.JPanel;

import net.ACCUWeather.Location;

public abstract class WeatherView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1213892901674065265L;

	public enum Views {
		standart, minimal, error
	}

	public Gui parent = null;
	private Views name = Views.error;
	public int displayed = -1;

	public WeatherView(Gui parent, Views name) {
		this.parent = parent;
		this.name = name;
	}

	public abstract void addLocation(Location location);

	public abstract void close();

	public Views getType() {
		return name;
	}

	public static WeatherView getViewByName(Views name, Gui gui) {
		switch (name) {
		case minimal:
			return new MinimumView(gui);
		case standart:
			return new DefaultView(gui);
		default:
			return null;
		}
	}

	public void update(int nr) {
		displayed = nr;
	}

	public void locationUpdated() {
		update(displayed);
	}
}
