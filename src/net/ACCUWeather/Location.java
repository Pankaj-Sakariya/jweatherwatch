package net.ACCUWeather;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import net.ACCUWeatherFetcher;
import net.NotificationConnector;
import net.Utils;
import net.ACCUWeather.WeatherSubtypes.UnitCode;

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
	private LocationList parentLocationList = null;

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


	public CurrentWeather getCurrentWeather() {
		if (currentWeather == null) {
			update();
			System.out.println("Blaaa");
			NotificationConnector.sendNotification(
					"Current Weather Notification", this.toString(),
					currentWeather.getNotification(), currentWeather
							.getWeathericon(),currentWeather.getUrl());
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
		if(TimeZone.getDefault().inDaylightTime(new Date()))
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
						+ UnitCode.getVal());

		if (timeZone == null)
			setTimezone(data);
		boolean updated = false;

		CurrentWeather cw = new CurrentWeather((Element) data
				.getElementsByTagName("currentconditions").item(0));
		FiveDayForecast fdf = new FiveDayForecast((Element) data
				.getElementsByTagName("forecast").item(0));

		if (currentWeather == null )
			NotificationConnector.sendNotification(
					"Current Weather Notification", this.toString(),
					cw.getNotification(), cw
							.getWeathericon(),cw.getUrl());

		
		if(currentWeather != null &&!currentWeather.equals(cw)){
			NotificationConnector.sendNotification(
					"Current Weather Notification", this.toString(),
					currentWeather.getNotification(), currentWeather
							.getWeathericon(),currentWeather.getUrl());
			updated=true;
		}
			
		
		if (fiveDayForecast != null&&!fiveDayForecast.equals(fdf)) {
			for (int i = 1; i <= 5; ++i) {
				if (!fdf.getDay(i).getDay().equals(
						fiveDayForecast.getDay(i).getDay())) {
					NotificationConnector.sendNotification(
							"Forecast Weather Notification", this.toString(),
							fdf.getDay(i).getDay().getNotification(), fdf
									.getDay(i).getDay().getWeathericon(),fdf
									.getDay(i).getDay().getUrl());
				}
				if (!fdf.getDay(i).getNight().equals(
						fiveDayForecast.getDay(i).getNight())) {
					NotificationConnector.sendNotification(
							"Forecast Weather Notification", this.toString(),
							fdf.getDay(i).getNight().getNotification(), fdf
									.getDay(i).getNight().getWeathericon(),fdf
									.getDay(i).getNight().getUrl());

				}
			}
			updated=true;
		}
		fiveDayForecast = fdf;
		currentWeather = cw;
		if (updated)
			parentLocationList.updated(this);

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
		int h=getTimeZone().get(Calendar.HOUR_OF_DAY);
		int m=getTimeZone().get(Calendar.MINUTE);
		return (h>9?h:"0"+h)+":"+(m>9?m:"0"+m);
		
	}

	public Calendar getTimeZone() {
		return Calendar.getInstance(TimeZone.getTimeZone("GMT" + timeZone));	
	}

	protected void setParentLocationList(LocationList parentLocationList) {
		this.parentLocationList = parentLocationList;
	}
}
