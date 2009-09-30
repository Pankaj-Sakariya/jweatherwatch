package net.ACCUWeather;

import net.Utils;
import net.ACCUWeather.WeatherSubtypes.Amount;
import net.ACCUWeather.WeatherSubtypes.Pressure;
import net.ACCUWeather.WeatherSubtypes.Speed;
import net.ACCUWeather.WeatherSubtypes.Temperature;
import net.ACCUWeather.WeatherSubtypes.UVIndex;

import org.w3c.dom.Element;

public class CurrentWeather extends Weather {

	
	Pressure pressure;
	String humidity;
	String visibility;
	String dewpoint;
	String cloudcover;
	String apparenttemp;
	String windchill;
	

	public CurrentWeather(Element element) {
		url = Utils.getXMLValue(element, "url");
		pressure = new Pressure(element);
		temperature = new Temperature(Utils.getXMLValue(element, "temperature"));
		realfeel = new Temperature(Utils.getXMLValue(element, "realfeel"));
		humidity = Utils.getXMLValue(element, "humidity");
		weathertext = Utils.getXMLValue(element, "weathertext");
		weathericon = Utils.getXMLValue(element, "weathericon");
		windgusts = Utils.getXMLValue(element, "windgusts");
		windspeed = new Speed(Utils.getXMLValue(element, "windspeed"));
		winddirection = Utils.getXMLValue(element, "winddirection");
		visibility = Utils.getXMLValue(element, "visibility");
		precipitation = new Amount(Utils.getXMLValue(element, "precip"));
		uvindex = new UVIndex(element);
		dewpoint = Utils.getXMLValue(element, "dewpoint");
		cloudcover = Utils.getXMLValue(element, "cloudcover");
		apparenttemp = Utils.getXMLValue(element, "apparenttemp");
		windchill = Utils.getXMLValue(element, "windchill");
	
	}

	

	public Pressure getPressure() {
		return pressure;
	}

	public String getHumidity() {
		return humidity;
	}


	

	
	public boolean equals(CurrentWeather currentWeather) {
		return super.equals(currentWeather)&&pressure.equals(currentWeather.pressure)&&humidity.equals(currentWeather.humidity)&&visibility.equals(visibility)&&dewpoint.equals(currentWeather.dewpoint)&&cloudcover.equals(currentWeather.cloudcover )&&apparenttemp.equals(currentWeather.apparenttemp)&&windchill.equals(currentWeather.windchill);
	}
	



}
