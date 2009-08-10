package net.Notifer.Notifers;

import gui.Gui;
import net.Notifer.Notifer;

import com.growl.GrowlWrapper;

public class Growl implements Notifer {
	GrowlWrapper growlWrapper=null;

	@Override
	public boolean laod() {
		growlWrapper=new GrowlWrapper(Gui.name+" "+Gui.version, System.getProperty("user.dir")+"/iconset/"+"01.png",new String[]{"Current Weather Notification","Forecast Weather Notification"}, new String[]{"defaultNotifications"});
		return growlWrapper.getState()==GrowlWrapper.GROWL_OK;
	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath) {
		growlWrapper.notify(alert, title, description);
		
	}

	@Override
	public void unload() {
		// TODO Auto-generated method stub
		
	}

}
