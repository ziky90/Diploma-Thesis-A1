package com.zikesjan.dt.a1.visualization;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.zikesjan.dt.a1.environment.bikeshare.BikeShare;
import com.zikesjan.dt.a1.model.route.AtomicTravelAction;
import com.zikesjan.dt.a1.model.route.Leg;
import com.zikesjan.dt.a1.model.route.Route;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.TimeStamp;

public class Visualizer {

	private static final Kml kml = new Kml();
	private static Document document = kml.createAndSetDocument().withName(
			"BikeShareMilano");
	private static final int visualizationInterval = 2 * 60 * 1000;
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	private static Date currentDate = new Date();

	public static void visualizeBikeshares(Collection<BikeShare> bikeShares) {
		Folder sharingPoints = document.createAndAddFolder();
		sharingPoints.withName("sharingPointsInit");
		sharingPoints.withOpen(true);
		TimeStamp ts = new TimeStamp();
		ts.setWhen(dateToString(currentDate));
		sharingPoints.setTimePrimitive(ts);
		for (BikeShare bs : bikeShares) {
			Placemark p = new Placemark();
			p.setName(bs.getAvailableItems() + "/" + bs.getCapacity() + " ||| "
					+ (bs.getCapacity() - bs.getAvailableItems()));
			p.withOpen(true);
			p.createAndSetPoint().addToCoordinates(
					bs.getPosition().getLonDouble(),
					bs.getPosition().getLatDouble());
			sharingPoints.addToFeature(p);
		}
	}

	public static void visualizeRoute(Route route) {
		for (Leg l : route.getLegs()) {
			List<Coordinate> wayPoints = new LinkedList<>();
			for (AtomicTravelAction ata : l.getAtomicTravelActions()) {
				wayPoints.add(new Coordinate(ata.getOrigin().getLonDouble(),
						ata.getOrigin().getLatDouble()));
				wayPoints.add(new Coordinate(ata.getDestination()
						.getLonDouble(), ata.getOrigin().getLatDouble()));
			}
			Placemark p = document.createAndAddPlacemark();
			LineString ls = p.createAndSetLineString();
			ls.withExtrude(Boolean.TRUE);
			ls.createAndSetCoordinates().addAll(wayPoints);
			TimeStamp ts = new TimeStamp();
			ts.setWhen(dateToString(currentDate));
			p.setTimePrimitive(ts);
			if(l.getRentStation()!=null) p.createAndAddStyle().createAndSetLineStyle().withColor("ff0000ff").withWidth(5);
		}
	}

	public static void resetBikeshares(Collection<BikeShare> bikeShares) {
		currentDate.setTime(currentDate.getTime() + visualizationInterval);
		Folder sharingPoints = document.createAndAddFolder();
		sharingPoints.withName("sharingPoints" + dateToString(currentDate));
		sharingPoints.withOpen(false);
		TimeStamp ts = new TimeStamp();
		ts.setWhen(dateToString(currentDate));
		sharingPoints.setTimePrimitive(ts);
		for (BikeShare bs : bikeShares) {
			Placemark p = new Placemark();
			p.setName(bs.getAvailableItems() + "/" + bs.getCapacity() + " ||| "
					+ (bs.getCapacity() - bs.getAvailableItems()));
			p.withOpen(true);
			p.createAndSetPoint().addToCoordinates(
					bs.getPosition().getLonDouble(),
					bs.getPosition().getLatDouble());
			sharingPoints.addToFeature(p);
		}
	}

	public static void saveToFile() {
		try {
			kml.marshal(new File("BikeShareMilano.kml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static String dateToString(Date d) {
		return sdf.format(d);
	}
}
