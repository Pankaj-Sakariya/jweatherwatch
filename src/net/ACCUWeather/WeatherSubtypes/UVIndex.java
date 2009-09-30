package net.ACCUWeather.WeatherSubtypes;

import org.w3c.dom.Element;

public class UVIndex {

	String indexName = "";
	byte index;

	public UVIndex(Element element) {
		indexName = element.getElementsByTagName("uvindex").item(0)
				.getChildNodes().item(0).getNodeValue();
		index = Byte.valueOf(((Element) element.getElementsByTagName(
				"uvindex").item(0)).getAttribute("index"));
	}

	public UVIndex(String value) {
		if (value != null)
			this.index = Byte.valueOf(value);		
	}

	public int getIndex() {
		return index;
	}

	public String getIndexName() {
		return indexName;
	}

	public String toString() {
		return indexName + " " + index;
	}

	public boolean equals(UVIndex u) {
	return index==u.index&&indexName.equals(u.indexName);
	}

}
