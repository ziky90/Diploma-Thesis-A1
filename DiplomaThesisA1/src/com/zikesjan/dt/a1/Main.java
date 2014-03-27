package com.zikesjan.dt.a1;

import com.zikesjan.dt.a1.api.ApiCallingThread;
import com.zikesjan.dt.a1.bikeshare.BikeShareData;
import com.zikesjan.dt.a1.model.route.Point;

public class Main {

	public static volatile int threadsRunned = 0;
	private static final int threads = 3;
	private static final int passengersPerThread = 100;
	private static final Point center = new Point(45466770, 9186372); //heuristically set the center of Milan
	private static final float radius = 0.05f;
	
	
	public static void main(String[] args) {
			
		for(int i = 0; i<threads; i++){
			ApiCallingThread act = new ApiCallingThread(center, radius, passengersPerThread);
			new Thread(act).start();
		}
		ApiCallingThread.generateWithRoutes(passengersPerThread, center, radius);
		BikeShareData bsd = BikeShareData.getInstance();
		while(threadsRunned != threads){
			
		}
		System.out.println(bsd.getBikeShares().size());
		
		//TODO do some analysis
		//TODO visualize
	}	
}
