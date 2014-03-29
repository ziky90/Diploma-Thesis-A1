package com.zikesjan.dt.a1.environment.bikeshare;

import com.zikesjan.dt.a1.model.route.Point;

/**
 * class to deal with the initial settings of the bike sharing stations
 * @author zikesjan
 *
 */
public class BikeShareUtil {

	private static final int capacity = 100;
	private static final int initialBikes = 50;
	private static final int initialPrice = 10;
	
	/**
	 * method to generate the bike sharing station at the given point with the fixed parameters
	 * @param p
	 */
	public static BikeShare saveBikeStation(Point p){
		BikeShareData bsd = BikeShareData.getInstance();
		BikeShare bs = new BikeShare(p, initialPrice, capacity, initialBikes);
		bsd.addBikeShare(bs);
		return bs;
	}
	
}
