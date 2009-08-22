package net.snarl;

/**
 * Enumeration containing all Snarl Replys
 * 
 * @author Patrick von Reth
 * 
 * 
 * 
 */
public enum Reply {
	OK(0), BadPacket(107), Application_Is_Already_Registered(203), Class_Is_Already_Registered(
			204), Notification_Cancelled(302), Notification_Timed_Out(303), Notification_Acknowledged(
			304), Notification_Closed(307),

	ERROR(-1);
	private int code;

	private Reply(int code) {
		this.code = code;
	}

	/**
	 * Returns the enum constant of this type with the specified code.
	 * 
	 * @param code
	 *            the Code of the enum constant to be returned
	 * @return the eum constant with the specific code
	 */
	public static Reply getByCode(int code) {
		for (Reply r : Reply.values())
			if (r.code == code)
				return r;
		return ERROR;
	}

	/**
	 * Returns the code of the specific enum constant
	 * 
	 * @return the code of the enum constant
	 */
	public int getCode() {
		return code;
	}
}
