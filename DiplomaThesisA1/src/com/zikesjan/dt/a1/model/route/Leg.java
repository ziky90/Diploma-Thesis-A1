package com.zikesjan.dt.a1.model.route;

import java.util.List;

import com.zikesjan.dt.a1.environment.IShareStation;

/**
 * POJO class modeling one leg of the journey
 * @author zikesjan
 *
 */
public class Leg {
	private String type;
	private int id;
	private int emissions;
	private int distance;
	private int duration;
	private int physicalEffort;
	private List<AtomicTravelAction> atomicTravelActions;
	private IShareStation rentStation;
	private IShareStation returnStation;
	
		
	public Leg(String type, int id, int emissions, int distance, int duration,
			int physicalEffort, List<AtomicTravelAction> atomicTravelActions) {
		super();
		this.type = type;
		this.id = id;
		this.emissions = emissions;
		this.distance = distance;
		this.duration = duration;
		this.physicalEffort = physicalEffort;
		this.atomicTravelActions = atomicTravelActions;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmissions() {
		return emissions;
	}

	public void setEmissions(int emissions) {
		this.emissions = emissions;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getPhysicalEffort() {
		return physicalEffort;
	}

	public void setPhysicalEffort(int physicalEffort) {
		this.physicalEffort = physicalEffort;
	}


	public IShareStation getRentStation() {
		return rentStation;
	}


	public void setRentStation(IShareStation rentStation) {
		this.rentStation = rentStation;
	}


	public IShareStation getReturnStation() {
		return returnStation;
	}


	public void setReturnStation(IShareStation returnStation) {
		this.returnStation = returnStation;
	}


	public List<AtomicTravelAction> getAtomicTravelActions() {
		return atomicTravelActions;
	}


	public void setAtomicTravelActions(List<AtomicTravelAction> atomicTravelActions) {
		this.atomicTravelActions = atomicTravelActions;
	}	
	
}
