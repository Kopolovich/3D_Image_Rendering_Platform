package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void testGetNormal() {
        // Create a cylinder with a radius of 2, height of 10, and axis along the X-axis
        Cylinder cylinder = new Cylinder(2, new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 10);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test normal vector calculation for a point on the side surface of the cylinder
        Point point1 = new Point(2, 2, 0);
        Vector expectedNormal1 = new Vector(0, 1, 0).normalize();
        assertEquals(expectedNormal1, cylinder.getNormal(point1), "ERROR: cylinder getNormal() does not work correctly for a general point on the curved surface");

        // =============== Boundary Values Tests ==================
        // TC11: Test normal vector calculation for a point on the top base at the center
        Point point3 = new Point(10, 0, 0);
        Vector expectedNormal3 = new Vector(1, 0, 0).normalize();
        assertEquals(expectedNormal3, cylinder.getNormal(point3), "ERROR: cylinder getNormal() does not work correctly for a point on the top base center");

        // TC12: Test normal vector calculation for a point on the edge of the bottom base
        Point point4 = new Point(0, 2, 0);
        Vector expectedNormal4 = new Vector(-1, 0, 0).normalize();
        assertEquals(expectedNormal4, cylinder.getNormal(point4), "ERROR: cylinder getNormal() does not work correctly for a point on the bottom base edge");

        // TC13: Test normal vector calculation for a point on the edge of the top base
        Point point5 = new Point(10, 2, 0);
        Vector expectedNormal5 = new Vector(1, 0, 0).normalize();
        assertEquals(expectedNormal5, cylinder.getNormal(point5), "ERROR: cylinder getNormal() does not work correctly for a point on the top base edge");
    }
}