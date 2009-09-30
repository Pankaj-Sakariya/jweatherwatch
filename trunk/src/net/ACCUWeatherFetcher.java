package net;

import gui.Splash;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.ACCUWeather.Location;
import net.ACCUWeather.LocationList;
import net.ACCUWeather.LocationListUser;
import net.ACCUWeather.WeatherSubtypes.UnitCode;
import net.myxml.Doc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ACCUWeatherFetcher {
	public static String baseURL = "http://vonreth.accu-weather.com/widget/vonreth/";


	public static LocationList search(String location) {
		Document doc = getDocument(baseURL + "city-find.asp?location="
				+ location);
		return new LocationList(null, getXMLElements((Element) doc
				.getElementsByTagName("adc_database").item(0), "location"));
	}

	public static Document getDocument(String url) {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(url);
		} catch (FileNotFoundException e) {
			System.out.println("File: " + url + " not found, retrying");
			return getDocument(url);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {

//			if (url.contains("weather-data.asp")) {
//				Document d = getOfflineDocument(SettingsReader.getInstance()
//						.getHomeDirectory()
//						+ "/offline/"
//						+ url
//								.substring(
//										url.indexOf("location=")
//												+ "location=".length(),
//										url.indexOf("&")).replace("|", "-")
//						+ ".xml");
//				if (d != null)
//					return d;
//			}

			System.out
					.println("There is a problem with zour Internet connection.\n jWeatherWatch exits now.");

			JOptionPane
					.showMessageDialog(
							null,
							"There is a problem with your Internet connection.\n jWeatherWatch exits now.",
							"Connection error", JOptionPane.ERROR_MESSAGE);

			System.exit(-1);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("URL= " + url);
		return null;
	}

	private static Document getOfflineDocument(String url) {
		System.out.println ("Lodaing offline data");
		try {
			DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					new File(url));
		} catch (FileNotFoundException e) {
			System.out.println("File: " + url + " not found, retrying");
			return null;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}




	public static void save(LocationList locationList) {
		net.myxml.Doc doc = new Doc(SettingsReader.name + "Profile");

		doc.appendNode("unitCode", UnitCode.stringValue(),
				"The UnitCcode English/Metric");
		doc.addDoc(locationList.toXML());
		doc.save(new File(SettingsReader.getInstance().getHomeDirectory() + "/profile.xml"));
	}

	public static LocationList load(LocationListUser listUser) {
		if (!new File(SettingsReader.getInstance().getHomeDirectory() + "/profile.xml")
				.exists())
			return new LocationList();
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		Document doc = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File(SettingsReader.getInstance().getHomeDirectory()
					+ "/profile.xml"));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			JOptionPane
					.showMessageDialog(null, "Locations reseted.",
							"Your Location List iscorrupted",
							JOptionPane.ERROR_MESSAGE);
			new File(SettingsReader.getInstance().getHomeDirectory() + "/profile.xml")
					.delete();
			return new LocationList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (doc.getElementsByTagName("unitCode").item(0) != null)
			UnitCode.getByName(Utils.getXMLValue(((Element) (doc
					.getElementsByTagName("" + SettingsReader.name + "Profile")
					.item(0))), "unitCode"));
		LocationList locationList = null;
		if (doc.getElementsByTagName("locations").item(0) != null) {
			Element locations = (Element) doc.getElementsByTagName("locations")
					.item(0).getChildNodes();
			locationList = new LocationList(listUser, getXMLElements(locations,
					"location"));
		}
		return locationList;

	}

	private static Element[] getXMLElements(Element doc, String tag) {
		NodeList nodes = doc.getElementsByTagName(tag);
		Element elements[] = new Element[nodes.getLength()];
		for (int i = 0; i < nodes.getLength(); ++i) {
			elements[i] = (Element) nodes.item(i);
		}
		return elements;
	}

	public static void saveOfflineData(LocationList locationList, Splash splash) {

		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;

		URL url;

		for (Location l : locationList) {
			splash.setLoadingText("Saving offline weather data of: " + l);
			try {
				new File(SettingsReader.getInstance().getHomeDirectory() + "/offline/")
						.mkdirs();
				File outfile = new File(SettingsReader.getInstance().getHomeDirectory()
						+ "/offline/tmp");
				url = new URL(baseURL + "weather-data.asp?location="
						+ l.getLocation() + "&metric=" + UnitCode.getVal());

				out = new BufferedOutputStream(new FileOutputStream(outfile));
				conn = url.openConnection();

				in = conn.getInputStream();
				byte[] buffer = new byte[1024];
				int numRead;
				while ((numRead = in.read(buffer)) != -1) {
					out.write(buffer, 0, numRead);
				}
				out.close();
				new File(SettingsReader.getInstance().getHomeDirectory() + "/offline/"
						+ l.getLocation().replace("|", "-") + ".xml").delete();
				outfile.renameTo(new File(SettingsReader.getInstance().getHomeDirectory()
						+ "/offline/" + l.getLocation().replace("|", "-")
						+ ".xml"));
			}catch(ConnectException e){
				return;
			}			
			catch (UnknownHostException e) {
				return;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}

	}

}
