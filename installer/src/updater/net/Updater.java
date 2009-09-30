package updater.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Updater {

	private static String version = null;
	private static String devVersion = null;

	public static String getVersion() {
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
			devVersion = doc.getElementsByTagName("devVersion").item(0)
					.getChildNodes().item(0).getNodeValue();
		}
		return version;
	}

	public static String getDevVersion() {
		if (devVersion == null)
			getVersion();
		return devVersion;
	}

	public static boolean update(String destination, boolean developementversion) {
		String adress = developementversion ? "http://jweatherwatch.googlecode.com/svn/trunk/dev/jWeatherWatch%20"
				+ getDevVersion() + ".zip"
				: "http://jweatherwatch.googlecode.com/files/jWeatherWatch%20v"
						+ getVersion() + ".zip";
		File download = Updater.download(adress);
		try {
			ZipArchiveExtractor.extract(download.toString(), destination + "/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public static File download(String adress) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;
		URL url;
		File outfile = null;

		try {
			outfile = new File(System.getProperty("java.io.tmpdir") + "/"
					+ adress.substring(adress.lastIndexOf("/")));
			System.out.println(outfile);
			url = new URL((adress));
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

	public static void main(String[] args) {
		if (args.length != 2)
			return;
		boolean dev = Boolean.parseBoolean(args[1]);
		update(args[0], dev);
		try {
			Runtime.getRuntime().exec(
					new String[] { System.getProperty("java.home")+"/bin/java", "-classpath",
							"\""+args[0] + "/JWeatherWatch.jar\"","net.Main",
							dev ? "-dev" : "-nodev" });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Found at http://www.java2s.com/Code/Java/File-Input-Output/
	 * CopyfilesusingJavaIOAPI.htm
	 * 
	 * @param fromFileName
	 * @param toFileName
	 * @throws IOException
	 */
	public static void copy(String fromFileName, String toFileName,
			boolean force) throws IOException {
		File fromFile = new File(fromFileName);
		File toFile = new File(toFileName);

		if (!fromFile.exists())
			throw new IOException("FileCopy: " + "no such source file: "
					+ fromFileName);
		if (!fromFile.isFile())
			throw new IOException("FileCopy: " + "can't copy directory: "
					+ fromFileName);
		if (!fromFile.canRead())
			throw new IOException("FileCopy: " + "source file is unreadable: "
					+ fromFileName);

		if (toFile.isDirectory())
			toFile = new File(toFile, fromFile.getName());

		if (!force && toFile.exists()) {
			if (!toFile.canWrite())
				throw new IOException("FileCopy: "
						+ "destination file is unwriteable: " + toFileName);
			System.out.print("Overwrite existing file " + toFile.getName()
					+ "? (Y/N): ");
			System.out.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			String response = in.readLine();
			if (!response.equals("Y") && !response.equals("y"))
				throw new IOException("FileCopy: "
						+ "existing file was not overwritten.");
		} else {
			String parent = toFile.getParent();
			if (parent == null)
				parent = System.getProperty("user.dir");
			File dir = new File(parent);
			if (!dir.exists())
				throw new IOException("FileCopy: "
						+ "destination directory doesn't exist: " + parent);
			if (dir.isFile())
				throw new IOException("FileCopy: "
						+ "destination is not a directory: " + parent);
			if (!dir.canWrite())
				throw new IOException("FileCopy: "
						+ "destination directory is unwriteable: " + parent);
		}

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					;
				}
			if (to != null)
				try {
					to.close();
				} catch (IOException e) {
					;
				}
		}
	}

	public static void creatLnk(String location) {
		String o = System.getProperty("os.name").toLowerCase();
		if (o.contains("windows")) {

			PrintWriter printWriter = null;
			try {
				printWriter = new PrintWriter(new File(System
						.getProperty("user.home")
						+ "/Desktop/jWeatherWatch.url"));

				printWriter.println("[InternetShortcut]\n" + "URL=\"file://"
						+ location + "\\jWeatherWatch.exe\"\n"
						+ "WorkingDirectory=\"" + location + "\"\n"
						+ "IconIndex=0\n" + "IconFile=\"" + location
						+ "\\jWeatherWatch.exe\"\n" + "IDList=\n"
						+ "HotKey=0\n"
						+ "[{000214A0-0000-0000-C000-000000000046}]\n"
						+ "Prop3=19,9");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				printWriter.close();
			}

		}

	}
}
