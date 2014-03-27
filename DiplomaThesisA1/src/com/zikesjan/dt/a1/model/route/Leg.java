package com.zikesjan.dt.a1.model.route;

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
	private int price;
	
		
	public Leg(String type, int id, int emissions, int distance, int duration,
			int physicalEffort) {
		super();
		this.type = type;
		this.id = id;
		this.emissions = emissions;
		this.distance = distance;
		this.duration = duration;
		this.physicalEffort = physicalEffort;
		//TODO perform custom price calculation
	}

	public Leg(String type, int id, int emissions, int distance, int duration,
			int physicalEffort, int price) {
		super();
		this.type = type;
		this.id = id;
		this.emissions = emissions;
		this.distance = distance;
		this.duration = duration;
		this.physicalEffort = physicalEffort;
		this.price = price;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
