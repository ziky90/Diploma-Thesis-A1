package com.zikesjan.dt.a1.environment.bikeshare;

import com.zikesjan.dt.a1.environment.IShareStation;
import com.zikesjan.dt.a1.model.route.Point;

/**
 * POJO to store bike sharing station objects 
 * @author zikesjan
 *
 */
public class BikeShare implements IShareStation{

	private Point position;
	private int price;
	private int capacity;
	private int availableItems;
	
	public BikeShare(Point position, int price, int capacity, int availableItems) {
		super();
		this.position = position;
		this.price = price;
		this.capacity = capacity;
		this.availableItems = availableItems;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getAvailableItems() {
		return availableItems;
	}

	public void setAvailableTtems(int availableItems) {
		this.availableItems = availableItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BikeShare other = (BikeShare) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
	
	/**
	 * method that is performing the rent a bike operation
	 * @return
	 */
	public boolean rentItem(){
		
		if(this.availableItems == 0) return false;
		else{
			this.availableItems--;
			System.out.println("rented @ " +this.position + ": " + this.availableItems);
			//TODO do some pricing logic, etc.
		}
		return true;
	}
	
	/**
	 * method that is performing the return a bike operation
	 * @return
	 */
	public boolean returnItem(){
		
		if(this.capacity - this.availableItems == 0) return false;
		else{
			this.availableItems++;
			System.out.println("returned @ " + this.position + ": " + this.availableItems);
			//TODO do some pricing logic, etc.
		}
		return true;
	}
}
