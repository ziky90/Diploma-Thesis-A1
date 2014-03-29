package com.zikesjan.dt.a1.model.route;

/**
 *POJO class modeling one geo point
 * @author zikesjan
 *
 */
public class Point {

	private int lat;
	private int lon;
	
	
	public Point(int lat, int lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}
	
	public int getLat() {
		return lat;
	}
	
	public double getLatDouble(){
		return (double)(lat)/1e6;
	}
	
	public void setLat(int lat) {
		this.lat = lat;
	}
	
	public int getLon() {
		return lon;
	}
	
	public double getLonDouble(){
		return (double)(lon)/1e6;
	}
	
	public void setLon(int lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return lat + ", " + lon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lat;
		result = prime * result + lon;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (lat != other.lat)
			return false;
		if (lon != other.lon)
			return false;
		return true;
	}
	
	
}
