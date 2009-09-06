package net.myxml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Doc {
	private LinkedList<Node> root = new LinkedList<Node>();
	private String title = null;
	private LinkedList<Doc> docs = new LinkedList<Doc>();

	public Doc(String title) {
		this.title = title;
	}

	public void save(File file) {
		PrintWriter out = null;

		try {
			if (file.getParentFile() != null && !file.getParentFile().exists())
				file.getParentFile().mkdir();
			out = new PrintWriter(file);

			out.print(this.toString());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void addDoc(Doc doc) {
		docs.add(doc);
	}

	public void appendNode(Node node) {
		root.add(node);
	}

	public void appendNode(String name, Object content) {
		appendNode(new Node(name, content));
	}

	public void appendNode(String name, Object content, String comment) {
		Node n = new Node(name, content);
		n.addComment(comment);
		appendNode(n);
	}

	private String getDocumentContent(int deps) {

		String out = "";
		String tab = "";
		if (deps == 0)
			out += "<?xml version='1.0' encoding='UTF-8' ?>\n";
		for (int i = 0; i < deps; i++)
			tab += "\t";
		out += tab + "<" + title + ">\n";
		for (Node n : root) {
			if (n.getComment() != null)
				out += tab + "\t" + n.getComment() + "\n";
			out += tab + "\t" + n + "\n";
		}
		for (Doc d : docs)
			out += d.getDocumentContent(deps + 1) + "\n";

		out += tab + "</" + title + ">";
		return out;

	}

	@Override
	public String toString() {
		return getDocumentContent(0);
	}
}
