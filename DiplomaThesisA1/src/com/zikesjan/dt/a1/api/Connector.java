package com.zikesjan.dt.a1.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zikesjan.dt.a1.environment.bikeshare.BikeShareUtil;
import com.zikesjan.dt.a1.model.request.Request;
import com.zikesjan.dt.a1.model.route.AtomicTravelAction;
import com.zikesjan.dt.a1.model.route.Leg;
import com.zikesjan.dt.a1.model.route.Point;
import com.zikesjan.dt.a1.model.route.Route;

/**
 * Class that is dealing with the API calls
 * @author zikesjan
 *
 */
public class Connector {

	private static final String apiURL = "http://transport.felk.cvut.cz/wp5-api/journeyPlanning/planJourney";

	/**
	 * method that calls the planers API and returns the possible routes
	 * 
	 * @return
	 */
	public static PriorityQueue<Route> getInfo(Date date, Request data) {
		
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		try {

			HttpPost post = new HttpPost(new URI(apiURL));
			post.setHeader("Content-type", "application/json");
			post.setEntity(Util.buildJson(date, data));
			HttpResponse response = httpclient.execute(post);
			StatusLine statusLine = response.getStatusLine();			
			
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				
				
				HttpEntity entity = response.getEntity();

				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				
				JSONObject json = null;
				try {
					json = new JSONObject(result);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				instream.close();
				
				JSONArray plans = json.getJSONArray("journeyPlans");
				PriorityQueue<Route> routes = new PriorityQueue<>();
				for(int i = 0; i<plans.length(); i++){
					JSONObject obj = plans.getJSONObject(i);
					JSONObject prop = obj.getJSONObject("properties");
					routes.add(new Route(obj.getString("description"), prop.getInt("emissions"), prop.getInt("physicalEffort"), null, null, prop.getInt("duration"), prop.getInt("distance"), buildLeg(obj.getJSONArray("journeyLegs")))); //FIXME implement also legs
				}
				return routes;
			} else if (statusLine.getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
				return null;
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		return null;
	}
	
	/**
	 * building leg from the JSON of the leg format
	 * @param json
	 * @return
	 */
	private static List<Leg> buildLeg(JSONArray json){
		List<Leg> result = new LinkedList<Leg>();
		for(int i = 0; i<json.length(); i++){
			JSONObject obj = json.getJSONObject(i);
			JSONObject prop = obj.getJSONObject("properties");
			JSONArray atomic = obj.getJSONArray("atomicTravelActions");
			List<AtomicTravelAction> atomicActions = new LinkedList<>();
			for(int j = 0; j < atomic.length(); j++){
				JSONObject current = atomic.getJSONObject(j); 
				JSONObject from = obj.getJSONObject("nodes").getJSONObject(current.getLong("originID")+"");
				JSONObject to = obj.getJSONObject("nodes").getJSONObject(current.getLong("destinationID")+"");
				atomicActions.add(new AtomicTravelAction(new Point(from.getInt("latE6"), from.getInt("lonE6")), new Point(to.getInt("latE6"), to.getInt("lonE6")), current.getInt("duration"), current.getInt("distance")));
			}
			Leg leg = new Leg(obj.getString("modeOfTransport"), obj.getInt("journeyLegID"), prop.getInt("emissions"), prop.getInt("distance"), prop.getInt("duration"), prop.getInt("physicalEffort"), atomicActions);
			if(obj.getString("modeOfTransport").equals("SHARED_BIKE")){
				JSONObject origin = obj.getJSONObject("nodes").getJSONObject(obj.getLong("originID")+"");
				JSONObject destination = obj.getJSONObject("nodes").getJSONObject(obj.getLong("originID") + "");
				leg.setRentStation(BikeShareUtil.saveBikeStation(new Point(origin.getInt("latE6"), origin.getInt("lonE6"))));
				leg.setReturnStation(BikeShareUtil.saveBikeStation(new Point(destination.getInt("latE6"), destination.getInt("lonE6"))));
			}
			result.add(leg);
			//System.out.println(obj.getString("modeOfTransport"));
		}
		//System.out.println(result.size());
		return result;
	}

	/**
	 * method for converting stream to string
	 * 
	 * @param is
	 * @return
	 */
	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
}
