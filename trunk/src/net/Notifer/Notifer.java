package net.Notifer;

public interface Notifer {
	public NotiferTypes getName();
	public boolean laod(String[] notifications);
	
	public void send(String alert, String title,
			String description, String iconPath);
	public void send(String alert, String title,
			String description);
	
	public void unload();

}