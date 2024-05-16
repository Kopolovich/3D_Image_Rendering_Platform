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

        // Calculate the projection of the point on the cylinder's axis
        double t = point.subtract(axis.getHead()).dotProduct(axis.getDirection());

        // Check if the point is on the bottom base
        if (t <= 0) {
            return axis.getDirection().scale(-1).normalize(); // Normal is opposite to the direction vector
        }

        // Check if the point is on the top base
        if (t >= height) {
            return axis.getDirection().normalize(); // Normal is the direction vector
        }

        // The point is on the curved surface of the cylinder
        Point o = axis.getHead().add(axis.getDirection().scale(t));
        return point.subtract(o).normalize();
    }
}

