package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

class PlaneTest {
    double DELTA = 0.0001;
    /**
     * Test method for Plane getNormal().
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test calculation of normal vector for a plane defined by three non-collinear points
        Plane plane = new Plane(
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(0, 0, 1)
        );
        assertEquals(new Vector(1, 1, 1).normalize(),
                    plane.getNormal(),
                    "ERROR: plane getNormal() does not work correctly");
        // ensure |result| = 1
        assertEquals(1,
                    plane.getNormal().length(),
                    DELTA,
                    "Tube's normal is not a unit vector");

        // =============== Boundary Values Tests ==================
        // TC11: Test calculation of normal vector for a plane defined by three co-linear points (expecting IllegalArgumentException)
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3)),
                "ERROR: calculating normal for plane using 3 co-lined points does not throw an exception");
        // TC12: Test calculation of normal vector for a plane defined by two coincident points (expecting IllegalArgumentException)
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Vector(1, 1, 1), new Vector(1, 1, 1), new Vector(2, 2, 2)),
                "ERROR: calculating normal for plane using 2 coincided points does not throw an exception");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane plane = new Plane(new Point(1, 1, 1), new Vector(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane (1 point)
        Ray ray1 = new Ray(new Point(0, 0, 0), new Vector(1, 1, 1));
        List<Point> result1 = plane.findIntersections(ray1);
        assertEquals(List.of(new Point(1, 1, 1)), result1, "Ray intersects the plane");

        // TC02: Ray does not intersect the plane (0 points)
        Ray ray2 = new Ray(new Point(0, 0, 0), new Vector(-1, -1, -1));
        assertNull(plane.findIntersections(ray2), "Ray does not intersect the plane");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC11: Ray is parallel to the plane and included in the plane (0 points)
        Ray ray3 = new Ray(new Point(1, 1, 1), new Vector(1, 0, 0));
        assertNull(plane.findIntersections(ray3), "Ray is parallel and included in the plane");

        // TC12: Ray is parallel to the plane and not included in the plane (0 points)
        Ray ray4 = new Ray(new Point(1, 1, 2), new Vector(1, 0, 0));
        assertNull(plane.findIntersections(ray4), "Ray is parallel and not included in the plane");

        // **** Group: Ray is orthogonal to the plane
        // TC21: Ray is orthogonal to the plane and starts before the plane (1 point)
        Ray ray5 = new Ray(new Point(1, 1, 0), new Vector(0, 0, 1));
        List<Point> result2 = plane.findIntersections(ray5);
        assertEquals(List.of(new Point(1, 1, 1)), result2, "Ray is orthogonal and starts before the plane");

        // TC22: Ray is orthogonal to the plane and starts in the plane (0 points)
        Ray ray6 = new Ray(new Point(1, 1, 1), new Vector(0, 0, 1));
        assertNull(plane.findIntersections(ray6), "Ray is orthogonal and starts in the plane");

        // TC23: Ray is orthogonal to the plane and starts after the plane (0 points)
        Ray ray7 = new Ray(new Point(1, 1, 2), new Vector(0, 0, 1));
        assertNull(plane.findIntersections(ray7), "Ray is orthogonal and starts after the plane");

        // **** Group: Ray is neither orthogonal nor parallel to the plane and begins at the plane
        // TC31: Ray is neither orthogonal nor parallel to and begins at the plane (0 points)
        Ray ray8 = new Ray(new Point(1, 1, 1), new Vector(1, 1, 1));
        assertNull(plane.findIntersections(ray8), "Ray begins at the plane and is neither orthogonal nor parallel to it");

        // **** Group: Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane
        // TC41: Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane (0 points)
        Ray ray9 = new Ray(new Point(1, 1, 1), new Vector(1, 1, 1));
        assertNull(plane.findIntersections(ray9), "Ray begins at the reference point and is neither orthogonal nor parallel to the plane");
    }
}