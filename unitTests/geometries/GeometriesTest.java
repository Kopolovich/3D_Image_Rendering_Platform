package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {


    @Test
    void testFindIntersections() {
        // A ray that will be used for intersection tests
        Ray ray1 = new Ray(new Point(0.25, 0.25, 1), new Vector(0, 0, -1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Some of the shapes but not all intersect with ray
        Geometries geometries = new Geometries(
                new Sphere(1, new Point(5, 5, 5)),
                new Plane(new Point(0, 0, -1), new Vector(0, 0, 1)),
                new Triangle(new Point(0.5, 0.5, 0), new Point(-0.5, 0.5, 0), new Point(0, -0.5, 0))
        );
        assertEquals(2,
                geometries.findIntersections(ray1).size(),
                "There are supposed to be 2 intersection points");

        // =============== Boundary Values Tests ==================
        // TC11: Empty collection
        Geometries geometries1 = new Geometries();
        assertNull(geometries1.findIntersections(ray1), "Empty list of geometries has no intersection points");

        // TC12: No intersection points
        Geometries geometries2 = new Geometries(
                new Sphere(1, new Point(10, 10, 10)),  // Sphere moved far away from the ray
                new Plane(new Point(0, 0, 20), new Vector(0, 0, 1)),  // Plane moved far away from the ray
                new Triangle(new Point(5, 5, 20), new Point(6, 5, 20), new Point(5, 6, 20))  // Triangle moved far away from the ray
        );
        assertNull(geometries2.findIntersections(ray1), "All geometries in collection don't intersect with ray");

        // TC13: Only one shape intersects with ray
        Geometries geometries3 = new Geometries(
                new Sphere(1, new Point(0.25, 0.25, 2)),  // Moved the sphere to be tangent to the ray
                new Plane(new Point(0, 0, -10), new Vector(0, 0, 1)),  // Plane far from the ray
                new Triangle(new Point(5, 5, 5), new Point(6, 5, 5), new Point(5, 6, 5))  // Triangle far from the ray
        );
        assertEquals(1,
                geometries3.findIntersections(ray1).size(),
                "There is exactly one intersection point");

        // TC14: All shapes intersect with ray
        Geometries geometries4 = new Geometries(
                new Sphere(1, new Point(0, 0, 0)),  // This sphere intersects
                new Plane(new Point(0, 0, -1), new Vector(0, 0, 1)),  // This plane intersects
                new Triangle(new Point(0.5, 0.5, 0), new Point(-0.5, 0.5, 0), new Point(0, -0.5, 0))  // This triangle intersects
        );
        assertEquals(4,
                geometries4.findIntersections(ray1).size(),
                "There are supposed to be 4 intersection points");
    }
}