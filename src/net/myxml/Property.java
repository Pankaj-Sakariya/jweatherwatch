package net.myxml;

public class Property {
	private String title = null;
	private String value = null;

	public Property(String title, Object value) {
		this.title = title;
		this.value = value.toString();
	}
	
	@Override
	public String toString() {
		return "\t" + title+ "=\"" + value+ "\"";
	}

}
