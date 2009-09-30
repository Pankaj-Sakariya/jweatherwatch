package net.ACCUWeather.WeatherSubtypes;

public class UnitCode {

	public enum UnitCodes {
		English(0), Metric(1);
		int val;

		UnitCodes(int i) {
			val = i;
		}
	}

	
	private static UnitCodes unitCode = UnitCodes.English;

	public static void getByName(String s){
		unitCode=UnitCodes.valueOf(s);
	}
	public static void setUnitCode(UnitCodes unitCode) {
		UnitCode.unitCode = unitCode;
	}

	public static UnitCodes getUnitCode() {
		return unitCode;
	}

	public static String temperature() {
		switch (unitCode) {
		case Metric:
			return "\u00B0C";
		case English:
			return "\u00B0F";
		default:
			return "Error";
		}
	}

	public static String speed() {
		switch (unitCode) {
		case Metric:
			return "km/h";
		case English:
			return "miles/h";
		default:
			return "Error";
		}
	}

	public static String pressure() {
		switch (unitCode) {
		case Metric:
			return "kPa";
		case English:
			return "IN";
		default:
			return "Error";
		}

	}

	public static String amount() {
		switch (unitCode) {
		case Metric:
			return "mm";
		case English:
			return "IN";
		default:
			return "Error";
		}
	}

	public static int getVal() {
		return unitCode.val;
	}
	
	public static String stringValue() {
		return  unitCode.toString();
	}

}
