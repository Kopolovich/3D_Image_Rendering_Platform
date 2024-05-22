package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * An interface representing a geometric shape in three-dimensional space.
 */
public interface Geometry extends Intersectable {

    /**
     * Computes the normal vector at the given point on the geometry's surface.
     *
     * @param point The point on the geometry's surface.
     * @return The normal vector at the given point.
     */
    Vector getNormal(Point point);
}

