package net.Notifer.Notifers;

import gui.Gui;
import net.Notifer.Notifer;
import at.dotti.snarl.Snarl4Java;

public class Snarl implements Notifer {

	@Override
	public boolean laod() {
		try {
			System.loadLibrary("lib/snarl4java");
		} catch (UnsatisfiedLinkError e) {
			System.out.println(e.getMessage());
			return false;
		}
		long msg = Snarl4Java.snGetGlobalMsg();
		final long hWnd = Snarl4Java.snRegisterConfig(111, Gui.name+" "+Gui.version, 3);
		System.out.println(msg + " " + hWnd);
		return true;
	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath) {
		Snarl4Java.snShowMessage(title, description, 4, iconPath, 0, 0);

	}

	@Override
	public void unload() {
		Snarl4Java.snRevokeConfig(111);

	}

}
