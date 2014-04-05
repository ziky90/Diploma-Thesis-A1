package com.zikesjan.dt.a1.environment.bikeshare.pricing;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class BikeSharePricer {
	
	private volatile int capacity = 0;
	private static volatile BikeSharePricer instance = null;
	private volatile PriorityQueue<Bid> bids = new PriorityQueue<>();
	
	private BikeSharePricer() {	}
	private BikeSharePricer(int capacity) {
		this.capacity = capacity;
	}
	
	public static BikeSharePricer getInstance() {
		if (instance == null) {
                        synchronized (BikeSharePricer.class){
			        if (instance == null) {
                                        instance = new BikeSharePricer();
                                }
                        }
		}
		return instance;
	}
	
	/**
	 * initial get instance call that sets up details about the bike sharing station
	 * @param capacity
	 * @return
	 */
	public static BikeSharePricer getInstance(int capacity) {
		if (instance == null) {
                        synchronized (BikeSharePricer.class){
			        if (instance == null) {
                                        instance = new BikeSharePricer(capacity);
                                }
                        }
		}
		return instance;
	}
	
	/**
	 * method to simply add bid
	 * @param b
	 */
	public void addBid(Bid b){
		this.bids.add(b);
	}
	
	/**
	 * method returning all the auction winners
	 * @return
	 */
	public List<Bid> getWinningBidders(){
		List<Bid> result = new LinkedList<>();
		for(int i = 0; i<capacity; i++){
			result.add(bids.poll());
		}
		return result;
	}
	
	/**
	 * method returning all the winners of the auction
	 * @return
	 */
	public List<Bid> getAllBidders(){
		List<Bid> result = new LinkedList<>();
		result.addAll(bids);
		return result;
	}
}
