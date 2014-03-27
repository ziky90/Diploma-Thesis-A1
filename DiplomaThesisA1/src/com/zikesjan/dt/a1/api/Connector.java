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

import com.zikesjan.dt.a1.bikeshare.BikeShare;
import com.zikesjan.dt.a1.bikeshare.BikeShareData;
import com.zikesjan.dt.a1.model.request.Request;
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
	public static List<Route> getInfo(Date date, Request data) {
		
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
				List<Route> l = new LinkedList<Route>();
				for(int i = 0; i<plans.length(); i++){
					JSONObject obj = plans.getJSONObject(i);
					JSONObject prop = obj.getJSONObject("properties");
					l.add(new Route(obj.getString("description"), prop.getInt("emissions"), prop.getInt("physicalEffort"), null, null, prop.getInt("duration"), prop.getInt("distance"), buildLeg(obj.getJSONArray("journeyLegs")))); //FIXME implement also legs
				}
				return l;
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
			result.add(new Leg(obj.getString("modeOfTransport"), obj.getInt("journeyLegID"), prop.getInt("emissions"), prop.getInt("distance"), prop.getInt("duration"), prop.getInt("physicalEffort")));
			if(obj.getString("modeOfTransport").equals("SHARED_BIKE")){
				BikeShareData bsd = BikeShareData.getInstance();
				JSONObject origin = obj.getJSONObject("nodes").getJSONObject(obj.getLong("originID")+"");
				JSONObject destination = obj.getJSONObject("nodes").getJSONObject(obj.getLong("originID") + "");
				bsd.addBikeShare(new BikeShare(new Point(origin.getInt("latE6"), origin.getInt("lonE6")), 10, 100)); //TODO deal with the pricing mechanism
				bsd.addBikeShare(new BikeShare(new Point(destination.getInt("latE6"), destination.getInt("lonE6")), 10, 100));
			}
			System.out.println(obj.getString("modeOfTransport"));
		}
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
