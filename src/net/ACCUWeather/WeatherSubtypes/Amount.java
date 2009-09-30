package net.ACCUWeather.WeatherSubtypes;


public class Amount {
	float amount;
	
	
	public Amount(String value) {
		this.amount=Float.valueOf(value);

	}
	
	public String toString() {
		return amount+" "+UnitCode.amount();
	}
	
	
	public boolean equals(Amount a) {
		return amount==a.amount;
	}

}
