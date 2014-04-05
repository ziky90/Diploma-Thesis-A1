package com.zikesjan.dt.a1.generator;

import java.util.Random;

import org.apache.commons.math3.distribution.BetaDistribution;

import com.zikesjan.dt.a1.model.route.Point;

/**
 * Class for generating points in the map
 * @author zikesjan
 *
 */
public class PointGenerator {

	private static final double ALPHA = 5;
	private static final double BETA = 5;
	private static final double ALPHA2 = 1.5;
	private static final double BETA2 = 1.5;
	
	/**
	 * method for generating the random point uniformly within the square
	 * @param west
	 * @param east
	 * @param north
	 * @param south
	 * @return random point
	 */
	public static Point generatePointinSquare(int west, int east, int north, int south){
		Random r = new Random();
		return new Point(r.nextInt(north - south) + north, r.nextInt(east-west)+east);
	}
	
	/**
	 * method for generating the random point uniformly within the circle
	 * @param center
	 * @param radius in degrees of lat lon
	 * @return
	 */
	public static Point generatePointInCircle(Point center, float radius){
		Random r = new Random();
		float angle = 2*(float)Math.PI*r.nextFloat();
		float distance = radius * (r.nextFloat());
		float x = (float)Math.cos(angle) * distance;
		float y = (float)Math.sin(angle) * distance;
		return new Point(center.getLat()+(int)(y*1E6), center.getLon()+(int)(x*1E6));
	}
	
	/**
	 * method that returns point in the circle from the beta distribution
	 * @param center
	 * @param radius
	 * @return
	 */
	public static Point generatePointInCircleFromBeta(Point center, float radius){
		Random r = new Random();
		float angle = 2*(float)Math.PI*r.nextFloat();
		BetaDistribution beta=new BetaDistribution(ALPHA, BETA);		//Beta distribution roughly representing the population density in the city
		float distance = 2*radius* (float) beta.inverseCumulativeProbability(Math.random());
		while(distance > radius){
			distance = 2*radius* (float) beta.inverseCumulativeProbability(Math.random());
		}
		float x = (float)Math.cos(angle) * distance;
		float y = (float)Math.sin(angle) * distance;
		return new Point(center.getLat()+(int)(y*1E6), center.getLon()+(int)(x*1E6));
	}
	
	/**
	 * method that returns point from the pseudo mixture of betas
	 * @param center
	 * @param radius1
	 * @param radius2
	 * @return
	 */
	public static Point generatePointInCircleFromBetaMixture(Point center, float radius1, float radius2){
		Random r = new Random();
		float angle = 2*(float)Math.PI*r.nextFloat();
		BetaDistribution beta1 = new BetaDistribution(ALPHA, BETA);		//Beta distribution roughly representing the population density in the city
		float distance1 = (float) beta1.inverseCumulativeProbability(Math.random());
		while(distance1 > radius1){
			distance1 = 2*radius1* (float) beta1.inverseCumulativeProbability(Math.random());
		}
		BetaDistribution beta2 = new BetaDistribution(ALPHA2, BETA2);
		float distance2 = (float) beta2.inverseCumulativeProbability(Math.random());
		while(distance2 > radius2){
			distance2 = 2*radius2* (float) beta2.inverseCumulativeProbability(Math.random());
		}
		float x = (float)Math.cos(angle) * max(distance1, distance2);
		float y = (float)Math.sin(angle) * max(distance1, distance2);
		return new Point(center.getLat()+(int)(y*1E6), center.getLon()+(int)(x*1E6));
	}
	
	private static float max(float x, float y){
		if(x > y) return x;
		return y;
	}
}
