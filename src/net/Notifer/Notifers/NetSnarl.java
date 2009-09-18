package net.Notifer.Notifers;

import net.NotificationConnector;
import net.SettingsReader;
import net.Utils;
import net.Notifer.NetNotifer;
import net.Notifer.NotiferTypes;
import net.snarl.Notification;
import net.snarl.SNPActionListener;
import net.snarl.SnarlNetworkBridge;

public class NetSnarl implements NetNotifer {

	@Override
	public boolean load(String[] notifications) {
		return load(notifications, "localhost");
	}

	@Override
	public boolean load(String[] notifications, String host) {
		// SnarlNetworkBridge.setDebug(true);
		SnarlNetworkBridge.snRegisterConfig(SettingsReader.name, host);

		for (String s : notifications) {
			SnarlNetworkBridge.snRegisterAlert(s);
		}

		return SnarlNetworkBridge.snIsRunnging();
	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath, final String url) {
		Notification not = new Notification(alert, title, description, iconPath);
		not.setActionListener(new SNPActionListener() {

			@Override
			public void notificationTimedOut() {
				// TODO Auto-generated method stub

			}

			@Override
			public void notificationRightClicked() {
				if (url != null)
					Utils.visitURL(url);
			}

			@Override
			public void notificationLeftClicked() {
				NotificationConnector.bringFrameToFront();
			}

			@Override
			public void notificationClosed() {
				// TODO Auto-generated method stub

			}
		});
		SnarlNetworkBridge.snShowMessage(not);

	}

	@Override
	public void unload() {
		SnarlNetworkBridge.snRevokeConfig();

	}

	@Override
	public String getHost() {
		return SnarlNetworkBridge.snGetHost();
	}

	@Override
	public boolean setHost(String host) {
		unload();
		return load(NotificationConnector.notifications, host);
	}

	@Override
	public NotiferTypes getName() {
		return NotiferTypes.NetSnarl;
	}

}
