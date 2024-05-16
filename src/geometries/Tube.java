package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a tube in three-dimensional space.
 * A tube is a cylindrical surface with a circular cross-section.
 */
public class Tube extends RadialGeometry {
    protected final Ray axis; // The axis of the tube

    /**
     * Constructs a new Tube instance with the specified radius and axis.
     *
     * @param radius The radius of the tube.
     * @param axis   The axis of the tube.
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    /**
     * calculates normal to tube by given point
     * @param point The point on the geometry's surface.
     * @return normal to tube by given point
     */
    @Override
    public Vector getNormal(Point point) {
        double t = (point.subtract(axis.getHead())).dotProduct(axis.getDirection());
        // If the point is exactly at the head of the ray (t == 0),
        // the normal is simply the normalized vector from the head to the point
        if (t == 0) {
            return point.subtract(axis.getHead()).normalize();
        }
        Point o = axis.getHead().add(axis.getDirection().scale(t));
        return point.subtract(o).normalize();
    }
}

