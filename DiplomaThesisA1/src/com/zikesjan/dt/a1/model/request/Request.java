package com.zikesjan.dt.a1.model.request;

import java.util.List;

import com.zikesjan.dt.a1.model.route.Point;

public class Request {

	private int maxInterchanges;
	private int maxWalking;
	private List<String> allowedTransport;
	private Point origin;
	private Point destination;
	
	public Request(int maxInterchanges, int maxWalking,
			List<String> allowedTransport, Point origin, Point destination) {
		super();
		this.maxInterchanges = maxInterchanges;
		this.maxWalking = maxWalking;
		this.allowedTransport = allowedTransport;
		this.origin = origin;
		this.destination = destination;
	}

	public int getMaxInterchanges() {
		return maxInterchanges;
	}

	public void setMaxInterchanges(int maxInterchanges) {
		this.maxInterchanges = maxInterchanges;
	}

	public int getMaxWalking() {
		return maxWalking;
	}

	public void setMaxWalking(int maxWalking) {
		this.maxWalking = maxWalking;
	}

	public List<String> getAllowedTransport() {
		return allowedTransport;
	}

	public void setAllowedTransport(List<String> allowedTransport) {
		this.allowedTransport = allowedTransport;
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
	
	
}
