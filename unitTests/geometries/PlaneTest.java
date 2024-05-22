package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testFindIntersections() {
    }
}