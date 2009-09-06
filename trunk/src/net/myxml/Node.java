package net.myxml;

import java.util.LinkedList;

public class Node {
	private String name = null;
	private String content = null;
	private LinkedList<Property> property = null;
	private String comment = null;

	public Node(String name, Object content) {
		this.name = name;
		this.content = content.toString();

	}

	public Node(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}

	public void addProperty(String title, String value) {
		if (property == null)
			property = new LinkedList<Property>();
		property.add(new Property(title, value));
	}

	public void addComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		if (comment != null)
			return "<!--" + comment + "-->";
		return null;
	}

	@Override
	public String toString() {
		String out = "<" + name;
		if (property != null) {
			for (Property p : property)
				out += p;
		}
		if (content != null) {
			out += ">" + content + "</" + name + ">";
		} else
			out += "/>";

		return out;
	}
}
