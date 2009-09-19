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

public class Installer {

	public static boolean download(String version) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;
		URL url;
		try {
			File outfile = new File(System.getProperty("java.io.tmpdir")
					+ "/version");
			url = new URL("http://jweatherwatch.googlecode.com/files/"
					+ version + ".zip");

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
			return false;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
