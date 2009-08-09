package net.ACCUWeather;



import lib.Utils;
import net.ACCUWeather.WeatherSubtypes.Day;
import net.ACCUWeather.WeatherSubtypes.UnitCode;

import org.w3c.dom.Element;

public class ForecastDay  {
	String date;
	String dayCode;
	String sunrise;
	String sunset;
	ForecastWeather day;
	ForecastWeather night;
	String url;
	int dayNR;

	public ForecastDay(Element element,int i, UnitCode unitCode) {
		dayNR=i;
		date = Utils.getXMLValue(element, "obsdate");
		dayCode = Utils.getXMLValue(element, "daycode");
		sunrise = Utils.getXMLValue(element, "sunrise");
		sunset = Utils.getXMLValue(element, "sunset");
		url = Utils.getXMLValue(element, "url");
		day=new ForecastWeather((Element)element.getElementsByTagName("daytime").item(0), this,Day.Day, unitCode);
		night=new ForecastWeather((Element)element.getElementsByTagName("nighttime").item(0), this,Day.Night,unitCode);

	}
	
	public ForecastWeather getDayNight(Day d){
		if(d==Day.Day)
			return day;
		return night;		
	}
	public ForecastWeather getDay() {
		return day;
	}
	public ForecastWeather getNight() {
		return night;
	}
	public String getDayCode() {
		return dayCode;
	}
	public String getDate() {
		return date;
	}
	public String getUrl() {
		return url;
	}
	public boolean equals(ForecastDay f) {
		return day.equals(f.day)&&night.equals(f.night);
	}
	public int getDayNR() {
		return dayNR;
	}


}
