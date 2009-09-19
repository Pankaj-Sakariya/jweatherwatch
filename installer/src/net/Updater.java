package net;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Updater {

	private static String version = null;

	public static String getVerion() {
		if (version == null) {
			org.w3c.dom.Document doc = null;
			try {
				doc = DocumentBuilderFactory
						.newInstance()
						.newDocumentBuilder()
						.parse(
								"http://jweatherwatch.googlecode.com/svn/trunk/CurrentVersion.xml");
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
			version = doc.getElementsByTagName("version").item(0)
					.getChildNodes().item(0).getNodeValue();
			System.out.println(version);
		}
		return version;
	}

	public static boolean update(String destination){
		File download=Updater.download(version);
		try {
			ZipArchiveExtractor.extract(download.toString(), destination+"/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		
		
	}
	public static File download(String version) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;
		URL url;
		File outfile = null;

		try {
			outfile = new File(System.getProperty("java.io.tmpdir") + "/"
					+ version + ".zip");
			System.out.println(outfile);
			url = new URL(("http://jweatherwatch.googlecode.com/files/"
					+ version + ".zip").replace(" ", "%20"));
			System.out.println(url);
			out = new BufferedOutputStream(new FileOutputStream(outfile));
			conn = url.openConnection();

			in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outfile;
	}

}
