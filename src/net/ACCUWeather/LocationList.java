package net.ACCUWeather;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.ACCUWeatherFetcher;

import org.w3c.dom.Element;

public class LocationList extends HashMap<Integer, Location> implements
		Iterable<Location>, Iterator<Location>,Closeable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5423811612672652279L;
	private int count = 0;
	private LocationListUser parent = null;

	public LocationList() {
		super();
	}

	public LocationList(LocationListUser locationListUser,
			Element[] locationElements) {
		super();
		parent = locationListUser;
		for (Element e : locationElements) {
			put(size(), new Location(e));
		}
	}

	@Override
	public Location remove(Object key) {
		Location o = super.remove(key);
		if (o == null)
			return null;
		System.out.println("Remove: " + key + " " + o);
		Object loc[] = this.values().toArray();
		this.clear();
		for (int i = 0; i < loc.length; ++i) {
			System.out.println("[" + i + "] " + loc[i]);
			this.put(i, (Location) loc[i]);
		}
		o.stop();
		return o;
	}

	public net.myxml.Doc toXML() {
		net.myxml.Doc doc = new net.myxml.Doc("locations");
		net.myxml.Node n = null;
		for (Location l : values()) {
			n = new net.myxml.Node("location");
			n.addProperty("city", l.getCity());
			n.addProperty("state", l.getState());
			n.addProperty("location", l.getLocation());
			doc.appendNode(n);
		}
		return doc;
	}

	@Override
	public Iterator<Location> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		if (count < size())
			return true;
		count = 0;
		return false;
	}

	@Override
	public Location next() {
		if (count == size())
			throw new NoSuchElementException();
		return get(count++);
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Location put(Integer key, Location value) {
		value.setNr(key);
		value.setParentLocationList(this);	
		return super.put(key, value);
	}

	public void updated(Location location) {
		if (parent != null)
			parent.locationUpdated(location);
	}

	@Override
	public void close() throws IOException {
		ACCUWeatherFetcher.save(this);		
	}

}
