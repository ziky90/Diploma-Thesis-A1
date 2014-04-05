package com.zikesjan.dt.a1.api;

import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.CountDownLatch;

import com.zikesjan.dt.a1.generator.PassengerGenerator;
import com.zikesjan.dt.a1.model.passenger.Passenger;
import com.zikesjan.dt.a1.model.passenger.PassengersData;
import com.zikesjan.dt.a1.model.request.Request;
import com.zikesjan.dt.a1.model.route.Point;
import com.zikesjan.dt.a1.model.route.Route;

/**
 * Class that implements threads for api calls
 * 
 * @author zikesjan
 * 
 */
public class ApiCallingThread implements Runnable {

	private Point center;
	private float radius;
	private float radiusIn;
	private int number;
	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;
	private int interchanges;
	private int maxWalking;
	private int maxPrice;
	private int optimalPrice;

	public ApiCallingThread(Point center, float radius, int number, CountDownLatch startSignal, CountDownLatch doneSignal, int interchanges, int maxWalking, int maxPrice, int optimalPrice) {
		super();
		this.center = center;
		this.radius = radius;
		this.number = number;
		this.startSignal = startSignal;
	    this.doneSignal = doneSignal;
	    this.interchanges = interchanges;
	    this.maxWalking = maxWalking;
	    this.maxPrice = maxPrice;
	    this.optimalPrice = optimalPrice;
	}
	
	

	public ApiCallingThread(Point center, float radius, float radiusIn,
			int number, CountDownLatch startSignal, CountDownLatch doneSignal, int interchanges, int maxWalking, int maxPrice, int optimalPrice) {
		super();
		this.center = center;
		this.radius = radius;
		this.radiusIn = radiusIn;
		this.number = number;
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
		this.interchanges = interchanges;
	    this.maxWalking = maxWalking;
	    this.maxPrice = maxPrice;
	    this.optimalPrice = optimalPrice;
	}



	@Override
	public void run() {
		try {
			startSignal.await();
			//ApiCallingThread.generateWithRoutes(number, center, radius, interchanges, maxWalking, maxPrice, optimalPrice);
			ApiCallingThread.generateWithRoutesPeak(number, center, radiusIn, radius, interchanges, maxWalking, maxPrice, optimalPrice);
			doneSignal.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method to generate passengers and call the planner's api
	 * 
	 * @param number
	 * @param center
	 * @param radius
	 */
	public static void generateWithRoutes(int number, Point center, float radius, int interchanges, int maxWalking, int maxPrice, int optimalPrice) {
		List<Passenger> passengers = PassengerGenerator.generateRandomPassengersUniformlyFromCircle(number, center, radius, interchanges, maxWalking, maxPrice, optimalPrice);
		int counter = 0;
		PassengersData pd = PassengersData.getInstance();
		for (Passenger p : passengers) {
			Request r = new Request(p.getMaxInterchnges(), p.getMaxWalking(), p.getAllowedTransport(), p.getOrigin(), p.getDestination());
			PriorityQueue<Route> result = Connector.getInfo(new Date(), r);
			p.setRoutes(result);
			System.out.println("passenger " + counter + "from: "
					+ p.getOrigin().toString() + "to: "
					+ p.getDestination().toString() + ", routes found: "
					+ result.size());
			pd.addPassenger(p);
			counter++;
		}
	}
	
	/**
	 * method to generate passengers and call the planner's api
	 * 
	 * @param number
	 * @param center
	 * @param radius
	 */
	public static void generateWithRoutesPeak(int number, Point center, float radius1, float radius2, int interchanges, int maxWalking, int maxPrice, int optimalPrice) {
		List<Passenger> passengers = PassengerGenerator.generateRandomPassengersMorningPeak(number, center, radius1, radius2, interchanges, maxWalking, maxPrice, optimalPrice);
		int counter = 0;
		PassengersData pd = PassengersData.getInstance();
		for (Passenger p : passengers) {
			Request r = new Request(p.getMaxInterchnges(), p.getMaxWalking(), p.getAllowedTransport(), p.getOrigin(), p.getDestination());
			PriorityQueue<Route> result = Connector.getInfo(new Date(), r);
			p.setRoutes(result);
			System.out.println("passenger " + counter + "from: "
					+ p.getOrigin().toString() + "to: "
					+ p.getDestination().toString() + ", routes found: "
					+ result.size());
			pd.addPassenger(p);
			counter++;
		}
	}

}
