package com.zikesjan.dt.a1.api;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.entity.StringEntity;

import com.zikesjan.dt.a1.model.request.Request;

/**
 * Class containing various static operations methods, that are not related to any specific class
 * @author zikesjan
 *
 */
public class Util {

	/**
	 * class that creates JSON from request and date
	 * @param date
	 * @param request
	 * @return
	 */
	public static StringEntity buildJson(Date date, Request request) {
		StringEntity json = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		String formattedTime = sdf.format(date);
		try {
			json = new StringEntity(
					"{\"departureDateTime\":\""
							+ formattedTime
							+ "+02:00\",\"requirements\": { \"maxNumberOfInterchanges\":"
							+ request.getMaxInterchanges()
							+ ",\"maxWalkingDistance\":"
							+ request.getMaxWalking()
							+ ",\"allowedModesOfTransports\":["
							+ listAsJsonString(request.getAllowedTransport())
							+ "] }, \"description\": \"ShortC1C2PeakRebSun\", \"userID\" : \"15c3e616-871c-41e9-a04a-3a52bfa0ab02\", \"origin\" :"
							+ "{ \"latE6\" : "
							+ request.getOrigin().getLat()
							+ ", \"lonE6\" : "
							+ request.getOrigin().getLon()
							+ " }, \"destination\" : { \"latE6\" : "
							+ request.getDestination().getLat()
							+ ", \"lonE6\" : "
							+ request.getDestination().getLon()
							+ " }, \"city\" : \"MILAN\", \"clientType\" : \"TEST\" }");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * method that converts list of strings to the JSON array
	 * @param list
	 * @return
	 */
	private static String listAsJsonString(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append("\"").append(s).append("\", ");
		}
		sb.deleteCharAt(sb.length() - 2);
		return sb.toString();
	}

}
