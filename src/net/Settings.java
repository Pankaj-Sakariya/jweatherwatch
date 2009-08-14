package net;

import gui.Gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.Notifer.Notifer;
import net.Notifer.NotiferTypes;
import net.Notifer.Notifers.KNotify;
import net.Notifer.Notifers.NetGrowl;
import net.Notifer.Notifers.NetSnarl;
import net.Notifer.Notifers.Snarl;
import net.Notifer.Notifers.TrayNotification;
import net.Utils.OS;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Settings {
	public static final String name = "jWeatherWatch";
	public static final String version = "v1.2.5.5 Beta";
	
	private static String homeDirectory;
	private static String workinDirectory =  System.getProperty("user.dir");
	private static boolean autostart=false;

	public static int notificationInterval = 30;

	static public boolean load(Gui parent) {
		if (!new File(Settings.getHomeDirectory() +  "/settings.xml").exists())
			return false;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		Document doc = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File(Settings.getHomeDirectory() + "/settings.xml"));
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

		Element element = ((Element) (doc.getElementsByTagName(""
				+ Settings.name + "Settings").item(0)));

		NotificationConnector.setHost(Utils.getXMLValue(element, "host"));
		Notifer notifer2 = null;

		switch (NotiferTypes.valueOf(Utils.getXMLValue(element, "notifer"))) {
		case Snarl:
			notifer2 = new Snarl();
			break;
		case NetSnarl:
			notifer2 = new NetSnarl();
			break;
		case NetGrowl:
			notifer2 = new NetGrowl();
			break;
		case TrayIcon:
			notifer2 = new TrayNotification(parent.getTrayIcon());
			break;
		case KNotify:
			notifer2 = new KNotify();
			break;
		}

		NotificationConnector.setNotifer(notifer2);
		// setting up notification thread
		notificationInterval = Integer.valueOf(Utils.getXMLValue(element,
				"notificationIterval"));
		if (parent.getNotificationthread() != null)
			parent.getNotificationthread().shutdown();
		if(Utils.getXMLValue(element,
				"autoStart")!=null)
		autostart=Boolean.parseBoolean(Utils.getXMLValue(element,
				"autoStart"));

		return true;
	}

	static public void save() {
		PrintWriter out = null;
		try {
			if (!new File(Settings.getHomeDirectory()).exists())
				new File(Settings.getHomeDirectory()).mkdir();
			out = new PrintWriter(new FileOutputStream(Settings
					.getHomeDirectory() + "/settings.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("<" + Settings.name + "Settings>");
		out.println("<host>" + NotificationConnector.getHost() + "</host>");
		out.println("<notificationIterval>" + notificationInterval
				+ "</notificationIterval>");
		out.println("<notifer>" + NotificationConnector.getNotifer().getName()
				+ "</notifer>");
		out.println("<autoStart>" + autostart	+ "</autoStart>");
		out.println("</" + Settings.name + "Settings>");
		out.close();

	}

	public static String getWorkindirectory() {
		return workinDirectory;
	}

	public static void setWorkinDirectory(String workinDirectory) {
			Settings.workinDirectory = workinDirectory;
	}
	
	
	public static void addtoAutostart(){		
		if(Utils.getOS()!=OS.WINDOWS)return;		
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(Settings.getHomeDirectory()+"\\regme.reg"));
		
		out.println("Windows Registry Editor Version 5.00");
		out.println();
		out.println("[HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Run]");
		out.println("\"jWeatherWatch\"=\"javaw  -jar "+System.getProperty("user.dir").replace("\\","\\\\")+"\\\\JWeatherWatch.jar -workindirectory "+System.getProperty("user.dir").replace("\\","\\\\")+"\\\\ -minimized\"");
		out.println();
		out.close();
		Runtime.getRuntime().exec(new String[]{"regedit.exe","/s", Settings.getHomeDirectory()+"\\regme.reg"});
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		autostart=true;
		
	}
	
	public static void removeAutostart(){
		if(Utils.getOS()!=OS.WINDOWS)return;
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(Settings.getHomeDirectory()+"regme.reg"));
		
		out.println("Windows Registry Editor Version 5.00");
		out.println();
		out.println("[HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Run]");
		out.println("\"jWeatherWatch\"=-");
		out.println();
		out.close();
		Runtime.getRuntime().exec(new String[]{"regedit.exe","/s", Settings.getHomeDirectory()+"regme.reg"});
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		autostart=false;		
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
			homeDirectory = System.getProperty("user.home") + "/."
					+ name;
			break;
		default:
			System.out.println("Unsopportet OS");
			System.exit(-1);
			break;
		}
	
	}

	public static String getHomeDirectory() {
		if(homeDirectory==null)
			initializeHomeDirectory();		
		return homeDirectory;
	}
	
	
	

}
