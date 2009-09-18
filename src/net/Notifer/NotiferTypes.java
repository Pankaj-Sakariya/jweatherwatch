package net.Notifer;

import net.Notifer.Notifers.KNotify;
import net.Notifer.Notifers.NetGrowl;
import net.Notifer.Notifers.NetSnarl;
import net.Notifer.Notifers.Snarl;
import net.Notifer.Notifers.TrayNotification;

public enum NotiferTypes {
	Snarl, NetSnarl, NetGrowl, TrayIcon, KNotify,LibNotify;
	public static Notifer getNotifer(NotiferTypes type,java.awt.TrayIcon icon) {
		switch (type) {
		case Snarl:
			return new Snarl();

		case NetSnarl:
			return new NetSnarl();

		case NetGrowl:
			return new NetGrowl();

		case TrayIcon:
			return new TrayNotification(icon);

		case KNotify:
			return new KNotify();
		case LibNotify:
			return new net.Notifer.Notifers.LibNotify();
		}
		return null;
	}
}
