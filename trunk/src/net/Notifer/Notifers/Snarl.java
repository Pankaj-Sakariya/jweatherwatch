package net.Notifer.Notifers;

import gui.Gui;
import net.Notifer.Notifer;
import net.Notifer.NotiferTypes;
import at.dotti.snarl.Snarl4Java;

public class Snarl implements Notifer {

	static boolean initialized = false;
	static {
		try {
			System.loadLibrary("lib/snarl4java");
			initialized = true;
		} catch (UnsatisfiedLinkError e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean laod(String[] notifications) {
		if (!initialized)
			return false;
		long msg = Snarl4Java.snGetGlobalMsg();
		final long hWnd = Snarl4Java.snRegisterConfig(111, Gui.name + " "
				+ Gui.version, 3);
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

	@Override
	public void send(String alert, String title, String description) {
		send(alert, title, description, "");

	}

	@Override
	public NotiferTypes getName() {
		return NotiferTypes.Snarl;
	}

}
