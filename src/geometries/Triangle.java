package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Represents a triangle in three-dimensional space.
 * Extends the Polygon class.
 */
public class Triangle extends Polygon {
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1,p2,p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
