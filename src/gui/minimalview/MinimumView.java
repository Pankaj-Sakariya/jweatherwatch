package gui.minimalview;

import gui.Gui;
import gui.WeatherView;
import net.SettingsReader;
import net.Utils;
import net.ACCUWeather.Location;
import net.ACCUWeather.LocationList;

public class MinimumView extends WeatherView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8337831440587765857L;

	private MininmalPanel mininmalPanerls[] = null;
	private int imsize = SettingsReader.getInstance().minimumViewSize;//65;

	public MinimumView(Gui parent) {
		super(parent,Views.minimal);
		this.setLayout(null);
		mininmalPanerls = new MininmalPanel[SettingsReader.getInstance().mininimalViewRows];
		parent.setIconImage(new Utils().imageLodaer("./iconset/01.png"));
		for (Location l : parent.getLocations())
			addLocation(l);

	}

	
	public void addLocation(final Location location) {
		update(0);
	}

	public void update(int nr) {
		super.update(nr);
		LocationList list = parent.getLocations();
		for (int i = 0; i < SettingsReader.getInstance().mininimalViewRows && i + nr < list.size(); ++i) {
			if (mininmalPanerls[i]==null||!list.get(nr + i).equals(mininmalPanerls[i].getWeatherLocation()))
				mininmalPanerls[i] = new MininmalPanel(list.get(nr + i),imsize);
			mininmalPanerls[i].setShifted(SettingsReader.getInstance().minimalView_Shifted);
			mininmalPanerls[i].setLocation(5, (imsize + 40) * i);
			this.setSize((imsize + 5) * 7 + 10, (imsize + 40) * (i + 1));
			parent.setSize(this.getWidth() + 5, this.getHeight() + 30);
			this.add(mininmalPanerls[i]);
		}
	}

	@Override
	public void close() {
		for (MininmalPanel mp : mininmalPanerls) {
			if (mp != null)
				mp.stop();
		}
	}

	public void setShifted(boolean shifted) {
		update(0);
	}

}
