package net.Notifer.Notifers;

import net.Notifer.Notifer;
import net.snarl.SnarlNetworkBridge;

public class NetSnarl implements Notifer {
	SnarlNetworkBridge snarl=null;

@Override
public boolean laod() {
	snarl = new SnarlNetworkBridge("ACCUWeather");
	snarl.snRegisterConfig();
	snarl.snRegisterAlert("Current Weather Notification");
	snarl.snRegisterAlert("Forecast Weather Notification");
	return snarl.SnarlIsRunnging();
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

}
