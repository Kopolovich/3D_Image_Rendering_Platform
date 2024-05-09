package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a cylinder in three-dimensional space.
 * Extends the Tube class and adds a height dimension.
 */
public class Cylinder extends Tube {
    private final double height;

    /**
     * Constructs a new Cylinder instance with the specified radius, axis, and height.
     *
     * @param radius The radius of the cylinder.
     * @param axis   The axis of the cylinder.
     * @param height The height of the cylinder.
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        return null; // To be implemented
    }
}
