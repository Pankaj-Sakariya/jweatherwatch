package net;

import gui.WeatherView.Views;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.Notifer.NotiferTypes;
import net.Utils.OS;
import net.myxml.Doc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class SettingsReader {
	public static final String name = "jWeatherWatch";
	public static final Version version = new Version("1.2.8.2");
	private static Version devversion = null;
	public static boolean devChannel = false;

	private static String homeDirectory = null;
	private static String iconpPath = null;
	private static boolean autostart = false;

	// Notifer
	public static NotiferTypes notifer = null;
	public static int notificationInterval = 30;
	// MinimalView Settings
	public static int mininimalViewRows = 3;
	public static boolean minimalView_Shifted = false;
	public static int minimumViewSize = 65;
	// StandartView
	public static int standartSelected = 0;

	public static Views view = Views.standart;

	static {
		load();
	}

	static private boolean load() {
		if (!new File(SettingsReader.getHomeDirectory() + "/settings.xml")
				.exists())
			return false;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		Document doc = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File(SettingsReader.getHomeDirectory()
					+ "/settings.xml"));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			JOptionPane.showMessageDialog(null, "Settings are reseted.",
					"Your Settings file is corrupted",
					JOptionPane.ERROR_MESSAGE);
			new File(SettingsReader.getHomeDirectory() + "/settings.xml")
					.delete();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Element element = ((Element) (doc
				.getElementsByTagName(SettingsReader.name + "Settings").item(0)));

		if (Utils.getXMLValue(element, "host") != null)
			NotificationConnector.setHost(Utils.getXMLValue(element, "host"));

		if (Utils.getXMLValue(element, "notifer") != null)
			notifer = NotiferTypes.valueOf(Utils
					.getXMLValue(element, "notifer"));
		if (iconpPath == null && Utils.getXMLValue(element, "iconPath") != null)
			setIconpPath(Utils.getXMLValue(element, "iconPath"));
		if (Utils.getXMLValue(element, "devChannel") != null)
			devChannel = Boolean.parseBoolean(Utils.getXMLValue(element,
					"devChannel"));
		if (devChannel)
			getDevversion();

		if (Utils.getXMLValue(element, "notificationIterval") != null)
			notificationInterval = Integer.valueOf(Utils.getXMLValue(element,
					"notificationIterval"));
		if (Utils.getXMLValue(element, "autoStart") != null)
			autostart = Boolean.parseBoolean(Utils.getXMLValue(element,
					"autoStart"));
		if (Utils.getXMLValue(element, "currentView") != null)
			view = Views.valueOf(Utils.getXMLValue(element, "currentView"));

		// Minimal View
		Element minimalView = ((Element) (doc
				.getElementsByTagName("minimalView").item(0)));
		if (minimalView != null) {
			mininimalViewRows = Integer.valueOf(Utils.getXMLValue(minimalView,
					"minimalViewRows"));
			minimalView_Shifted = Boolean.valueOf(Utils.getXMLValue(
					minimalView, "minimalViewShifted"));
			if (Utils.getXMLValue(minimalView, "minimumViewSize") != null)
				minimumViewSize = Integer.valueOf(Utils.getXMLValue(
						minimalView, "minimumViewSize"));

		}

		// Standart View
		Element standartView = ((Element) (doc
				.getElementsByTagName("standartView").item(0)));
		if (standartView != null) {
			standartSelected = Integer.valueOf(Utils.getXMLValue(standartView,
					"standartSelected"));
		}
		return true;
	}

	static public void save() {
		net.myxml.Doc doc = new Doc(SettingsReader.name + "Settings");
		doc.appendNode("host", NotificationConnector.getHost(),
				"Notification Connector Host");
		doc.appendNode("notificationIterval", notificationInterval);
		doc.appendNode("notifer", NotificationConnector.getNotifer().getName());
		doc.appendNode("autoStart", autostart, "Autostart true/false");
		doc.appendNode("currentView", view, "The View loaded on start");
		doc.appendNode("iconPath", iconpPath,
				"Icon path used for weather icons");
		doc.appendNode("devChannel", devChannel);
		net.myxml.Doc minimalView = new Doc("minimalView");
		minimalView.appendNode("minimalViewRows", mininimalViewRows,
				"Number of Rows displayed in Minimal View");
		minimalView.appendNode("minimalViewShifted", minimalView_Shifted);
		minimalView.appendNode("minimumViewSize", minimumViewSize,
				"The size of an minimalpanel");
		doc.addDoc(minimalView);

		net.myxml.Doc standartView = new Doc("standartView");
		standartView.appendNode("standartSelected", standartSelected,
				"Slelected location in standart view");
		doc.addDoc(standartView);

		doc.save(new File(SettingsReader.getHomeDirectory() + "/settings.xml"));

	}

	public static void addtoAutostart() {
		if (Utils.getOS() != Utils.OS.WINDOWS)
			return;
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(
					SettingsReader.getHomeDirectory() + "/regme.reg"));

			out.println("Windows Registry Editor Version 5.00");
			out.println();
			out
					.println("[HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Run]");
			out.println("\"jWeatherWatch\"=\"\\\""
					+ getCurrentDirectory().replace("/", "\\\\")
					+ "jWeatherWatch.exe\\\" -minimized\"");
			out.println();
			out.close();
			Runtime.getRuntime().exec(
					new String[] { "regedit.exe", "/s",
							SettingsReader.getHomeDirectory() + "/regme.reg" });

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		autostart = true;

	}

	public static void removeAutostart() {
		if (Utils.getOS() != Utils.OS.WINDOWS)
			return;
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(
					SettingsReader.getHomeDirectory() + "/regme.reg"));

			out.println("Windows Registry Editor Version 5.00");
			out.println();
			out
					.println("[HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Run]");
			out.println("\"jWeatherWatch\"=-");
			out.println();
			out.close();
			Runtime.getRuntime().exec(
					new String[] { "regedit.exe", "/s",
							SettingsReader.getHomeDirectory() + "/regme.reg" });

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		autostart = false;
	}

	public static boolean isAutostart() {
		return autostart;
	}

	private static void initializeHomeDirectory() {
		switch (Utils.getOS()) {
		case WINDOWS:
			homeDirectory = System.getenv("appdata") + "\\." + name;
			break;
		case LINUX:
		case MAC:
			homeDirectory = System.getProperty("user.home") + "/." + name;
			break;
		default:
			System.out.println("Unsopportet OS");
			System.exit(-1);
			break;
		}

	}

	public static String getHomeDirectory() {
		if (homeDirectory == null)
			initializeHomeDirectory();
		return homeDirectory;
	}

	public static String getIconpPath() {
		if (iconpPath == null) {
			setIconpPath(getCurrentDirectory() + "iconset/");
		}
		return iconpPath;
	}

	public static void setIconpPath(String iconpPath) {
		iconpPath = iconpPath.replace("\\", "/");
		if (!iconpPath.endsWith("/"))
			iconpPath += "/";
		if (new File(iconpPath + "/01.png").exists()) {
			SettingsReader.iconpPath = iconpPath;
		}
	}

	public static String getCurrentDirectory() {
		String out = new SettingsReader().getClass().getResource("/").getFile()
				.replace("%20", " ");
		if (Utils.getOS() == OS.WINDOWS)
			out = out.replaceFirst("/", "");
		return out;
	}

	public static Version getVersion() {
		return devversion != null ? devversion : version;
	}

	public static Version getDevversion() {
		if (devversion == null) {
			devversion = new Version(new SimpleDateFormat("MMddyy")
					.format(new Date()));

		}
		return devversion;
	}
}
