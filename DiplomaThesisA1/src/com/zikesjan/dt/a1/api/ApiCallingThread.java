package com.zikesjan.dt.a1.api;

import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.CountDownLatch;

import com.zikesjan.dt.a1.Main;
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
	private int number;
	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;

	public ApiCallingThread(Point center, float radius, int number, CountDownLatch startSignal, CountDownLatch doneSignal) {
		super();
		this.center = center;
		this.radius = radius;
		this.number = number;
		this.startSignal = startSignal;
	    this.doneSignal = doneSignal;
	}

	@Override
	public void run() {
		try {
			startSignal.await();
			ApiCallingThread.generateWithRoutes(number, center, radius);
			Main.threadsRunned++;
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
	public static void generateWithRoutes(int number, Point center, float radius) {
		List<Passenger> passengers = PassengerGenerator
				.generateRandomPassengers(number, center, radius);

		int counter = 0;
		PassengersData pd = PassengersData.getInstance();
		for (Passenger p : passengers) {
			Request r = new Request(p.getMaxInterchnges(), p.getMaxWalking(),
					p.getAllowedTransport(), p.getOrigin(), p.getDestination());
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
