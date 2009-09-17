package net.Notifer.Notifers;

import net.SettingsReader;
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
	public boolean load(String[] notifications) {
		if (!initialized)
			return false;
		long msg = Snarl4Java.snGetGlobalMsg();
		final long hWnd = Snarl4Java.snRegisterConfig(111, SettingsReader.name + " "
				+ SettingsReader.version, 3);
		System.out.println(msg + " " + hWnd);
		return true;
	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath,String url) {
		Snarl4Java.snShowMessage(title, description, 4, iconPath, 0, 0);

	}

	@Override
	public void unload() {
		Snarl4Java.snRevokeConfig(111);

	}

	@Override
	public void send(String alert, String title, String description,String url) {
		send(alert, title, description, "",null);

	}

	@Override
	public NotiferTypes getName() {
		return NotiferTypes.Snarl;
	}

}
