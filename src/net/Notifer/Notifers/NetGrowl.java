package net.Notifer.Notifers;

import gui.Gui;
import net.Notifer.NetNotifer;
import net.Notifer.NotiferTypes;
import net.sf.libgrowl.Application;
import net.sf.libgrowl.GrowlConnector;
import net.sf.libgrowl.Notification;
import net.sf.libgrowl.NotificationType;

public class NetGrowl implements NetNotifer {
	GrowlConnector growlConnector = null;
	Application application = null;
	NotificationType notificationTypes[] = null;
	String host=null;

	@Override
	public boolean laod(String[] notifications) {
	
		return load(notifications,"localhost");

	}
	public boolean load(String[] notifications, String host){
		this.host=host;	
		growlConnector = new GrowlConnector(host);
		application = new Application(Gui.name + " " + Gui.version, System
				.getProperty("user.dir")
				+ "/iconset/01.png");
		notificationTypes = new NotificationType[notifications.length];
		for (int i = 0; i < notifications.length; ++i) {
			notificationTypes[i] = new NotificationType(notifications[i]);
		}		

		growlConnector.register(application, notificationTypes);
		return true;
	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath) {
		growlConnector.notify(new Notification(application,
				getNotification(alert), title, description));

	}

	@Override
	public void send(String alert, String title, String description) {
		send(alert, title, description, null);

	}

	@Override
	public void unload() {

		// TODO Auto-generated method stub

	}

	public NotificationType getNotification(String alert) {
		for (NotificationType t : notificationTypes) {
			if (t.getDisplayName().equals(alert))
				return t;

		}
		return null;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public boolean setHost(String host) {
		String[] n=new String[notificationTypes.length];
		for(int i=0;i<notificationTypes.length;++i){
			n[i]=notificationTypes[i].getDisplayName();
		}
		
		return load(n,host);		
	}
	
	@Override
	public NotiferTypes getName() {
		return NotiferTypes.NetGrowl;
	}

}
