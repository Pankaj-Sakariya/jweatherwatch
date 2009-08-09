package net.ACCUWeather.WeatherSubtypes;



public class Temperature {
	int temperature;
	int maxTemperature=-999;
	UnitCode unitCode;

	
	public Temperature(String value, UnitCode unitCode) {
			this.temperature=Integer.valueOf(value);
			this.unitCode=unitCode;			
	}
	
	public Temperature(String low,String max, UnitCode unitCode) {
		this.temperature=Integer.valueOf(low);
		this.maxTemperature=Integer.valueOf(max);
		this.unitCode=unitCode;			
}

	@Override
	public String toString() {
		if(maxTemperature!=-999)
			return temperature+unitCode.temperature()+"/"+maxTemperature+unitCode.temperature();
		return temperature+""+unitCode.temperature();
	}
	
	
	public boolean equals(Temperature t) {
		return temperature==t.temperature&&maxTemperature==t.maxTemperature;	
	}
	

}
