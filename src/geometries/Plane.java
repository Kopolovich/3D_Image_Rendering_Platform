package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a plane in three-dimensional space.
 */
public class Plane extends Geometry {

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

    /**
     * Returns the normal vector to the plane by given point
     *
     * @param point a point on the geometry's surface.
     * @return The normal vector to the plane by given point
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * Finds the intersection points of a ray with the plane.
     *
     * @param ray the ray for which to find the intersections
     * @return A list containing the intersection points, or null if there are no intersections.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getHead();  // The origin point of the ray
        Vector v = ray.getDirection();  // The direction vector of the ray

        if(q.equals(p0))  // If the ray's origin lies exactly on the plane and the ray is parallel to the plane
            return null;

        Vector q0MinusP0 = q.subtract(p0);  // Vector from p0 to q

        double nv = normal.dotProduct(v);  // Dot product of the normal and the ray direction

        // If the ray is parallel to the plane (dot product is zero), return null
        if (isZero(nv)) {
            return null;
        }

        double nQMinusP0 = normal.dotProduct(q0MinusP0);  // Dot product of the normal and q0MinusP0
        double t = alignZero(nQMinusP0 / nv);  // Calculate t

        // If t <= 0, the intersection point is behind the ray's origin or on the ray's origin, return null
        if (t <= 0) {
            return null;
        }

        // Calculate the intersection point
        Point intersectionPoint = ray.getPoint(t);

        // Return the intersection point as a list
        return List.of(intersectionPoint);
    }

    /**
     * Finds the intersections of the given ray with the current geometry.
     *
     * @param ray The ray to intersect with the geometry.
     * @return A list containing a single {@link GeoPoint} representing the intersection point, or null if there is no intersection.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getHead();  // The origin point of the ray
        Vector v = ray.getDirection();  // The direction vector of the ray

        if(q.equals(p0))  // If the ray's origin lies exactly on the plane and the ray is parallel to the plane
            return null;

        Vector q0MinusP0 = q.subtract(p0);  // Vector from p0 to q

        double nv = normal.dotProduct(v);  // Dot product of the normal and the ray direction

        // If the ray is parallel to the plane (dot product is zero), return null
        if (isZero(nv)) {
            return null;
        }

        double nQMinusP0 = normal.dotProduct(q0MinusP0);  // Dot product of the normal and q0MinusP0
        double t = alignZero(nQMinusP0 / nv);  // Calculate t

        // If t <= 0, the intersection point is behind the ray's origin or on the ray's origin, return null
        if (t <= 0) {
            return null;
        }

        // Calculate the intersection point
        GeoPoint intersection = new GeoPoint(this, ray.getPoint(t)) ;

        // Return the intersection GeoPoint as a list
        return List.of(intersection);
    }
}
