package net;

import net.snarl.Action;
import net.snarl.Notification;

public class testingNotification extends Notification {
String text=null;
public testingNotification(String test,String alert, String title,
		String content,int timeout) {
	super( alert, title, content,"http://jweatherwatch.googlecode.com/svn/trunk/iconset/01.png",timeout);
	text=test;
}
 	 

	@Override
	public void setUserAction(Action response ) {
		// TODO Auto-generated method stub
		super.setUserAction(response);
		System.out.println(text);
		switch (response) {
		case LeftClicked:
			System.out.println("ok");
			System.exit(0);						
			break;

		default:
			break;
		}

	}

}
