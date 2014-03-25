package com.zikesjan.dt.a1.model.route;

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
	public void setLat(int lat) {
		this.lat = lat;
	}
	public int getLon() {
		return lon;
	}
	public void setLon(int lon) {
		this.lon = lon;
	}
}
