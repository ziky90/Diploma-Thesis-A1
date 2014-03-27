package com.zikesjan.dt.a1.model.passenger;

import java.util.List;

import com.zikesjan.dt.a1.model.route.Point;
import com.zikesjan.dt.a1.model.route.Route;

/**
 * POJO modelling the passenger
 * @author zikesjan
 *
 */
public class Passenger {

	private Point origin;
	private Point destination;
	private int maxWalking;
	private List<String> allowedTransport;
	private int maxInterchnges;
	private double optimalPrice;
	private double maxPrice;
	private List<Route> routes;
	
	public Passenger(Point origin, Point destination, int maxWalking,
			List<String> allowedTransport, int maxInterchnges,
			double optimalPrice, double maxPrice) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.maxWalking = maxWalking;
		this.allowedTransport = allowedTransport;
		this.maxInterchnges = maxInterchnges;
		this.optimalPrice = optimalPrice;
		this.maxPrice = maxPrice;
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

	public int getMaxInterchnges() {
		return maxInterchnges;
	}

	public void setMaxInterchnges(int maxInterchnges) {
		this.maxInterchnges = maxInterchnges;
	}

	public double getOptimalPrice() {
		return optimalPrice;
	}

	public void setOptimalPrice(double optimalPrice) {
		this.optimalPrice = optimalPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
	
}
