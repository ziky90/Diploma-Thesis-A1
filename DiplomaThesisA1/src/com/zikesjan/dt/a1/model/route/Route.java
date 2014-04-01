package com.zikesjan.dt.a1.model.route;

import java.util.Date;
import java.util.List;

import com.zikesjan.dt.a1.model.passenger.ai.RouteSelector;

/**
 * POJO class representing the route
 * @author zikesjan
 *
 */
public class Route implements Comparable<Route>{

	private String description;
	private int emissions;
	private int physicalEffort;
	private Date departure;
	private Date arrival;
	private int duration;
	private int distance;
	private List<Leg> legs;
	private int price;
	
	public Route(String description, int emissions, int physicalEffort,
			Date departure, Date arrival, int duration, int distance,
			List<Leg> legs) {
		super();
		this.description = description;
		this.emissions = emissions;
		this.physicalEffort = physicalEffort;
		this.departure = departure;
		this.arrival = arrival;
		this.duration = duration;
		this.distance = distance;
		this.legs = legs;
		this.price = RouteSelector.computeRoutePrices(legs, description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getEmissions() {
		return emissions;
	}

	public void setEmissions(int emissions) {
		this.emissions = emissions;
	}

	public int getPhysicalEffort() {
		return physicalEffort;
	}

	public void setPhysicalEffort(int physicalEffort) {
		this.physicalEffort = physicalEffort;
	}

	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public Date getArrival() {
		return arrival;
	}

	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public List<Leg> getLegs() {
		return legs;
	}

	public void setLegs(List<Leg> legs) {
		this.legs = legs;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public int compareTo(Route o) {
		for(Leg l : this.legs){
			if(l.getRentStation()!=null) return -1;		//condition to prefer bike renters
		}
		for(Leg l : o.legs){
			if(l.getRentStation()!=null) return 1;		//CONDITION TO PREFER BIKE RENTERS
		}
		if(this.price > o.price) return 1;
		else return -1;
	}
	
	
	
}
