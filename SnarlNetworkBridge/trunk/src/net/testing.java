package net;

import net.snarl.SnarlNetworkBridge;

public class testing { 
	
	public static void main(String[] args) {
		SnarlNetworkBridge.snRegisterConfig("Test", "localhost");
		SnarlNetworkBridge.snRegisterAlert("test");
		 SnarlNetworkBridge.snShowMessage(new testingNotification("bli","test", "TEST1", "This is a test",10));
		 SnarlNetworkBridge.snShowMessage( new testingNotification("bla","test", "TEST2", "This is a test",10));
		 SnarlNetworkBridge.snShowMessage( new testingNotification("blubb","test", "TEST3", "This is a test",10));
		//SnarlNetworkBridge.snRevokeConfig();
		
	}
	


}
