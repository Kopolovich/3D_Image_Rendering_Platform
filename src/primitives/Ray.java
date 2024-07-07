/**
 * Represents a ray in 3D space, defined by a starting point and a direction vector.
 */
package primitives;

import java.util.List;

import geometries.Intersectable.GeoPoint;


import static primitives.Util.isZero;

public class Ray {
    private static final double DELTA = 0.1;
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

    /**
     * Constructs a new ray with the given starting point, direction vector, and normal vector.
     * The head of the ray is adjusted slightly in the direction of the normal vector to avoid self-intersections.
     *
     * @param head      The starting point of the ray.
     * @param direction The direction vector of the ray.
     * @param normal    The normal vector of the geometry that the ray is intersecting.
     */
    public Ray(Point head, Vector direction, Vector normal)//con and move in a given direction
    {
        this.direction = direction.normalize();
        if (isZero(direction.dotProduct(normal))) {
            this.head = head;
        } else if (direction.dotProduct(normal) > 0)
            this.head = head.add(normal.scale(DELTA));
        else
            this.head = head.add(normal.scale(-DELTA));
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
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;

    }

    /**
     * Finds the closest {@link GeoPoint} from a list of GeoPoints to the origin of the ray.
     *
     * @param geoPoints The list of GeoPoints to search through.
     * @return The closest GeoPoint to the ray's origin, or null if the list is null or empty.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints){
        if (geoPoints == null || geoPoints.isEmpty()) {
            return null;
        }

        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;

        for (GeoPoint geoPoint : geoPoints) {
            double distance = head.distanceSquared(geoPoint.point);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = geoPoint;
            }
        }
        return closestPoint;
    }
}