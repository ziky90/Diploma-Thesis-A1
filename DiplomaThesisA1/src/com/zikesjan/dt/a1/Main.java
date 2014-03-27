package com.zikesjan.dt.a1;

import com.zikesjan.dt.a1.api.ApiCallingThread;
import com.zikesjan.dt.a1.bikeshare.BikeShareData;
import com.zikesjan.dt.a1.model.route.Point;

/**
 * Main class dealing with the experiments
 * @author zikesjan
 *
 */
public class Main {

	public static volatile int threadsRunned = 0;		//variable to count all the threads that has finished
	private static final int threads = 3;				//number of threads to be used
	private static final int passengersPerThread = 100;	//number of passengers generated per one thread
	private static final Point center = new Point(45466770, 9186372); //heuristically set the center of Milan
	private static final float radius = 0.05f;			//radius of the point from where are points generated
	
	
	public static void main(String[] args) {
		
		for(int i = 0; i<threads; i++){
			ApiCallingThread act = new ApiCallingThread(center, radius, passengersPerThread);
			new Thread(act).start();
		}
		ApiCallingThread.generateWithRoutes(passengersPerThread, center, radius);
		BikeShareData bsd = BikeShareData.getInstance();
		
		while(threadsRunned != threads){}				//waiting for the threads
		System.out.println(bsd.getBikeShares().size());
		
		//TODO do some analysis
		//TODO visualize
	}	
}
