package com.zikesjan.dt.a1.model.passenger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class PassengersData {
	private List<Passenger> passengers = new CopyOnWriteArrayList<>();
	private static volatile PassengersData instance = null;
	 
	private PassengersData() {}
 
	public static PassengersData getInstance() {
		if (instance == null) {
                        synchronized (PassengersData.class){
			        if (instance == null) {
                                        instance = new PassengersData();
                                }
                        }
		}
		return instance;
	}
	
	public void addPassenger(Passenger passenger){
		passengers.add(passenger);
	}
	
	public List<Passenger> getPassengers(){
		return this.passengers;
	}
}
