package com.zikesjan.dt.a1.model.passenger.ai;

import java.util.List;

import com.zikesjan.dt.a1.environment.IShareStation;
import com.zikesjan.dt.a1.environment.bikeshare.BikeShareData;
import com.zikesjan.dt.a1.model.passenger.Passenger;
import com.zikesjan.dt.a1.model.route.Leg;
import com.zikesjan.dt.a1.model.route.Route;
import com.zikesjan.dt.a1.visualization.Visualizer;

/**
 * class dealing with the simulation and choosing of the mean of the transport
 * 
 * @author zikesjan
 * 
 */
public class RouteSelector {

	private static final int carPerKM = 5;
	private static final int PTcharge = 15;
	private static final int taxiPerKM = 10;

	/**
	 * method that computes the price that should the route cost
	 * @param legs
	 * @param routeType
	 * @return
	 */
	public static int computeRoutePrices(List<Leg> legs, String routeType) {
		int price = 0;
		if(routeType.contains("PT")) price = PTcharge;
		for (Leg l : legs) {
			if (l.getType().equals("WALK")) {
				price += l.getDuration();
			} else if (l.getType().equals("CAR")) {
				price += (l.getDistance() * carPerKM) + l.getDuration();
			} else if (l.getType().equals("TAXI")) {
				price += (l.getDistance() * taxiPerKM) + l.getDuration();
			} else if (l.getType().equals("SHARED_BIKE")) {
				price += l.getRentStation().getPrice() + l.getDuration();
			}else{
				price += l.getDuration();
			}
		}
		return price;
	}
	
	/**
	 * method that finds the best possible route for the passenger
	 * @param p
	 * @return
	 */
	public static Route selectTheBestRoute(Passenger p){
		Route preffered = null;
		while(!p.getRoutes().isEmpty()){
			boolean correct = true;
			preffered = p.getRoutes().poll();
			for(Leg l : preffered.getLegs()){
				if(l.getRentStation() != null){
					IShareStation rentingStation = l.getRentStation();
					IShareStation returningStation = l.getReturnStation();
					if(rentingStation.getAvailableItems() == 0){
						correct = false;
						break;
					}
					if(returningStation.getCapacity() - returningStation.getAvailableItems() == 0){
						correct = false;
						break;
					}
				}
			}
			if(correct) break;
		}
		return preffered;
	}	
	
	/**
	 * method that performs the route
	 * @param route
	 */
	public static void performRoute(Route route){
		for(Leg l : route.getLegs()){
			if(l.getRentStation() != null){
				l.getRentStation().rentItem();
				l.getReturnStation().returnItem();
				BikeShareData.hasChanged = true;
			}
		}
		Visualizer.visualizeRoute(route);
	}
}
