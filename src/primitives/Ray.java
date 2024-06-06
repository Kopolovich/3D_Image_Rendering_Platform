/**
 * Represents a ray in 3D space, defined by a starting point and a direction vector.
 */
package primitives;

import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;

public class Ray {
    /**
     * The starting point of the ray.
     */
    private final Point head;

    /**
     * The direction vector of the ray.
     */
    private final Vector direction;

    /**
     * Constructs a new ray with the given starting point and direction vector.
     *
     * @param head      The starting point of the ray.
     * @param direction The direction vector of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    public Point getHead() {
        return head;
    }

    public Vector getDirection() {
        return direction;
    }

    /**
     * Gets a point along the ray at a given distance from the head.
     *
     * @param t the distance from the head along the ray direction
     * @return the point at the specified distance from the head, or the head if the distance is zero
     */
    public Point getPoint(double t){
        if(isZero(t))
            return head;
        return head.add(direction.scale(t));

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

    /**
     * Finds the closest point to the ray's head from a list of points.
     *
     * @param points the list of points
     * @return the closest point to the ray's head, or null if the list is empty or null
     */
    public Point findClosestPoint(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return null;
        }

        Point closestPoint = null;
        double closestDistance = Double.MAX_VALUE;

        for (Point point : points) {
            double distance = head.distanceSquared(point);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = point;
            }
        }
        return closestPoint;
    }
}