package net.ACCUWeather.WeatherSubtypes;

public class Speed {
	int speed;
	UnitCode unitCode;

	public Speed(String value, UnitCode unitCode) {
		this.speed = Integer.valueOf(value);
		this.unitCode = unitCode;
	}

	@Override
	public String toString() {
		return speed + " " + unitCode.speed();
	}
	
	
	public boolean equals(Speed s) {
		return speed==s.speed;
	}
}
