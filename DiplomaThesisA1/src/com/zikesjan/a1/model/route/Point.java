package com.zikesjan.a1.model.route;

public class Point {

	private int lat;
	private int lon;
	
	
	public Point(int lat, int lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}
	
	public long getLat() {
		return lat;
	}
	public void setLat(int lat) {
		this.lat = lat;
	}
	public long getLon() {
		return lon;
	}
	public void setLon(int lon) {
		this.lon = lon;
	}
}
