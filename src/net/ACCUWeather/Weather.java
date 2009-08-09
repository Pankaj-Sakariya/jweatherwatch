package net.ACCUWeather;



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
	public String getUrl() {
		return url;
	}
	


	public String getNotification() {
		return getWeathertext() + "\nTemprature: " + getTemperature()
				+ "\nRealFeal: " + getRealfeel() + "\nWind: "
				+ getWinddirection() + " " + getWindspeed();
	}
	

	public boolean equals(Weather weather) {
		return weathertext.equals(weather.weathertext)&&temperature.equals(weather.temperature)&&realfeel.equals(weather.realfeel)&&windgusts.equals(weather.windgusts)&&windspeed.equals(weather.windspeed)&&winddirection.equals(weather.winddirection);
		
	}
	
	

}
