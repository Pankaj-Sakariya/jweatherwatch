package net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Install {

	public static void install() {
		switch (Utils.getOS()) {
		case LINUX:
			installLinux();
			break;
		case WINDOWS:
			installWindows();
			break;

		default:
			break;
		}

	}

	public static void unInstall() {
		switch (Utils.getOS()) {
		case LINUX:
			unInstallLinux();
			break;
		case WINDOWS:
			unInstallWindows();
			break;

		default:
			break;
		}
		SettingsReader.getInstance().removeAutostart();

	}

	private static void unInstallWindows() {
		new File(System.getProperty("user.home") + "/Desktop/jWeatherWatch.url")
				.delete();

		new File(System.getProperty("user.home")
				+ "/Start Menu/jWeatherWatch/jWeatherWatch.url").delete();
		new File(System.getProperty("user.home") + "/Start Menu/jWeatherWatch/")
				.delete();

	}

	private static void unInstallLinux() {
		new File(SettingsReader.getInstance().getCurrentDirectory()
				+ "/jWeatherWatch").delete();
		new File(SettingsReader.getInstance().getCurrentDirectory()
				+ "/jWeatherWatch.desktop").delete();

		new File(System.getProperty("user.home") + "/bin/jWeatherWatch")
				.delete();
		
		new File(System.getProperty("user.home")
		+ "/.local/share/applications/jWeatherWatch.desktop").delete();


	}

	public static void installLinux() {
		PrintWriter out = null;
		File file = new File(SettingsReader.getInstance().getCurrentDirectory()
				+ "/jWeatherWatch");
		try {
			if (file.getParentFile() != null && !file.getParentFile().exists())
				return;
			file.delete();
			out = new PrintWriter(file);
			out
					.print("#!/bin/bash\nstarttime=`date +%s`\necho \"start = $starttime\"\njava -classpath "
							+ SettingsReader.getInstance()
									.getCurrentDirectory()
							+ "JWeatherWatch.jar net.Main \nendtime=`date +%s`\necho \"end = $endtime\"\ntotal=`expr $endtime - $starttime`\n echo \"jWeatherWatch lived $total seconds\"\n");
			out.close();
			Runtime.getRuntime().exec(
					new String[] {
							"chmod",
							"+x",
							SettingsReader.getInstance().getCurrentDirectory()
									+ "jWeatherWatch" });

			file = new File(SettingsReader.getInstance().getCurrentDirectory()
					+ "/jWeatherWatch.desktop");
			file.mkdirs();
			file.delete();
			createDesktopFile(SettingsReader.name,
					"Displays Weather from AccuWeather", SettingsReader
							.getInstance().getCurrentDirectory(),
					SettingsReader.getInstance().getCurrentDirectory()
							+ "iconset/01.png", "Application;Network;", SettingsReader
							.getInstance().getCurrentDirectory()
							+ "/jWeatherWatch.desktop");

			if (new File(System.getProperty("user.home") + "/bin/").exists())
				Runtime.getRuntime().exec(
						new String[] {
								"ln",
								"-fs",
								SettingsReader.getInstance()
										.getCurrentDirectory()
										+ "jWeatherWatch",
								System.getProperty("user.home")
										+ "/bin/jWeatherWatch" });
			Runtime.getRuntime().exec(
					new String[] {
							"ln",
							"-fs",
							SettingsReader.getInstance().getCurrentDirectory()
									+ "jWeatherWatch.desktop",
							System.getProperty("user.home")
									+ "/.local/share/applications/jWeatherWatch.desktop" });

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void installWindows() {
		creatUrlFile(SettingsReader.name, SettingsReader.getInstance()
				.getCurrentDirectory()
				+ "/jWeatherWatch.exe", SettingsReader.getInstance()
				.getCurrentDirectory()
				+ "/jWeatherWatch.exe", System.getProperty("user.home")
				+ "/Desktop/jWeatherWatch");
		new File(System.getProperty("user.home") + "/Start Menu/jWeatherWatch")
				.mkdirs();
		creatUrlFile(SettingsReader.name, SettingsReader.getInstance()
				.getCurrentDirectory()
				+ "/jWeatherWatch.exe", SettingsReader.getInstance()
				.getCurrentDirectory()
				+ "/jWeatherWatch.exe", System.getProperty("user.home")
				+ "/Start Menu/jWeatherWatch/jWeatherWatch");
	}

	public static void creatUrlFile(String programmmName, String target,
			String icon, String dest) {
		if (!dest.endsWith(".url"))
			dest += ".url";

		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(new File(System
					.getProperty("user.home")
					+ "/Desktop/jWeatherWatch.url"));

			printWriter.println("[InternetShortcut]\n" + "URL=\"file://"
					+ target + "\"\n" + "WorkingDirectory=\""
					+ target.substring(target.lastIndexOf("//")) + "\"\n"
					+ "IconIndex=0\n" + "IconFile=\"" + icon + "\"\n"
					+ "IDList=\n" + "HotKey=0\n"
					+ "[{000214A0-0000-0000-C000-000000000046}]\n"
					+ "Prop3=19,9");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			printWriter.close();
		}

	}

	public static void createDesktopFile(String programmmName,
			String description, String target, String icon, String category,
			String dest) {
		PrintWriter out = null;
		if (!dest.endsWith(".desktop"))
			dest += ".desktop";
		if (!category.endsWith(";"))
			dest += ";";
		File file = new File(dest);
		try {
			out = new PrintWriter(file);
			out.println("[Desktop Entry]\n" + "Version=1.0\n"
					+ "Encoding=UTF-8\n" + "Name=" + programmmName + "\n"
					+ "GenericName=" + programmmName + "\n" + "Comment="
					+ description + "\n" + "Exec=" + target + "jWeatherWatch\n"
					+ "Icon=" + icon + "\n" + "StartupNotify=false\n"
					+ "Type=Application\n" + "Categories=" + category);
			out.close();
			Runtime.getRuntime().exec(new String[] { "chmod", "+x", dest });
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
