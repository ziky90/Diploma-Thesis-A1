package com.zikesjan.dt.a1;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.zikesjan.dt.a1.api.Connector;
import com.zikesjan.dt.a1.generator.PointGenerator;
import com.zikesjan.dt.a1.model.request.Request;
import com.zikesjan.dt.a1.model.route.Point;
import com.zikesjan.dt.a1.model.route.Route;

public class Main {

	public static void main(String[] args) {
		//TODO generate the points
		//TODO find all the routes
		//TODO do some analysis
		Point center = new Point(45466770, 9186372); //heuristically set the center of Milan
		Request r = new Request(10, 2000, addAllMeansOfTransport(), PointGenerator.generatePointInCircle(center, 0.1f), PointGenerator.generatePointInCircle(center, 0.1f));//new Point(45481920, 9188340), new Point(45422913, 9105514));
		List<Route> result = Connector.getInfo(new Date(), r);
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
