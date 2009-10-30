package net.Notifer;

import java.io.File;
import java.util.ArrayList;

import net.Utils;
import net.Notifer.Notifers.KNotify;
import net.Notifer.Notifers.NetGrowl;
import net.Notifer.Notifers.NetSnarl;
import net.Notifer.Notifers.Snarl;
import net.Notifer.Notifers.TrayNotification;
import net.Utils.OS;

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
	public static NotiferTypes[] getValues(){
		ArrayList<NotiferTypes> nots=new ArrayList<NotiferTypes>();
		for(NotiferTypes n: NotiferTypes.values())
			nots.add(n);
		OS os=Utils.getOS();
		if(os==OS.WINDOWS){
			nots.remove(KNotify);
			nots.remove(LibNotify);
		}
		if(os==OS.LINUX)
		{
			nots.remove(Snarl);
		if(!new File("/usr/share/java/gtk.jar").exists())
			nots.remove(LibNotify);
		if(!new File("/usr/bin/kdialog").exists())
				nots.remove(KNotify);
		}
		NotiferTypes types[]=new NotiferTypes[1];
		return nots.toArray(types);		
		
	}
	
	
	
}
