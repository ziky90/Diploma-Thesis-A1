package com.zikesjan.dt.a1.environment;


public class ExperimentEvaluator {

	private static volatile ExperimentEvaluator instance = null;
	public int totalEmissions;
	public int totalPrice;
	public int totalDistance;
	public int totalTravellingTime;
	public int totalPhysicalEffort;
	
	
	private ExperimentEvaluator() {	}
	 
	public static ExperimentEvaluator getInstance() {
		if (instance == null) {
                        synchronized (ExperimentEvaluator.class){
			        if (instance == null) {
                                        instance = new ExperimentEvaluator();
                                }
                        }
		}
		return instance;
	}
	
	public void printStatistics(){
		System.out.println("emissions: " + totalEmissions + " || distance: " + totalDistance + " || time: " + totalTravellingTime + " || effort: " + totalPhysicalEffort + " || price: " + totalPrice);
	}
}
