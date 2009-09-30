package net;

import gui.Gui;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import it.sauronsoftware.junique.MessageHandler;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import updater.net.Updater;

public class Main {
	public static LinkedList<Closeable> thingsToClose = new LinkedList<Closeable>();

	enum options {
		minimized, workindirectory, help, h, version, tofront, dev, nodev, portable
	}

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread("jWeatherWatchDispose"){
			@Override
			public void run() {
				close();
			}
		});
		boolean windowstate = parseArgs(args);
		allreadyRunning(args);

		Version compare = new Version(Updater.getVersion());
		if (SettingsReader.getInstance().devChannel) {
			JOptionPane
					.showMessageDialog(
							null,
							"This is a beta version please report all occuring errors.",
							SettingsReader.name
									+ SettingsReader.getInstance().getVersion(),
							JOptionPane.INFORMATION_MESSAGE);
			compare = new Version(Updater.getDevVersion());
		}

		if (compare.compareTo(SettingsReader.getInstance().getVersion()) > 0) {
			update();
		}
		new Gui(windowstate);

	}

	private static boolean parseArgs(String[] args) {
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
						System.out.println(SettingsReader.name + " "
								+ SettingsReader.getInstance().getVersion());
						System.exit(0);
						break;
					case tofront:
						NotificationConnector.bringFrameToFront();
						break;
					case dev:
						SettingsReader.getInstance().devChannel = true;
						break;
					case nodev:
						SettingsReader.getInstance().devChannel = false;
						break;
					case portable:
						SettingsReader.getInstance().setPortable(true);
						break;
					case help:
					case h:
					default:
						help(0);
						break;
					}

				} catch (EnumConstantNotPresentException e) {
					System.out.println("Unknown kommand: " + args[i]);
					help(-1);
				}
		}
		return windowstate;

	}

	public static void update() {
		String version = SettingsReader.getInstance().devChannel ? Updater
				.getDevVersion().toString() : Updater.getVersion().toString();
		int result = JOptionPane
				.showConfirmDialog(
						null,
						"A new version of jWeatherWatch is availible,do you want to update?",
						"Update to " + version, JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.NO_OPTION)
			return;
		JOptionPane.showMessageDialog(null,
				"jWeather Watch whil now quit and update to version " + version
						+ " this will take some moments", "Update Started",
				JOptionPane.INFORMATION_MESSAGE);
		try {
			Updater.copy(SettingsReader.getInstance().getCurrentDirectory()
					+ "/lib/Updater.jar", System.getProperty("java.io.tmpdir")
					+ "/Updater.jar", true);
			Runtime.getRuntime().exec(
					new String[] {
							System.getProperty("java.home")+"/bin/java",
							"-classpath",
							System.getProperty("java.io.tmpdir")
									+ "/Updater.jar", "updater.net.Updater",
							SettingsReader.getInstance().getCurrentDirectory(),
							SettingsReader.getInstance().devChannel + "" });
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		close();

	}

	private static boolean allreadyRunning(String[] args) {
		boolean alreadyRunning;
		try {
			JUnique.acquireLock(SettingsReader.name, new MessageHandler() {

				@Override
				public String handle(String arg0) {
					parseArgs(new String[] { arg0 });
					return null;
				}
			});
			alreadyRunning = false;
		} catch (AlreadyLockedException e) {
			alreadyRunning = true;
		}
		if (!alreadyRunning)
			return false;
		JUnique.sendMessage(SettingsReader.name, "-" + options.tofront);
		System.exit(0);
		return true;
	}

	public static void close() {
		System.out.println("Saving....");
		try {
			for (Closeable c : thingsToClose) {
				System.out.println("Closing: " + c.getClass());
				c.close();
			}
			System.out.println("Closing: "
					+ SettingsReader.getInstance().getClass());
			SettingsReader.getInstance().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.exit(0);

	}

	private static void help(int i) {
		System.out
				.println(SettingsReader.name
						+ " [-options]"
						+ "\n-"
						+ options.minimized
						+ " to start the programm minimized\n-"
						+ options.portable
						+ " to save settings files in the directory of jWeatherWatch\n-"
						+ options.h + "/" + options.help
						+ " to display this text" + "\n-" + options.version);
		System.exit(i);

	}
}
