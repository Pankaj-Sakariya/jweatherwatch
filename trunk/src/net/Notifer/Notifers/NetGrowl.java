package net.Notifer.Notifers;

import net.SettingsReader;
import net.Utils;
import net.Notifer.NetNotifer;
import net.Notifer.NotiferTypes;
import net.Utils.OS;
import net.sf.libgrowl.Application;
import net.sf.libgrowl.GrowlConnector;
import net.sf.libgrowl.IResponse;
import net.sf.libgrowl.Notification;
import net.sf.libgrowl.NotificationType;
import net.sf.libgrowl.internal.IProtocol;

public class NetGrowl implements NetNotifer {
	GrowlConnector growlConnector = null;
	Application application = null;
	NotificationType notificationTypes[] = null;
	String host = null;

	@Override
	public boolean load(String[] notifications) {

		return load(notifications, "localhost");

	}

	public boolean load(String[] notifications, String host) {
		notificationTypes = new NotificationType[notifications.length];
		for (int i = 0; i < notifications.length; ++i) {
			notificationTypes[i] = new NotificationType(notifications[i]);
		}
		return load(notificationTypes, host);

	}

	public boolean load(NotificationType[] notifications, String host) {
		this.host = host;
		int port = IProtocol.DEFAULT_GROWL_PORT;
		if (Utils.getOS() == OS.MAC)
			port = 23052;
		growlConnector = new GrowlConnector(host, port);
		application = new Application(SettingsReader.name, SettingsReader
				.getIconpPath()
				+ "01.png");
		return growlConnector.register(application, notificationTypes) == IResponse.OK;

	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath, String url) {
		Notification notification = new Notification(application,
				getNotification(alert), title, description);
		if (iconPath != null)
			notification.setIcon(iconPath);
		growlConnector.notify(notification);
	}

	@Override
	public void unload() {

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
		return load(notificationTypes, host);
	}

	@Override
	public NotiferTypes getName() {
		return NotiferTypes.NetGrowl;
	}

}
