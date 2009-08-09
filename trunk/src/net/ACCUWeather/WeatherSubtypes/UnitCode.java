package net.ACCUWeather.WeatherSubtypes;

public enum UnitCode {
	English(0), Metric(1);
	int val;

	UnitCode(int i) {
		val = i;
	}

	
	public String temperature() {
		switch (this) {
		case Metric:
			return "°C";
		case English :	
			return "°F";
		default:
			return "Error";
		}
	}
	
	public String speed() {
		switch (this) {
		case Metric:
			return "km/h";
		case English :	
			return "miles/h";
		default:
			return "Error";
		}
	}
	public String pressure(){
		switch (this) {
		case Metric:
			return "kPa";
		case English :	
			return "IN";
		default:
			return "Error";
		}
		
	}
	public String amount(){
		switch (this) {
		case Metric:
			return "mm";
		case English :	
			return "IN";
		default:
			return "Error";
		}
	}
	
	public int getVal() {
		return val;
	}
	
}