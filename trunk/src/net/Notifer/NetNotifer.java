package net.Notifer;

public interface NetNotifer extends Notifer{
	public boolean setHost(String host);
	public String getHost();
	public boolean load(String[]  notifications,String host);

}
