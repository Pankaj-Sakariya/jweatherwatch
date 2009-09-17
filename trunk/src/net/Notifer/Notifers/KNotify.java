package net.Notifer.Notifers;

import java.io.File;
import java.io.IOException;

import net.Notifer.Notifer;
import net.Notifer.NotiferTypes;

public class KNotify implements Notifer {

	private Runtime runtime = null;

	@Override
	public NotiferTypes getName() {
		return NotiferTypes.KNotify;
	}

	@Override
	public boolean load(String[] notifications) {
		runtime = Runtime.getRuntime();
		return new File("/usr/bin/kdialog").exists();
	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath,String url) {
		try {
			runtime.exec(new String[]{"kdialog", "--title",  title,"--passivepopup",description ,"10"});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void send(String alert, String title, String description,String url) {
		send(alert, title, description, null);
	}

	@Override
	public void unload() {
		// TODO Auto-generated method stub

	}

}
