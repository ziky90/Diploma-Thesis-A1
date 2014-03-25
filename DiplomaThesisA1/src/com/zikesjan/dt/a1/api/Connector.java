package com.zikesjan.dt.a1.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Iterator;
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

import com.zikesjan.dt.a1.model.request.Request;
import com.zikesjan.dt.a1.model.route.Route;


public class Connector {

	private static final String apiURL = "http://transport.felk.cvut.cz/wp5-api/journeyPlanning/planJourney";

	/**
	 * method for getting the informations about the planes up in the air
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

				// a simple JSON response read
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);

				// a simple JSONObject creation
				JSONObject json = null;
				try {
					json = new JSONObject(result);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				//TODO build the data from the received string
				// closing the input stream will trigger connection release
				instream.close();
				JSONArray plans = json.getJSONArray("journeyPlans");
				List<Route> l = new LinkedList<Route>();
				for(int i = 0; i<plans.length(); i++){
					JSONObject obj = plans.getJSONObject(i);
					JSONObject prop = obj.getJSONObject("properties");
					l.add(new Route(obj.getString("description"), prop.getInt("emissions"), prop.getInt("physicalEffort"), null, null, prop.getInt("duration"), prop.getInt("distance"), null)); //FIXME implement also legs
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
