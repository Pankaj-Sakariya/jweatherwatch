package net;

import net.snarl.Notification;
import net.snarl.SNPActionListener;
import net.snarl.SnarlNetworkBridge;

public class testing {

	public static void main(String[] args) {
		SnarlNetworkBridge.setDebug(true);
		SnarlNetworkBridge.snRegisterConfig("Test", "localhost");
		SnarlNetworkBridge.snRegisterAlert("test");
		Notification not1=new Notification("bli", "test",
				"TEST1", "This is a test", 10);
		not1.setActionListener(new SNPActionListener() {
					
					public void notificationTimedOut() {
						// TODO Auto-generated method stub
						
					}
					
					public void notificationRightClicked() {
						System.out.println("Right clicked");
					}
					
					public void notificationLeftClicked() {
						System.out.println("Left clicked");
						
					}
					
					public void notificationClosed() {
						// TODO Auto-generated method stub
						
					}
				});
		SnarlNetworkBridge.snShowMessage(not1);
		 SnarlNetworkBridge.snRevokeConfig();

	}

}
