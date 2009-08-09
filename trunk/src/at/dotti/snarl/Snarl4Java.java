package at.dotti.snarl;

/**
 * @author Stefan Dotti
 * 
 */
public class Snarl4Java {

	public static native long snShowMessage(String title, String text, long timeout, String iconPath, long hWndReply, long uReplyMsg);

	public static native boolean snHideMessage(long id);

	public static native boolean snIsMessageVisible(long id);

	public static native boolean snUpdateMessage(long Id, String title, String text);

	public static native long snRegisterConfig(long hWnd, String appName, long replyMsg);

	public static native long snRevokeConfig(long hWnd);

	public static native int snGetMajorVersion();

	public static native int snGetMinorVersion();

	public static native long snGetGlobalMsg();

	/**
	 * @return a String representing the Snarl version.
	 */
	public static String snGetVersion() {
		return snGetMajorVersion() + "." + snGetMinorVersion();
	}

}
