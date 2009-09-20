package net;

import gui.Gui;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import javax.swing.JOptionPane;

import updater.net.Updater;

public class Main {
	enum options {
		minimized, workindirectory, help, h, version

	}

	public static void main(String[] args) {
		boolean windowstate = true;
		for (int i = 0; i < args.length; ++i) {
			if (args[i].charAt(0) == '-')
				try {
					switch (options.valueOf(args[i].toLowerCase().substring(1))) {
					case minimized:
						windowstate = false;
						break;
					case workindirectory:
						i++;
						break;
					case version:
						System.out.println(SettingsReader.version);
						System.exit(0);
						break;
					case help:
					case h:
					default:
						help(0);
						break;
					}
				} catch (Exception e) {
					help(-1);
				}
		}
		allreadyRunning();
		if (Integer
				.valueOf(Updater.getVersion().replace(".", "")) > Integer
				.valueOf(SettingsReader.version.replace(".", ""))) {
			int result = JOptionPane
					.showConfirmDialog(
							null,
							"A new version of jWeatherWatch is availible,do you want to update?",
							"Update", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				try {

					Updater.copy(SettingsReader.getCurrentDirectory()
							+ "/lib/Updater.jar", System
							.getProperty("java.io.tmpdir")
							+ "/Updater.jar");
					Runtime.getRuntime().exec(
							new String[] {
									"java",
									"-classpath",
									System.getProperty("java.io.tmpdir")
											+ "/Updater.jar",
									"updater.net.Updater",
									SettingsReader.getCurrentDirectory() });
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		}
		new Gui(windowstate);

	}

	private static void allreadyRunning() {
		try {
			File path = new File(SettingsReader.getHomeDirectory());
			File running = new File(path + "/running");
			if (!running.exists()) {
				path.mkdirs();
				running.createNewFile();
			}
			RandomAccessFile randomFile = new RandomAccessFile(running, "rw");

			FileChannel channel = randomFile.getChannel();

			if (channel.tryLock() == null) { // we couldnt acquire lock as it is
				// already locked by another
				// program instance)
				System.out
						.println("An application instance is already running.");
				JOptionPane.showMessageDialog(null,
						"An application instance is already running.", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} catch (IOException e) {
			return;
		}

	}

	private static void help(int i) {
		System.out.println(SettingsReader.name + " [-options]" + "\n-"
				+ options.minimized + " to start the programm minimized\n-"
				+ options.h + "/" + options.help + "\n-"
				+ "iconPath the path where the icons are placed \n-"
				+ " to display this text" + "\n-" + options.version);
		System.exit(i);

	}

}
