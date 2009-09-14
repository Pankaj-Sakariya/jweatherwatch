package net.Notifer.Notifers.NetSnarl;

import net.NotificationConnector;
import net.snarl.Action;

public class NetSnarlWeatherNotification extends net.snarl.Notification {

	public NetSnarlWeatherNotification(String alert, String title, String content,
			String iconUrl) {
		super(alert, title, content, iconUrl);
	}

	@Override
	protected void setUserAction(Action action) {
		super.setUserAction(action);
		if (action== Action.LeftClicked) {
			NotificationConnector.bringFrameToFront();
		}
	}

}