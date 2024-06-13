package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a triangle in three-dimensional space.
 * Extends the Polygon class.
 */
public class Triangle extends Polygon {
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1,p2,p3);
    }

    /**
     * Finds the intersections of the given ray with the current triangle.
     *
     * @param ray The ray to intersect with the triangle.
     * @return A list of {@link GeoPoint} objects representing the intersection points, or null if there are no intersections.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<Point> intersectionPoints = plane.findIntersections(ray);
        if (intersectionPoints == null)
            return null;
        Point intersectionPoint = intersectionPoints.getFirst();

        // Calculate vectors from ray's head to each vertex of the triangle
        Vector v1 = vertices.get(0).subtract(ray.getHead());
        Vector v2 = vertices.get(1).subtract(ray.getHead());
        Vector v3 = vertices.get(2).subtract(ray.getHead());

        // Calculate normal vectors to the planes formed with the ray direction and the triangle's sides
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        // Calculate dot products of the ray's direction with each normal vector
        double vn1 = ray.getDirection().dotProduct(n1);
        double vn2 = ray.getDirection().dotProduct(n2);
        double vn3 = ray.getDirection().dotProduct(n3);

        // Check if the ray lies in the same plane as any of the triangle's sides
        if (isZero(vn1) || isZero(vn2) || isZero(vn3))
            return null;

        // Check if the ray intersects the triangle by verifying all dot products are either all positive or all negative
        if ((vn1 > 0.0 && vn2 > 0.0 && vn3 > 0.0) || (vn1 < 0.0 && vn2 < 0.0 && vn3 < 0.0))
            return List.of(new GeoPoint(this, intersectionPoint));

        return null;
    }
}
