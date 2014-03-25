package com.zikesjan.dt.a1.generator;

import java.util.Random;

import com.zikesjan.dt.a1.model.route.Point;

/**
 * Class for generating points in the map
 * @author zikesjan
 *
 */
public class PointGenerator {

	
	/**
	 * method for generating the random point within the square
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
	 * method for generating the random point within the circle
	 * @param center
	 * @param radius
	 * @return
	 */
	public static Point generatePointInCircle(Point center, int radius){
		//TODO implement
		return null;
	}
}
