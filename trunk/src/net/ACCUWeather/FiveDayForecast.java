package net.ACCUWeather;

import org.w3c.dom.Element;

public class FiveDayForecast {
	ForecastDay forecast[] = new ForecastDay[5];

	public FiveDayForecast(Element element) {
		for (int i = 0; i < 5; ++i) {
			forecast[i] = new ForecastDay((Element) element
					.getElementsByTagName("day").item(i),i+1);
		}
	}

	public ForecastDay getDay(int nr) {
		return forecast[nr-1];
	}

	public boolean equals(FiveDayForecast f) {
		for (int i = 0; i < 5; ++i) {
			if (!forecast[i].equals(f.forecast[i]))
				return false;
		}
		return true;

	}

}
