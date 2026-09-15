package com.qrazy.bean;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class Star {

	public Star() {

		start = new Point2D.Float(); // store start point 
		p = new GeneralPath(GeneralPath.WIND_NON_ZERO);

		//	    createStar(); 

	}

	// Create the path from start 

	void createStar() {

		Point2D.Float point = start;

		p.moveTo(point.x, point.y);

		p.lineTo(point.x + 4.0f, point.y - 1.0f); // Line from start to A 

		point = (Point2D.Float) p.getCurrentPoint();

		p.lineTo(point.x + 1.0f, point.y - 4.0f); // Line from A to B 

		point = (Point2D.Float) p.getCurrentPoint();

		p.lineTo(point.x + 1.0f, point.y + 4.0f); // Line from B to C 

		point = (Point2D.Float) p.getCurrentPoint();

		p.lineTo(point.x + 4.0f, point.y + 1.0f); // Line from C to D 

		point = (Point2D.Float) p.getCurrentPoint();

		p.lineTo(point.x - 4.0f, point.y + 1.0f); // Line from D to E 

		point = (Point2D.Float) p.getCurrentPoint();

		p.lineTo(point.x - 1.0f, point.y + 4.0f); // Line from E to F 

		point = (Point2D.Float) p.getCurrentPoint();

		p.lineTo(point.x - 1.0f, point.y - 4.0f); // Line from F to g 

		p.closePath(); // Line from G to start 

	}

	public Shape atLocation(float x, float y) {

		start.setLocation(x, y); // Store new start 

		p.reset(); // Erase current path 

		createStar(); // create new path 

		return p; // Return the path 

	}

	// Make the path available 

	Shape getShape() {

		return p;

	}

	private Point2D.Float start; // Start point for star 

	private GeneralPath p; // Star path 

}
