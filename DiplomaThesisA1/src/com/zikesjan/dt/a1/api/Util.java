package com.zikesjan.dt.a1.api;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.http.entity.StringEntity;

import com.zikesjan.dt.a1.model.request.Request;

public class Util {

	public static StringEntity buildJson(Date date, Request request) {
		// TODO test this
		StringEntity json = null;
		try {
			json = new StringEntity(
					"{\"departureDateTime\":"
							+ date.toString()
							+ ",\"requirements\": { \"maxNumberOfInterchanges\":"
							+ request.getMaxInterchanges()
							+ ",\"maxWalkingDistance\":\""
							+ request.getMaxWalking()
							+ "\",\"allowedModesOfTransports\":\"["
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
							+ " }, \"city\" : \"MILAN\", \"clientType\" : \"TEST\"\" }");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(json.toString());
		return json;
	}

	private static String listAsJsonString(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(s).append(", ");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

}
