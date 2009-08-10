package net;

import net.ACCUWeather.Location;
import net.ACCUWeather.LocationList;

public class NotificationThread extends Thread {
	private boolean threadRun=true;
	private LocationList locationList=null;
	private int interval=0;
	public NotificationThread(LocationList locationList, int interval) {
		this.locationList=locationList;
		this.interval=interval;
		this.start();
	}
	@Override
	public void run() {
		threadRun=true;
		while (threadRun){		
		
			for(Location l:locationList){
			NotificationConnector.sendNotification("Current Weather Notification", l.toString(),
					l.getCurrentWeather().getNotification(),l.getCurrentWeather().getWeathericon());		
			}
			try{
				Thread.sleep(interval*10000);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		}
	public void shutdown() {
		threadRun=false;
		
	}
	public int getInterval() {
		return interval;
	}

}