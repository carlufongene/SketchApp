
import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, updated Fall 2016
 */

public class Polyline implements Shape {
	// TODO: YOUR CODE HERE
	private final List<Point> points;
	private Color color;
	/**
	 * Single initial point in polyline
	 */
	public Polyline(Point p1, Color color) {
		this.points = new ArrayList<Point>();
		points.add(p1); this.color = color;
	}
	/**
	 * Complete polyline
	 */
	public Polyline(List<Point> points, Color color) {
		this.points = points;
		this.color = color;
	}
	/**
	 * Incrementally extend polyline
	 */
	public void addPoint(Point point) {
		points.add(point);
	}
	@Override
	public void moveBy(int dx, int dy) {
		for (Point p : points) {
			p.x += dx;
			p.y += dy;
		}
	}
	@Override
	public Color getColor() {
		return color;
	}
	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	@Override
	public boolean contains(int x, int y) {
		for (int i=0; i<points.size()-1; i++) {
			Point p1 = points.get(i), p2 = points.get(i+1);
			if (Segment.pointToSegmentDistance(x, y, p1.x, p1.y, p2.x, p2.y)
					<= 3) return true;
		}
		return false;
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		for (int i=0; i<points.size()-1; i++) {
			g.drawLine(points.get(i).x, points.get(i).y, points.get(i+1).x,
					points.get(i+1).y);
		}
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("polyline");
		for (Point p : points) {
			s.append(" ").append(p.x).append(" ").append(p.y);
		}
		return s + " " + color.getRGB();
	}
}
