package com.zikesjan.dt.a1.environment.bikeshare;

import java.util.Collection;
import java.util.HashMap;

import com.zikesjan.dt.a1.model.route.Point;

/**
 * singleton to keep the data about bike sharing stations
 * @author zikesjan
 *
 */
public class BikeShareData {

	private HashMap<Point, BikeShare> shares = new HashMap<>();
	private static volatile BikeShareData instance = null;
	public static volatile boolean hasChanged = false;
	 
	private BikeShareData() {	}
 
	public static BikeShareData getInstance() {
		if (instance == null) {
                        synchronized (BikeShareData.class){
			        if (instance == null) {
                                        instance = new BikeShareData ();
                                }
                        }
		}
		return instance;
	}
	
	public BikeShare addBikeShare(Point p, int initialPrice, int capacity, int initialBikes){
		synchronized (instance) {
			if(shares.get(p) == null){
				BikeShare bs = new BikeShare(p, initialPrice, capacity, initialBikes);
				shares.put(p,bs);
				return bs;
			}else{
				return shares.get(p);
			}
		}
	}
	
	public Collection<BikeShare> getBikeShares(){
		return this.shares.values();
	}
	
	public static void printBikeshareStations(int id){
		BikeShareData bsd = BikeShareData.getInstance();
		System.out.println();
		int counter = 0;
		for(BikeShare bs : bsd.shares.values()){
			System.out.println(counter+"##  @"+bs.getPosition().toString() + " | price: " +bs.getPrice()+ " | availableItems: " + bs.getAvailableItems() + " | remainingSpaces: " + (bs.getCapacity()-bs.getAvailableItems()));
			counter++;
		}
	}
}
