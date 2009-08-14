package net.Notifer.Notifers;

import net.NotificationConnector;
import net.Settings;
import net.Notifer.NetNotifer;
import net.Notifer.NotiferTypes;
import net.snarl.SnarlNetworkBridge;

public class NetSnarl implements NetNotifer {
	SnarlNetworkBridge snarl = null;

	@Override
	public boolean laod(String[] notifications) {
		return load(notifications, "localhost");
	}

	@Override
	public boolean load(String[] notifications, String host) {
		snarl = new SnarlNetworkBridge(Settings.name, host);
		snarl.snRegisterConfig();
		for (String s : notifications) {
			snarl.snRegisterAlert(s);
		}

		return snarl.isRunnging();
	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath) {
		snarl.snShowMessage(alert, title, description);

	}

	@Override
	public void unload() {
		snarl.snRevokeConfig();

	}

	public SnarlNetworkBridge getSnarl() {
		return snarl;
	}

	@Override
	public void send(String alert, String title, String description) {
		send(alert, title, description, null);

	}

	@Override
	public String getHost() {
		return snarl.getHost();
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
