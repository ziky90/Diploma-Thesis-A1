package com.zikesjan.a1.generator;

import java.util.Random;

import com.zikesjan.a1.model.route.Point;

public class Generator {

	public static Point generatePoint(int west, int east, int north, int south){
		Random r = new Random();
		return new Point(r.nextInt(north - south) + north, r.nextInt(east-west)+east);
	}
}
