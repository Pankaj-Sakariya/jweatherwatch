package net.ACCUWeather.WeatherSubtypes;

public class Speed {
	byte speed;

	public Speed(String value) {
		this.speed = Byte.valueOf(value);

	}

	@Override
	public String toString() {
		return speed + " " + UnitCode.speed();
	}

	public boolean equals(Speed s) {
		return speed == s.speed;
	}
}
