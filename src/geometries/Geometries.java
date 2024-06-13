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
public class Geometries extends Intersectable {

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
     * Finds the intersections of the given ray with all geometries in this composite.
     *
     * @param ray The ray to intersect with the geometries.
     * @return A list of {@link GeoPoint} objects representing the intersection points, or null if there are no intersections.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersectionPoints = null;

        for (Intersectable geometry : geometries) {
            List<GeoPoint> temp = geometry.findGeoIntersections(ray);
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
