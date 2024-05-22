package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * Interface for geometric objects that can be intersected by a ray.
 * Provides a method to find intersection points of a ray with the object.
 */
public interface Intersectable {

    /**
     * Finds all intersection points between a given ray and the geometric object.
     *
     * @param ray the ray for which to find the intersections
     * @return a list of points where the ray intersects the object, or null if there are no intersections
     */
    List<Point> findIntersections(Ray ray);
}
