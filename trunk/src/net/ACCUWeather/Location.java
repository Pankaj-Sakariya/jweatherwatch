package net.ACCUWeather;

import java.util.Calendar;
import java.util.TimeZone;

import lib.Utils;
import net.ACCUWeatherFetcher;
import net.NotificationConnector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Location implements Runnable {
	String city;
	String state;
	String location;
	String timeZone = null;
	CurrentWeather currentWeather = null;
	FiveDayForecast fiveDayForecast = null;
	boolean updateIsRunnging = false;
	int obsDaylight = 0;

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
			currentWeather = ACCUWeatherFetcher.getCurrentWeather(this);
			NotificationConnector.sendNotification(
					"Current Weather Notification", this.toString(),
					currentWeather.getNotification(),System.getProperty("user.dir")+"/iconset/"+currentWeather.getWeathericon()+".png");
		}
		if (!updateIsRunnging) {
			new Thread(this).start();
			updateIsRunnging = true;
		}
		return currentWeather;
	}

	public FiveDayForecast getFiveDayForecast() {
		if (fiveDayForecast == null)
			fiveDayForecast = ACCUWeatherFetcher.getFiveDayDorecast(this);
		if (!updateIsRunnging) {
			new Thread(this).start();
			updateIsRunnging = true;
		}

		return fiveDayForecast;
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
		currentWeather = ACCUWeatherFetcher.getCurrentWeather(this);
		fiveDayForecast = ACCUWeatherFetcher.getFiveDayDorecast(this);

	}

	public void stop() {
		updateIsRunnging = false;

	}

	@Override
	public void run() {
		while (updateIsRunnging) {
			if (currentWeather != null) {
				CurrentWeather temp = ACCUWeatherFetcher
						.getCurrentWeather(this);
				if (!temp.equals(currentWeather)) {
					currentWeather = temp;
					NotificationConnector.sendNotification("Current Weather Notification", this.toString(),
							currentWeather.getNotification(),System.getProperty("user.dir")+"/iconset/"+currentWeather.getWeathericon()+".png");
				}
			}
			if (fiveDayForecast != null) {
				FiveDayForecast fore = ACCUWeatherFetcher
						.getFiveDayDorecast(this);
				if (!fore.equals(fiveDayForecast)) {

					for (int i = 1; i <= 5; ++i) {
						if (!fore.getDay(i).getDay().equals(
								fiveDayForecast.getDay(i).getDay())) {
							NotificationConnector.sendNotification(
									"Forecast Weather Notification", this
											.toString(), fore.getDay(i)
											.getDay().getNotification(),System.getProperty("user.dir")+"/iconset/"+fore.getDay(i)
											.getDay().getWeathericon()+".png");
						}
						if (!fore.getDay(i).getNight().equals(
								fiveDayForecast.getDay(i).getNight())) {
							NotificationConnector.sendNotification(
									"Forecast Weather Notification", this
											.toString(), fore.getDay(i)
											.getNight().getNotification(),System.getProperty("user.dir")+"/iconset/"+fore.getDay(i)
											.getNight().getWeathericon()+".png");

						}
					}
					fiveDayForecast = fore;
				}
			}
			try {
				Thread.sleep(600000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public Calendar getCurrentTime() {
		return Calendar.getInstance(TimeZone.getTimeZone("GMT"+timeZone));
	}
}
