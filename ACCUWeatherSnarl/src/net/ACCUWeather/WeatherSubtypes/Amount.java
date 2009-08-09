package net.ACCUWeather.WeatherSubtypes;


public class Amount {
	UnitCode unitCode;
	double amount;
	
	
	public Amount(String value, UnitCode unitCode) {
		this.amount=Double.valueOf(value);
		this.unitCode=unitCode;
	}
	
	public String toString() {
		return amount+" "+unitCode.amount();
	}
	
	
	public boolean equals(Amount a) {
		return amount==a.amount;
	}

}
