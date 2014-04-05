package com.zikesjan.dt.a1;

import java.util.concurrent.CountDownLatch;

import com.zikesjan.dt.a1.api.ApiCallingThread;
import com.zikesjan.dt.a1.environment.ExperimentEvaluator;
import com.zikesjan.dt.a1.environment.bikeshare.BikeShareData;
import com.zikesjan.dt.a1.model.passenger.Passenger;
import com.zikesjan.dt.a1.model.passenger.PassengersData;
import com.zikesjan.dt.a1.model.passenger.ai.RouteSelector;
import com.zikesjan.dt.a1.model.route.Point;
import com.zikesjan.dt.a1.model.route.Route;
import com.zikesjan.dt.a1.visualization.Visualizer;

/**
 * Main class dealing with the experiments
 * @author zikesjan
 *
 */
public class Main {

	private static final int threads = 49;				//number of threads to be used
	private static final int passengersPerThread = 50;	//number of passengers generated per one thread
	private static final Point center = new Point(45466770, 9186372); //heuristically set the center of Milan
	private static final float radius = 0.05f;			//radius of the point from where are points generated
	private static final float radiusIn = 0.0125f;			//radius of the point inner circle
	
	private static final int maxInterchanges = 10;
	private static final int maxWalking = 1;
	private static final int maxPrice = 1000;
	private static final int optimalPrice = 200;
	
	
	public static void main(String[] args) {
		CountDownLatch startSignal = new CountDownLatch(1);
	    CountDownLatch doneSignal = new CountDownLatch(threads);
		for(int i = 0; i<threads; i++){
			//ApiCallingThread act = new ApiCallingThread(center, radius, passengersPerThread, startSignal, doneSignal);
			ApiCallingThread act = new ApiCallingThread(center, radius, radiusIn, passengersPerThread, startSignal, doneSignal, maxInterchanges, maxWalking, maxPrice, optimalPrice);
			new Thread(act).start();
		}
		startSignal.countDown();
		//ApiCallingThread.generateWithRoutes(passengersPerThread, center, radius);
		ApiCallingThread.generateWithRoutesPeak(passengersPerThread, center, radiusIn, radius, maxInterchanges, maxWalking, maxPrice, optimalPrice);
		try {
			doneSignal.await();							//waiting for other threads
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		BikeShareData bsd = BikeShareData.getInstance();
		System.out.println(bsd.getBikeShares().size());
		Visualizer.visualizeBikeshares(bsd.getBikeShares());
		
		PassengersData pd = PassengersData.getInstance();
		
		//TODO place bidding mechanism in here
		
		for(Passenger p : pd.getPassengers()){				//TODO extend this method to something smarter
			Route r = RouteSelector.selectTheBestRoute(p);
			if(r != null){									//we don't deal with guys that were so unlucky that planner has even not found a route for them
				RouteSelector.performRoute(r);
				Visualizer.visualizeRoute(r);
				if(BikeShareData.hasChanged){
					Visualizer.resetBikeshares(bsd.getBikeShares());
					BikeShareData.hasChanged = false;
				}
			}
		}
		Visualizer.saveToFile();
		
		ExperimentEvaluator ee = ExperimentEvaluator.getInstance();
		ee.printStatistics();
	}	
}
