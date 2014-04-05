package com.zikesjan.dt.a1.environment.bikeshare.pricing;

import com.zikesjan.dt.a1.model.passenger.Passenger;

public class Bid implements Comparable<Bid>{

	private Passenger passenger;
	private int price;
	
	public Bid(Passenger passenger, int price) {
		super();
		this.passenger = passenger;
		this.price = price;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public int compareTo(Bid o) {
		if(this.price > o.price) return 1;
		return -1;
	}
	
}
