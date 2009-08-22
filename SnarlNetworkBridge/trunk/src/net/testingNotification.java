package net;

import net.snarl.Action;
import net.snarl.Notification;

public class testingNotification extends Notification {
String text=null;
public testingNotification(String test,String alert, String title,
		String content,int timeout) {
	super( alert, title, content,timeout);
	text=test;
}
 	 

	@Override
	public void setAction(Action response ) {
		// TODO Auto-generated method stub
		super.setAction(response);
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
