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

    @Override
    public Vector getNormal(Point point) {
        return null; // To be implemented
    }
}

