package net.Notifer;

public interface NetNotifer extends Notifer{
	public String getHost();
	public boolean load(String[]  notifications,String host);

}
