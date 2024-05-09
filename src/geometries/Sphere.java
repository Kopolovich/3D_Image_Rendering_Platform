package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a sphere in three-dimensional space.
 */
public class Sphere extends RadialGeometry {
    private final Point center; // The center point of the sphere

    /**
     * Constructs a new Sphere instance with the specified radius and center point.
     *
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        return null; // To be implemented
    }
}

