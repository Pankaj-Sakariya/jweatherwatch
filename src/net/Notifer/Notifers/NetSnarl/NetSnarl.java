package net.Notifer.Notifers.NetSnarl;

import net.NotificationConnector;
import net.SettingsReader;
import net.Notifer.NetNotifer;
import net.Notifer.NotiferTypes;
import net.snarl.SnarlNetworkBridge;

public class NetSnarl implements NetNotifer {

	@Override
	public boolean laod(String[] notifications) {
		return load(notifications, "localhost");
	}

	@Override
	public boolean load(String[] notifications, String host) {
		SnarlNetworkBridge.snRegisterConfig(SettingsReader.name, host);
		for (String s : notifications) {
			SnarlNetworkBridge.snRegisterAlert(s);
		}
		System.out.println(SnarlNetworkBridge.snIsRunnging());
		return SnarlNetworkBridge.snIsRunnging();
	}

	@Override
	public void send(String alert, String title, String description,
			String iconPath) {
		SnarlNetworkBridge.snShowMessage(new NetSnarlWeatherNotification(alert, title, description));

	}

	@Override
	public void unload() {
		SnarlNetworkBridge.snRevokeConfig();

	}



	@Override
	public void send(String alert, String title, String description) {
		send(alert, title, description, null);

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
