package net.ACCUWeather;

import net.ACCUWeather.WeatherSubtypes.Amount;
import net.ACCUWeather.WeatherSubtypes.Speed;
import net.ACCUWeather.WeatherSubtypes.Temperature;
import net.ACCUWeather.WeatherSubtypes.UVIndex;

public class Weather {

	String weathertext;
	Temperature temperature;

	Temperature realfeel;
	String windgusts;
	Speed windspeed;
	String winddirection;
	String weathericon;
	UVIndex uvindex;
	String url;
	Amount precipitation;

	public String getWeathertext() {
		return weathertext;
	}

	public Temperature getTemperature() {
		return temperature;
	}

	public Temperature getRealfeel() {
		return realfeel;
	}

	public Speed getWindspeed() {
		return windspeed;
	}

	public String getWinddirection() {
		return winddirection;
	}

	public String getWeathericon() {
		return weathericon;
	}
	public UVIndex getUvindex() {
		return uvindex;
	}
	public String getWindgusts() {
		return windgusts;
	}

	public String getUrl() {
		return url;
	}

	public String getNotification() {
		return getWeathertext() + "\nTemprature: " + getTemperature()
				+ "\nRealFeal: " + getRealfeel() + "\nWind: "
				+ getWinddirection() + " " + getWindspeed();
	}

	public Amount getPrecipitation() {
		return precipitation;
	}

	public boolean equals(Weather weather) {
		return precipitation.equals(weather.precipitation)
				&& weathertext.equals(weather.weathertext)
				&& temperature.equals(weather.temperature)
				&& realfeel.equals(weather.realfeel)
				&& windgusts.equals(weather.windgusts)
				&& windspeed.equals(weather.windspeed)
				&& winddirection.equals(weather.winddirection);

	}
	

}
