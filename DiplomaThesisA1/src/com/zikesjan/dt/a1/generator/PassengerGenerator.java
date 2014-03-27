package com.zikesjan.dt.a1.generator;

import java.util.LinkedList;
import java.util.List;

import com.zikesjan.dt.a1.model.passenger.Passenger;
import com.zikesjan.dt.a1.model.route.Point;

public class PassengerGenerator {

	public static List<Passenger> generateRandomPassengers(int n, Point center, float r){
		//TODO iplement this
		//Passenger random = new Passenger(origin, destination, maxWalking, allowedTransport, maxInterchnges, optimalPrice, maxPrice);
		//return random;
		List<Passenger> passengers = new LinkedList<>();
		for(int i = 0; i<n; i++){
			passengers.add(new Passenger(PointGenerator.generatePointInCircle(center, r),PointGenerator.generatePointInCircle(center, r), 1000, addAllMeansOfTransport(), 5, 100, 1000));
		}
		return passengers;
	}
	
	private static List<String> addAllMeansOfTransport(){
		List<String> l = new LinkedList<>();
		l.add("WALK");
		l.add("TAXI");
		l.add("CAR");
		l.add("BIKE");
		l.add("SHARED_BIKE");
		l.add("BUS");
		l.add("TRAM");
		l.add("UNDERGROUND");
		l.add("TRAIN");
		l.add("TROLLEYBUS");
		l.add("FERRY");
		l.add("OTHER");
		return l;
	}
}
