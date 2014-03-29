package com.zikesjan.dt.a1.model.route;

public class AtomicTravelAction {
	
	private Point origin;
	private Point destination;
	private int duration;
	private int distance;
	
	public AtomicTravelAction(Point origin, Point destination, int duration,
			int distance) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.duration = duration;
		this.distance = distance;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public Point getDestination() {
		return destination;
	}

	public void setDestination(Point destination) {
		this.destination = destination;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
}
