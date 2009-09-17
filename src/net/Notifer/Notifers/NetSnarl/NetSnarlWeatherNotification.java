package net.Notifer.Notifers.NetSnarl;

import net.NotificationConnector;
import net.Utils;
import net.snarl.Action;

public class NetSnarlWeatherNotification extends net.snarl.Notification {

	private String url = null;

	public NetSnarlWeatherNotification(String alert, String title,
			String content, String iconUrl, String url) {
		super(alert, title, content, iconUrl);
		this.url = url;
	}

	@Override
	protected void setUserAction(Action action) {
		super.setUserAction(action);
		switch (action) {
		case LeftClicked:
			NotificationConnector.bringFrameToFront();
			break;
		case RhigthClicked:
			Utils.visitURL(url);
			break;

		default:
			break;
		}

	}

}