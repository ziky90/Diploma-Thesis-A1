package com.zikesjan.dt.a1.model.passenger.ai;

import java.util.List;

import com.zikesjan.dt.a1.model.route.Leg;

public class RoutePriceEvaluator {

	private static final int carPerKM = 2;
	private static final int PTcharge = 15;
	private static final int taxiPerKM = 4;
	private static final int interchangeAlpha = 10;
	private static final int timeAlpha = 2;
	private static final int emissionsAlpha = 1;
	private static final int physicalEffortAlpha = 1;
	

	
	/**
	 * method that computes the price that should the route cost
	 * @param legs
	 * @param routeType
	 * @return
	 */
	public static int computeRoutePrices(List<Leg> legs, String routeType) {
		if(routeType.equals("BikeOnly")) return Integer.MAX_VALUE;				//condition that will remove all the routes that are bike only
		int price = interchangeAlpha * (legs.size()-1);
		if(routeType.contains("PT")) price = PTcharge;
		for (Leg l : legs) {
			if (l.getType().equals("CAR")) {
				price += (l.getDistance() * carPerKM);
			} else if (l.getType().equals("TAXI")) {
				price += (l.getDistance() * taxiPerKM);
			} else if (l.getType().equals("SHARED_BIKE")) {
				price += l.getRentStation().getPrice();
			}
			price += (l.getDuration() * timeAlpha) + (l.getEmissions() * emissionsAlpha) + (l.getPhysicalEffort() * physicalEffortAlpha);
			
		}
		System.out.println("Type: "+routeType + "for: " + price);
		return price;
	}
	
}
