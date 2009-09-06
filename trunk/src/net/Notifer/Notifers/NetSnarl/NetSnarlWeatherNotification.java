package net.Notifer.Notifers.NetSnarl;

import net.NotificationConnector;
import net.snarl.Action;

public class NetSnarlWeatherNotification extends net.snarl.Notification{

	public NetSnarlWeatherNotification(String arg0, String arg1, String arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setAction(Action arg0) {
		// TODO Auto-generated method stub
		super.setAction(arg0);
		if(arg0==Action.LeftClicked){
			NotificationConnector.bringFrameToFront();	
		}
		}
	
}