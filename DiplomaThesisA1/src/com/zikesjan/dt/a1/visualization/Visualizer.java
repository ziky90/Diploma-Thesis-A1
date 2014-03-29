package com.zikesjan.dt.a1.visualization;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.zikesjan.dt.a1.environment.bikeshare.BikeShare;
import com.zikesjan.dt.a1.model.route.AtomicTravelAction;
import com.zikesjan.dt.a1.model.route.Leg;
import com.zikesjan.dt.a1.model.route.Route;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

public class Visualizer {

	private static final Kml kml = new Kml();
	private static Document document = kml.createAndSetDocument().withName("BikeShareMilano");
	private static HashMap<BikeShare, Placemark> map = new HashMap<>();
	
	public static void visualizeBikeshares(Set<BikeShare> bikeShares){
		for(BikeShare bs : bikeShares){
			//TODO build placemarks
			document.createAndAddPlacemark().withName(bs.getPrice() + " | " + bs.getAvailableItems()).withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(bs.getPosition().getLonDouble(), bs.getPosition().getLatDouble());
		}
	}
	
	public static void visualizeRoute(Route route){
		//TODO perform the visualization
		//document.createAndAddPlacemark().withName(route.getDescription() + " | " + route.getPrice()).withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(route., bs.getPosition().getLatDouble());
		List<Coordinate> wayPoints = new LinkedList<>();
		for(Leg l : route.getLegs()){
			for(AtomicTravelAction ata : l.getAtomicTravelActions()){
				wayPoints.add(new Coordinate(ata.getOrigin().getLonDouble(), ata.getOrigin().getLatDouble()));
				wayPoints.add(new Coordinate(ata.getDestination().getLonDouble(), ata.getOrigin().getLatDouble()));
				/*document.createAndAddPlacemark().createAndSetLineString().createAndSetCoordinates().add(new Coordinate(ata.getOrigin().getLonDouble(), ata.getOrigin().getLatDouble()));
				document.createAndAddPlacemark().createAndSetLineString().createAndSetCoordinates().add(new Coordinate(ata.getDestination().getLonDouble(), ata.getOrigin().getLatDouble()));*/
			}
		}
		document.createAndAddPlacemark().createAndSetLineString().withExtrude(Boolean.TRUE).createAndSetCoordinates().addAll(wayPoints);
	}
	
	public static void resetBikeshares(Route r){
		//TODO reset the visualization of the bike shares
		//TODO get all the placemarks from the map
		for(Leg l : r.getLegs()){
			//TODO change values at the changed rental stations
		}
	}
	
	public static void saveToFile(){
		try {
			kml.marshal(new File("BikeShareMilano.kml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
