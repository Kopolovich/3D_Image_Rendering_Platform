/**
 * Represents a 3D point in Cartesian coordinate system.
 */
package primitives;

import java.util.Objects;

public class Point {
    /**
     * A constant representing the origin point (0, 0, 0).
     */
    public static final Point ZERO = new Point(Double3.ZERO);

    /**
     * The underlying coordinates of this point.
     */
    protected final Double3 xyz;

    /**
     * Constructs a new point with the given coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param z The z-coordinate of the point.
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }

    /**
     * Constructs a new point with the given Double3 coordinates.
     *
     * @param xyz The Double3 object containing the coordinates of the point.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    /**
     * Calculates the vector between this point and the given point.
     *
     * @param p1 The other point.
     * @return A vector representing the difference between the two points.
     */
    public Vector subtract(Point p1) {
        return new Vector(xyz.subtract(p1.xyz));
    }

    /**
     * Calculates a new point by adding a vector to this point.
     *
     * @param v1 The vector to add.
     * @return A new point calculated by adding the vector to this point.
     */
    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }

    /**
     * Calculates the squared distance between this point and the given point.
     *
     * @param p1 The other point.
     * @return The squared distance between the two points.
     */
    public double distanceSquared(Point p1) {
        return  (p1.xyz.d1 - this.xyz.d1) * (p1.xyz.d1 - this.xyz.d1)
                + (p1.xyz.d2 - this.xyz.d2) * (p1.xyz.d2 - this.xyz.d2)
                + (p1.xyz.d3 - this.xyz.d3) * (p1.xyz.d3 - this.xyz.d3);
    }

    /**
     * Calculates the distance between this point and the given point.
     *
     * @param p1 The other point.
     * @return The distance between the two points.
     */
    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }
}
