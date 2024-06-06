package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a collection of geometric objects that can be intersected by a ray.
 * Implements the Intersectable interface.
 */
public class Geometries implements Intersectable {

    private final List<Intersectable> geometries = new LinkedList<>();

    /**
     * Default constructor. Creates an empty collection of geometries.
     */
    public Geometries() {
    }

    /**
     * Constructor that initializes the collection with given geometries.
     *
     * @param geometries the initial geometries to add to the collection
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Adds multiple geometries to the collection.
     *
     * @param geometries the geometries to add
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(this.geometries, geometries);
    }

    /**
     * Finds all intersection points between a given ray and the geometries in the collection.
     *
     * @param ray the ray for which to find the intersections
     * @return a list of points where the ray intersects any of the geometries, or null if there are no intersections
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersectionPoints = null;

        for (Intersectable geometry : geometries) {
            List<Point> temp = geometry.findIntersections(ray);
            if (temp != null) {
                if (intersectionPoints == null) {
                    intersectionPoints = new LinkedList<>();
                }
                intersectionPoints.addAll(temp);
            }
        }

        return intersectionPoints;
    }

}
