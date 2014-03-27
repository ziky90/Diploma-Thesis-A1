package com.zikesjan.dt.a1.api;

import java.util.Date;
import java.util.List;

import com.zikesjan.dt.a1.Main;
import com.zikesjan.dt.a1.generator.PassengerGenerator;
import com.zikesjan.dt.a1.model.passenger.Passenger;
import com.zikesjan.dt.a1.model.passenger.PassengersData;
import com.zikesjan.dt.a1.model.request.Request;
import com.zikesjan.dt.a1.model.route.Point;
import com.zikesjan.dt.a1.model.route.Route;

public class ApiCallingThread implements Runnable {

	private Point center;
	private float radius;
	private int number;

	public ApiCallingThread(Point center, float radius, int number) {
		super();
		this.center = center;
		this.radius = radius;
		this.number = number;
	}

	@Override
	public void run() {
		ApiCallingThread.generateWithRoutes(number, center, radius);
		Main.threadsRunned++;
	}

	public static void generateWithRoutes(int number, Point center, float radius) {
		List<Passenger> passengers = PassengerGenerator
				.generateRandomPassengers(number, center, radius);

		int counter = 0;
		PassengersData pd = PassengersData.getInstance();
		for (Passenger p : passengers) {
			Request r = new Request(p.getMaxInterchnges(), p.getMaxWalking(),
					p.getAllowedTransport(), p.getOrigin(), p.getDestination());
			List<Route> result = Connector.getInfo(new Date(), r);
			p.setRoutes(result);
			System.out.println("passenger " + counter + "from: "
					+ p.getOrigin().toString() + "to: "
					+ p.getDestination().toString() + ", routes found: "
					+ result.size());
			pd.addPassenger(p);
			for (Route rt : result) {
				System.out.println(rt.getDescription());
			}
			counter++;
			 
		}
	}

}
