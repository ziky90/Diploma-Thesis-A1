package com.zikesjan.dt.a1;

import java.util.concurrent.CountDownLatch;

import com.zikesjan.dt.a1.api.ApiCallingThread;
import com.zikesjan.dt.a1.bikeshare.BikeShareData;
import com.zikesjan.dt.a1.model.route.Point;
import com.zikesjan.dt.a1.visualization.Visualizer;

/**
 * Main class dealing with the experiments
 * @author zikesjan
 *
 */
public class Main {

	public static volatile int threadsRunned = 0;		//variable to count all the threads that has finished
	private static final int threads = 19;				//number of threads to be used
	private static final int passengersPerThread = 20;	//number of passengers generated per one thread
	private static final Point center = new Point(45466770, 9186372); //heuristically set the center of Milan
	private static final float radius = 0.05f;			//radius of the point from where are points generated
	
	
	public static void main(String[] args) {
		CountDownLatch startSignal = new CountDownLatch(1);
	    CountDownLatch doneSignal = new CountDownLatch(threads);
		for(int i = 0; i<threads; i++){
			ApiCallingThread act = new ApiCallingThread(center, radius, passengersPerThread, startSignal, doneSignal);
			new Thread(act).start();
		}
		startSignal.countDown();
		ApiCallingThread.generateWithRoutes(passengersPerThread, center, radius);
		try {
			doneSignal.await();							//waiting for other threads
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		BikeShareData bsd = BikeShareData.getInstance();
		System.out.println(bsd.getBikeShares().size());
		
		//TODO do some analysis
		//TODO visualize
		Visualizer.visualize(bsd.getBikeShares());
	}	
}
