package net;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

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

import edu.stanford.ejalbert.launching.IBrowserLaunching;
import gui.WeatherView.Views;

public class SettingsReader implements Closeable {
	private static SettingsReader instance = null;
	private boolean portable = false;
	public static final String name = "jWeatherWatch";
	private Version releaseVersion = null;
	private Version devVersion = null;
	public boolean devChannel = false;

	private String homeDirectory = null;
	private String iconpPath = null;
	private boolean autostart = false;

	// Notifer
	public NotiferTypes notifer = null;
	public int notificationInterval = 30;
	// MinimalView Settings
	public int mininimalViewRows = 3;
	public boolean minimalView_Shifted = false;
	public int minimumViewSize = 65;
	// StandartView
	public int standartSelected = 0;

	public String webBrowser = IBrowserLaunching.BROWSER_DEFAULT;

	public Views view = Views.standart;

	public static SettingsReader getInstance() {
		if (instance == null) {
			instance = new SettingsReader();
		}
		return instance;
	}

	private SettingsReader() {
		load();
	}

	private SettingsReader(boolean portable) {
		this.portable = portable;
		System.out.println(getHomeDirectory());
		load();
	}

	private boolean load() {
		if (!new File(getHomeDirectory() + "/settings.xml").exists())
			return false;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		Document doc = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File(getHomeDirectory()
					+ "/settings.xml"));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			JOptionPane.showMessageDialog(null, "Settings are reseted.",
					"Your Settings file is corrupted",
					JOptionPane.ERROR_MESSAGE);
			new File(getHomeDirectory() + "/settings.xml").delete();
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
		if (Utils.getXMLValue(element, "webBrowser") != null)
			webBrowser = Utils.getXMLValue(element, "webBrowser");
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

	public void save() {
		net.myxml.Doc doc = new Doc(SettingsReader.name + "Settings");
		doc.appendNode("host", NotificationConnector.getHost(),
				"Notification Connector Host");
		doc.appendNode("notificationIterval", notificationInterval);
		if (NotificationConnector.getNotifer() != null)
			doc.appendNode("notifer", notifer);
		doc.appendNode("autoStart", autostart, "Autostart true/false");
		doc.appendNode("currentView", view, "The View loaded on start");
		doc.appendNode("iconPath", iconpPath,
				"Icon path used for weather icons");
		doc.appendNode("devChannel", devChannel);
		doc.appendNode("webBrowser", webBrowser);
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

		doc.save(new File(getHomeDirectory() + "/settings.xml"));

	}

	
	public void addtoAutostart() {
		OS os=Utils.getOS();
		if (os== Utils.OS.WINDOWS){
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(
					getHomeDirectory() + "/regme.reg"));

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
							getHomeDirectory() + "/regme.reg" });

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			autostart = false;
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			autostart = false;
			return;
		}
		}
		if(os==OS.LINUX){	
			Install.installLinux();
				try {
					Runtime.getRuntime().exec(new String[]{"ln","-fs",getCurrentDirectory()+"jWeatherWatch.desktop",System.getProperty("user.home")+"/.config/autostart/jWeatherWatch.desktop"});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					autostart = false;
					return;
				}
			
		}
		autostart = true;

	}

	public void removeAutostart() {
		OS os=Utils.getOS();
		if ( os== Utils.OS.WINDOWS) {
			try {
				PrintWriter out = new PrintWriter(new FileOutputStream(
						getHomeDirectory() + "/regme.reg"));

				out.println("Windows Registry Editor Version 5.00");
				out.println();
				out
						.println("[HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Run]");
				out.println("\"jWeatherWatch\"=-");
				out.println();
				out.close();
				Runtime.getRuntime().exec(
						new String[] { "regedit.exe", "/s",
								getHomeDirectory() + "/regme.reg" });

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(os==OS.LINUX){
			File autoStartFile=new File(System.getProperty("user.home")+"/.config/autostart/jWeatherWatch.desktop");
			if(autoStartFile.exists())
				autoStartFile.delete();
		}
		autostart = false;

	}

	public boolean isAutostart() {
		return autostart;
	}

	private void initializeHomeDirectory() {
		switch (Utils.getOS()) {
		case WINDOWS:
			homeDirectory = System.getenv("appdata") + "\\." + name;
			break;
		case LINUX:
			homeDirectory = System.getProperty("user.home") + "/." + name;
			break;
		default:
			System.out.println("Unsopportet OS");
			System.exit(-1);
			break;
		}

	}

	public String getHomeDirectory() {
		if (homeDirectory == null)
			initializeHomeDirectory();
		if (portable)
			homeDirectory = getCurrentDirectory();
		return homeDirectory;
	}

	public String getIconpPath() {
		if (iconpPath == null) {
			setIconpPath(getCurrentDirectory() + "iconset/");
		}
		return iconpPath;
	}

	public void setIconpPath(String iconpPath) {
		iconpPath = iconpPath.replace("\\", "/");
		if (!iconpPath.endsWith("/"))
			iconpPath += "/";
		if (new File(iconpPath + "/01.png").exists()) {
			this.iconpPath = iconpPath;
		}
	}

	public String getCurrentDirectory() {
		String out = new SettingsReader().getClass().getResource("/").getFile()
				.replace("%20", " ");
		if (Utils.getOS() == OS.WINDOWS)
			out = out.replaceFirst("/", "");
		return out;
	}

	public Version getReleaseVersion() {
		if (releaseVersion == null) {
			initializeVersions();
		}
		return releaseVersion;
	}

	public Version getVersion() {
		return devChannel ? getDevversion() : getReleaseVersion();
	}

	public Version getDevversion() {
		if (devVersion == null) {
			initializeVersions();
		}
		return devVersion;
	}

	private void initializeVersions() {
		try {
			releaseVersion = new Version(DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(
							new SettingsReader().getClass().getResource(
									"/CurrentVersion.xml").toString())
					.getElementsByTagName("version").item(0).getChildNodes()
					.item(0).getNodeValue());
			devVersion = new Version(DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(
							new SettingsReader().getClass().getResource(
									"/CurrentVersion.xml").toString())
					.getElementsByTagName("devVersion").item(0).getChildNodes()
					.item(0).getNodeValue());
		} catch (Exception e) {
			System.err.println("Version file is missing");
			releaseVersion = new Version("0");
			devVersion = new Version("0");
			// System.exit(0);
		}

	}

	@Override
	public void close() throws IOException {
		save();

	}

	public void setPortable(boolean portable) {
		instance = new SettingsReader(portable);
	}

	public boolean isPortable() {
		return portable;
	}
}
