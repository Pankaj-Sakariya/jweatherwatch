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
	private SNPProperty msg[] = null;
	protected SNPProperty action = new SNPProperty("action");

	/**
	 * The Constructor of a Snarl Message
	 * 
	 * @param msg
	 *            the SNP String
	 */
	public Message(String action, SNPProperty[] propertys) {
		this.msg = propertys;
		this.action.setValue(action);
	}

	public Message(String action, SNPProperty propertys) {
		this.action.setValue(action);
		this.msg = new SNPProperty[] { propertys };
	}

	public Message() {

		// TODO Auto-generated constructor stub
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
		String out = SnarlNetworkBridge.head + action;
		for (SNPProperty p : msg) {
			out += p;
		}
		return out;
	}

	
}
