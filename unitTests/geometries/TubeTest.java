package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    /**
     * Test method for Tube getNormal.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test calculation of normal vector for a point on the surface of the tube
        Tube tube = new Tube(2, new Ray(new Point(0,0,0), new Vector(1,0,0)));
        assertEquals(new Vector(0, 1, 0),
                    tube.getNormal(new Point(2,2,0)),
                    "ERROR: tube getNormal() does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: Test calculation of normal vector for a point directly opposite the ray's origin (forming a right angle with the axis)
        assertEquals(new Vector(0, 1, 0),
                tube.getNormal(new Point(0, 2, 0)),
                "ERROR: tube getNormal() does not work correctly at the boundary point");
    }

    @Test
    void testFindIntersections() {
    }
}