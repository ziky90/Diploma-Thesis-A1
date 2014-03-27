package com.zikesjan.dt.a1.bikeshare;

import java.util.HashSet;

/**
 * singleton to keep the data about bike sharing stations
 * @author zikesjan
 *
 */
public class BikeShareData {

	private HashSet<BikeShare> shares = new HashSet<>();
	private static volatile BikeShareData instance = null;
	 
	private BikeShareData() {	}
 
	public static BikeShareData getInstance() {
		if (instance == null) {
                        synchronized (BikeShareData .class){
			        if (instance == null) {
                                        instance = new BikeShareData ();
                                }
                        }
		}
		return instance;
	}
	
	public void addBikeShare(BikeShare bs){
		synchronized (instance) {
			if(!shares.contains(bs)) shares.add(bs);
		}
	}
	
	public HashSet<BikeShare> getBikeShares(){
		return this.shares;
	}
}
