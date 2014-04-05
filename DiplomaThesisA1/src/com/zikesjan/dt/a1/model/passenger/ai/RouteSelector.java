package com.zikesjan.dt.a1.model.passenger.ai;

import com.zikesjan.dt.a1.environment.ExperimentEvaluator;
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
		//FIXME API is not properly providing the information about emissions
		ExperimentEvaluator ee = ExperimentEvaluator.getInstance();
		ee.totalEmissions += route.getEmissions();
		ee.totalDistance += route.getDistance();
		ee.totalPrice += route.getPrice();
		ee.totalTravellingTime += route.getDuration();
		ee.totalPhysicalEffort += route.getPhysicalEffort();
		Visualizer.visualizeRoute(route);
	}
}
