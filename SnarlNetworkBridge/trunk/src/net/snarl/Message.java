package net.snarl;

/**
 * 
 * A snarl Message containing Message and Snarl Reply
 * 
 * @author Patrick von Reth
 * 
 * 
 */
public class Message {

	private Reply reply = null;
	private String msg = null;

	/**
	 * The Constructor of a Snarl Message
	 * 
	 * @param msg
	 *            the SNP String
	 */
	public Message(String msg) {
		this.msg = SnarlNetworkBridge.head + msg;
	}

	/**
	 * Returns the Reply of Snarl if set, otherwise null
	 * 
	 * @return the Reply of the Message
	 */
	public Reply getReply() {
		return reply;
	}

	/**
	 * Called by SnarlNetworkBridge to set the Snarl Reply
	 * 
	 * @param reply
	 *            the Reply which is to be set
	 */
	void setReply(Reply reply) {
		this.reply = reply;
	}

	/**
	 * Returns the SNP String
	 */
	public String toString() {
		return msg;
	}
}
