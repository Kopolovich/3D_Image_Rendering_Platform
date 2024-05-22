package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Represents a plane in three-dimensional space.
 */
public class Plane implements Geometry {

    private final Point q; // A point on the plane
    private final Vector normal; // The normal vector to the plane

    /**
     * Constructs a new Plane instance using three points lying on the plane.
     *
     * @param p1 The first point lying on the plane.
     * @param p2 The second point lying on the plane.
     * @param p3 The third point lying on the plane.
     */
    public Plane(Point p1, Point p2, Point p3) {
        //calculating normal to plane
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        normal = v1.crossProduct(v2).normalize();
        q = p1; // Choosing one of the points as a reference point for the plane
    }

    /**
     * Constructs a new Plane instance using a reference point and a normal vector.
     *
     * @param q A point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize(); // Normalize the normal vector
    }

    /**
     * Returns the normal vector to the plane.
     *
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
