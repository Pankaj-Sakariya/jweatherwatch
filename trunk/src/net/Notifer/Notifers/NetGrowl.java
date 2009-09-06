package net.Notifer.Notifers;


import net.SettingsReader;
import net.Notifer.NetNotifer;
import net.Notifer.NotiferTypes;
import net.sf.libgrowl.Application;
import net.sf.libgrowl.GrowlConnector;
import net.sf.libgrowl.IResponse;
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
		notificationTypes = new NotificationType[notifications.length];
		for (int i = 0; i < notifications.length; ++i) {
			notificationTypes[i] = new NotificationType(notifications[i]);
		}	
		return load(notifications, host);
		
	}
	
	public boolean load(NotificationType[] notifications, String host){
		this.host=host;	
		growlConnector = new GrowlConnector(host);
		application = new Application(SettingsReader.name, SettingsReader.getIconpPath()+"01.png");		
		return growlConnector.register(application, notificationTypes)==IResponse.OK;
		
	}


	@Override
	public void send(String alert, String title, String description,
			String iconPath) {
		Notification notification=new Notification(application,
				getNotification(alert), title, description);
		notification.setIcon(iconPath);
		growlConnector.notify(notification);
	}

	@Override
	public void send(String alert, String title, String description) {
		send(alert, title, description, "");

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
		unload();		
		return load(notificationTypes,host);		
	}
	
	@Override
	public NotiferTypes getName() {
		return NotiferTypes.NetGrowl;
	}

}
