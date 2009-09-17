package net.Notifer;

import net.Notifer.Notifers.KNotify;
import net.Notifer.Notifers.NetGrowl;
import net.Notifer.Notifers.Snarl;
import net.Notifer.Notifers.TrayNotification;
import net.Notifer.Notifers.NetSnarl.NetSnarl;

public enum NotiferTypes {
	Snarl, NetSnarl, NetGrowl, TrayIcon, KNotify;
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
		}
		return null;
	}
}