package net;

public class Version implements Comparable<Version> {
	private String version = null;

	public Version(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return version;
	}

	@Override
	public int compareTo(Version o) {
		return bigger(version.split("\\."), o.version.split("\\."), 0);

	}

	private int bigger(String[] sa, String[] sb, int i) {
		int a = Integer.valueOf(sa[i]);
		int b = Integer.valueOf(sb[i]);
		if (a < b)
			return -1;
		if (a == b) {
			if (i + 1 >= sa.length || i + 1 >= sb.length) {				
				return sa.length > sb.length?1:(sa.length == sb.length?0:-1);
			}
			return bigger(sa, sb, ++i);
		}
		if (a > b)
			return 1;
		return 0;

	}


}
