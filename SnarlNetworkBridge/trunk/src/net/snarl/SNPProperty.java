package net.snarl;

public class SNPProperty {
	private String name = null;
	private String value = null;

	public SNPProperty(String name, String value) {
		this.name = name;
		if (!value.equals(""))
			this.value = value;

	}

	public SNPProperty(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		if (value == null)
			return "";
		return "?" + name + "=" + value + "#";
	}
}
