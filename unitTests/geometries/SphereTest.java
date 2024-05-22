package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    double DELTA = 0.0001;

    /**
     * Test method for Sphere getNormal.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test calculation of normal vector for a point on the surface of the sphere
        Sphere sphere = new Sphere(5, new Point(0,0,0));
        Vector normal = sphere.getNormal(new Point(5,0,0));
        assertEquals(new Vector(1,0,0),
                    normal,
                    "ERROR: sphere getNormal() does not work correctly");
        // ensure |result| = 1
        assertEquals(1,
                    normal.length(),
                    DELTA,
                    "Sphere's normal is not a unit vector");
    }

    @Test
    void testFindIntersections() {
    }
}