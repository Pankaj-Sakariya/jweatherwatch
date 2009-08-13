package net;

import gui.Gui;

import java.awt.Frame;

public class Main {
	enum options {
		minimized, workindirectory, help, h, version

	}

	public static void main(String[] args) {
		int windowstate = Frame.NORMAL;
		for (int i = 0; i < args.length; ++i) {
			if (args[i].charAt(0) == '-')
				try{
				switch (options.valueOf(args[i].toLowerCase().substring(1))) {
				case minimized:
					windowstate = Frame.ICONIFIED;
					break;
				case workindirectory:
					if (i + 1 < args.length)
						Settings.setWorkinDirectory(args[++i]);
					else{
						System.out.println("Illegal arguments");
						help(-1);
					}
					break;
				case version:
					System.out.println(Settings.name + " " + Settings.version);
					System.exit(0);
					break;
				case help:
				case h:
				default:
					help(0);
					break;
				}
				}catch (Exception e) {
					help(-1);
				};
		}
		new Gui(windowstate);

	}
	
	private static void help(int i){
		System.out
		.println(Settings.name + " [-options]" + "\n-"
				+ options.minimized + "\n-"
				+ options.workindirectory + " path" + "\n-"
				+ options.h + "/" + options.help
				+ " to display this text" + "\n-"
				+ options.version);
			System.exit(i);
		
	}

}
