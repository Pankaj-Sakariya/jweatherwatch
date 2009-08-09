package net.ACCUWeather;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.w3c.dom.Element;

public class LocationList extends HashMap<Integer, Location> implements
		Iterable<Location>, Iterator<Location> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5423811612672652279L;
	private int count = 0;
	
	public LocationList() {
		super();		
	}
	
	

	public LocationList(Element[] locationElements) {

		for (Element e : locationElements) {
			put(size(), new Location(e));
		}
	}

	public LocationList(String[] locs) {
		for (String s : locs) {
			put(size(), new Location(s));
		}
	}

	@Override
	public Location remove(Object key) {

		Location o = super.remove(key);
		System.out.println("Remove: " + key + " " + o);
		Object loc[] = this.values().toArray();
		this.clear();
		for (int i=0;i<loc.length;++i) {
			System.out.println("[" + i + "] " + loc[i]);
			this.put(i++, (Location)loc[i]);
		}
		o.stop();
		return o;
	}

	public String toXML() {
		String out = "<locations>\n";
		for (Location l : values()) {
			out += "\t<location city=\"" + l.city + "\" state=\"" + l.state
					+ "\" location=\"" + l.location + "\"/>\n";
		}
		out += "</locations>";
		return out;
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


		
	
	
	
		
	


}
