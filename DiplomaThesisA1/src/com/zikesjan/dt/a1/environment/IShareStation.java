package com.zikesjan.dt.a1.environment;

/**
 * Interface that represents the sharing station of any type 
 * @author zikesjan
 *
 */
public interface IShareStation {

	public int getCapacity();
	public int getPrice();
	public int getAvailableItems();
	public boolean rentItem();
	public boolean returnItem();
	
}
