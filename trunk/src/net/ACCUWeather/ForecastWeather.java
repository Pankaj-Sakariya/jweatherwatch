package net.ACCUWeather;

import net.Utils;
import net.ACCUWeather.WeatherSubtypes.Amount;
import net.ACCUWeather.WeatherSubtypes.Day;
import net.ACCUWeather.WeatherSubtypes.Speed;
import net.ACCUWeather.WeatherSubtypes.Temperature;
import net.ACCUWeather.WeatherSubtypes.UVIndex;
import net.ACCUWeather.WeatherSubtypes.UnitCode;

import org.w3c.dom.Element;

public class ForecastWeather extends Weather {
	String txtshort;
	Amount rain;
	Amount snow;
	Amount ice;
	
	ForecastDay day;
	int thunderstormProbability;
	Day dayOrNight=null;
	

	public ForecastWeather(Element element, ForecastDay day,Day don,
			UnitCode unitCode) {
		this.url=day.getUrl();
		this.day = day;
		this.dayOrNight=don;
		temperature = new Temperature(Utils.getXMLValue(element,
				"lowtemperature"), Utils
				.getXMLValue(element, "hightemperature"), unitCode);
		realfeel = new Temperature(Utils.getXMLValue(element, "realfeellow"),
				Utils.getXMLValue(element, "realfeelhigh"), unitCode);
		weathertext = Utils.getXMLValue(element, "txtlong");
		txtshort=Utils.getXMLValue(element, "txtshort");
		weathericon = Utils.getXMLValue(element, "weathericon");
		windgusts = Utils.getXMLValue(element, "windgust");
		windspeed = new Speed(Utils.getXMLValue(element, "windspeed"), unitCode);
		winddirection = Utils.getXMLValue(element, "winddirection");

		uvindex = new UVIndex(Utils.getXMLValue(element, "maxuv"));
		rain = new Amount(Utils.getXMLValue(element, "rainamount"), unitCode);
		snow = new Amount(Utils.getXMLValue(element, "snowamount"), unitCode);
		ice = new Amount(Utils.getXMLValue(element, "iceamount"), unitCode);
		precipitation = new Amount(Utils.getXMLValue(element, "precipamount"),
				unitCode);
		thunderstormProbability = Integer.valueOf(Utils.getXMLValue(element,
				"tstormprob"));
//		SnarlConnector.snarl.snShowMessage(
//				"Forecast Weather Notification", getLocation().toString(),
//				this.getSnarlMessage());

	}

	public ForecastDay getDay() {
		return day;
	}

	public boolean equals(ForecastWeather f) {
		return super.equals(f)&&rain.equals(f.rain)&&snow.equals(f.snow)&&ice.equals(f.ice)&&thunderstormProbability==f.thunderstormProbability;
	}
	
	@Override
	public String getNotification() {		
		return day.dayCode+" "+day.date+" "+dayOrNight+"\n"+super.getNotification()+"\nPrecipitation: "+precipitation+"\nThunderstorm Probability: "+thunderstormProbability+"%";
	}
	
	public Amount getRain() {
		return rain;
	}
	public Amount getSnow() {
		return snow;
	}
	public Amount getIce() {
		return ice;
	}
	
	public int getThunderstormProbability() {
		return thunderstormProbability;
	}
	public String getTxtshort() {
		return txtshort;
	}

}
