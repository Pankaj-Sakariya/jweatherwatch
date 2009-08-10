package net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.ACCUWeather.CurrentWeather;
import net.ACCUWeather.FiveDayForecast;
import net.ACCUWeather.Location;
import net.ACCUWeather.LocationList;
import net.ACCUWeather.WeatherSubtypes.UnitCode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ACCUWeatherFetcher {
	private static String baseURL = "http://vonreth.accu-weather.com/widget/vonreth/";
	private static UnitCode unitCode = UnitCode.English;
	private static String homeDirectory;

	static {
		initializeHomeDirectory();
	}

	public static LocationList search(String location) {
		Document doc = getDocument(baseURL + "city-find.asp?location="
				+ location);
		return new LocationList(getXMLElements((Element) doc
				.getElementsByTagName("adc_database").item(0), "location"));
	}

	public static CurrentWeather getCurrentWeather(Location location) {
		Document doc = getDocument(baseURL + "weather-data.asp?location="
				+ location.getLocation() + "&metric=" + unitCode.getVal());
		location.setTimezone(doc);
		return new CurrentWeather((Element) doc.getElementsByTagName(
				"currentconditions").item(0), unitCode);

	}

	public static FiveDayForecast getFiveDayDorecast(Location location) {
		Document doc = getDocument(baseURL + "weather-data.asp?location="
				+ location.getLocation() + "&metric=" + unitCode.getVal());
		location.setTimezone(doc);
		return new FiveDayForecast((Element) doc.getElementsByTagName(
				"forecast").item(0), unitCode);
	}

	private static Document getDocument(String url) {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(url);
			return doc;
		} catch (FileNotFoundException e) {
			System.out.println("File: " + url + " not found, retrying");
			return getDocument(url);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("URL= " + url);
		return null;

	}

	public static UnitCode getUnit_code() {
		return unitCode;
	}

	public static void setUnitCode(UnitCode unitCode) {
		ACCUWeatherFetcher.unitCode = unitCode;
	}

	public static void save(LocationList locationList) {
		PrintWriter out = null;
		try {
			if (!new File(homeDirectory + "/.SnarlWeatherWatch").exists())
				new File(homeDirectory + "/.SnarlWeatherWatch").mkdir();
			out = new PrintWriter(new FileOutputStream(homeDirectory
					+ "/.SnarlWeatherWatch/profile.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("<SnarlWeatherWatchProfile>");
		out.println("<unitCode>" + unitCode + "</unitCode>");
		out.println(locationList.toXML());
		out.println("</SnarlWeatherWatchProfile>");
		out.close();
	}

	public static LocationList load() {
		if (!new File(homeDirectory + "/.SnarlWeatherWatch/profile.xml")
				.exists())
			return new LocationList();
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		Document doc = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(homeDirectory
					+ "/.SnarlWeatherWatch/profile.xml");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (doc.getElementsByTagName("unitCode").item(0) != null)
			unitCode = UnitCode.valueOf(Utils.getXMLValue(
					((Element) (doc
							.getElementsByTagName("SnarlWeatherWatchProfile")
							.item(0))), "unitCode"));
		LocationList locationList = null;
		if (doc.getElementsByTagName("locations").item(0) != null) {
			Element locations = (Element) doc.getElementsByTagName("locations")
					.item(0).getChildNodes();
			locationList = new LocationList(getXMLElements(locations,
					"location"));
		}
		return locationList;
	}

	private static void initializeHomeDirectory() {
		switch (Utils.getOS()) {
		case WINDOWS:
			homeDirectory = System.getenv("appdata");
			break;
		case LINUX:			
		case MAC:
			homeDirectory = System.getProperty("user.home");
			break;
		default:
			System.out.println("Unsopportet OS");
			System.exit(-1);
			break;
		}

			
	}

	private static Element[] getXMLElements(Element doc, String tag) {
		NodeList nodes = doc.getElementsByTagName(tag);
		Element elements[] = new Element[nodes.getLength()];
		for (int i = 0; i < nodes.getLength(); ++i) {
			elements[i] = (Element) nodes.item(i);
		}
		return elements;
	}

}
