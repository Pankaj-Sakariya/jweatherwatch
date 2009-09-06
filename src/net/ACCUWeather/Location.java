package net.ACCUWeather;

import java.util.Calendar;
import java.util.TimeZone;

import net.ACCUWeatherFetcher;
import net.NotificationConnector;
import net.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Location implements Runnable {

	private int nr = -1;
	private String city;
	private String state;
	private String location;
	private String timeZone = null;
	private CurrentWeather currentWeather = null;
	private FiveDayForecast fiveDayForecast = null;
	private boolean updateIsRunnging = false;
	private int obsDaylight = 0;
	private LocationList parentLocationList=null;

	public Location(Element element) {
		city = element.getAttribute("city");
		state = element.getAttribute("state");
		location = element.getAttribute("location");
	}

	public Location(String s) {
		location = s;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getLocation() {
		return location;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public CurrentWeather getCurrentWeather() {
		if (currentWeather == null) {
			update();
			NotificationConnector.sendNotification(
					"Current Weather Notification", this.toString(),
					currentWeather.getNotification(), currentWeather
							.getWeathericon());
		}
		if (!updateIsRunnging) {
			new Thread(this).start();
			updateIsRunnging = true;
		}
		return currentWeather;
	}

	public FiveDayForecast getFiveDayForecast() {
		if (fiveDayForecast == null)
			update();
		if (!updateIsRunnging) {
			new Thread(this).start();
			updateIsRunnging = true;
		}

		return fiveDayForecast;
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	@Override
	public String toString() {
		return this.city + ", " + this.state;
	}

	public void setTimezone(Document doc) {
		if (timeZone != null)
			return;
		Element ele = (Element) doc.getElementsByTagName("local").item(0);
		String t = Utils.getXMLValue(ele, "timeZone");
		int ti;
		if (t.contains(":"))
			ti = Integer.valueOf(t.substring(0, t.indexOf(":")));
		else
			ti = Integer.valueOf(t);
		obsDaylight = Integer.valueOf(Utils.getXMLValue(ele, "obsDaylight"));
		if (t.contains(":"))
			timeZone = (ti + obsDaylight) + t.substring(t.indexOf(":"));
		else
			timeZone = ti + obsDaylight + "";
		if (timeZone.charAt(0) != '-')
			timeZone = "+" + timeZone;

	}

	public void update() {
		Document data = ACCUWeatherFetcher
				.getDocument(ACCUWeatherFetcher.baseURL
						+ "weather-data.asp?location=" + getLocation()
						+ "&metric="
						+ ACCUWeatherFetcher.getUnit_code().getVal());

		setTimezone(data);

		CurrentWeather cw = new CurrentWeather((Element) data
				.getElementsByTagName("currentconditions").item(0),
				ACCUWeatherFetcher.getUnit_code());
		FiveDayForecast fdf = new FiveDayForecast((Element) data
				.getElementsByTagName("forecast").item(0), ACCUWeatherFetcher
				.getUnit_code());

		if (currentWeather != null && !cw.equals(currentWeather)) {
			parentLocationList.updated(this);
			NotificationConnector.sendNotification(
					"Current Weather Notification", this.toString(),
					currentWeather.getNotification(), currentWeather
							.getWeathericon());
		}

		if (fiveDayForecast != null && !fdf.equals(fiveDayForecast)) {
			parentLocationList.updated(this);
			for (int i = 1; i <= 5; ++i) {
				if (!fdf.getDay(i).getDay().equals(
						fiveDayForecast.getDay(i).getDay())) {
					NotificationConnector.sendNotification(
							"Forecast Weather Notification", this.toString(),
							fdf.getDay(i).getDay().getNotification(), fdf
									.getDay(i).getDay().getWeathericon());
				}
				if (!fdf.getDay(i).getNight().equals(
						fiveDayForecast.getDay(i).getNight())) {
					NotificationConnector.sendNotification(
							"Forecast Weather Notification", this.toString(),
							fdf.getDay(i).getNight().getNotification(), fdf
									.getDay(i).getNight().getWeathericon());

				}
			}
			
		}
		fiveDayForecast = fdf;
		currentWeather = cw;

	}

	public void stop() {
		updateIsRunnging = false;

	}

	@Override
	public void run() {
		while (updateIsRunnging) {
			update();
			try {
				Thread.sleep(600000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String getCurrentTime() {
		Calendar c = getTime();
		String hour = c.get(Calendar.HOUR_OF_DAY) + "";
		String min = c.get(Calendar.MINUTE) + "";
		if (hour.length() == 1)
			hour = "0" + hour;
		if (min.length() == 1)
			min = "0" + min;
		return hour + ":" + min;

	}

	public Calendar getTime() {
		return Calendar.getInstance(TimeZone.getTimeZone("GMT" + timeZone));
	}

	protected void setParentLocationList(LocationList parentLocationList) {
		this.parentLocationList = parentLocationList;
	}
}
