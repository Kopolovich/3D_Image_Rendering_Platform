package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;
import java.util.Objects;


/**
 * Abstract class representing an intersectable object.
 */
public abstract class Intersectable {

    /**
     * Nested class representing a geometric point along with its associated geometry.
     */
    public static class GeoPoint {
        /**
         * The geometry associated with this point.
         */
        public Geometry geometry;

        /**
         * The point in space.
         */
        public Point point;

        /**
         * Constructs a GeoPoint with the specified geometry and point.
         *
         * @param geometry the geometry associated with this point
         * @param point the point in space
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Finds the intersections of the given ray with the intersectable object.
     *
     * @param ray the ray to intersect with the object
     * @return a list of points where the ray intersects the object, or null if there are no intersections
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the intersections of the given ray with the intersectable object, returning them as GeoPoints.
     *
     * @param ray the ray to intersect with the object
     * @return a list of GeoPoints where the ray intersects the object, or null if there are no intersections
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * Finds the intersections of the given ray with the intersectable object.
     * This method is to be implemented by subclasses to provide the actual intersection logic.
     *
     * @param ray the ray to intersect with the object
     * @return a list of GeoPoints where the ray intersects the object
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
