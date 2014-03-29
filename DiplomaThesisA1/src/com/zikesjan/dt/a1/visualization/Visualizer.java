package com.zikesjan.dt.a1.visualization;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

import com.zikesjan.dt.a1.bikeshare.BikeShare;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;

public class Visualizer {

	public static void visualize(Set<BikeShare> bikeShares){
		final Kml kml = new Kml();
		Document document = kml.createAndSetDocument().withName("BikeShareMilano");
		
		for(BikeShare bs : bikeShares){
			document.createAndAddPlacemark().withName(bs.getPrice() + " | " + bs.getCapacity()).withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(bs.getPosition().getLonDouble(), bs.getPosition().getLatDouble());
		}
		
		try {
			kml.marshal(new File("BikeShareMilano.kml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
