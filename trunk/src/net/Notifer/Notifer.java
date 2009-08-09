package net.Notifer;

public interface Notifer {
	public boolean laod();
	public void send(String alert, String title,
			String description, String iconPath);
	public void unload();

}
