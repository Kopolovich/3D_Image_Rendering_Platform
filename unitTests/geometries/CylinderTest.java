package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    double DELTA = 0.0001;

    @Test
    void testGetNormal() {
        // Create a cylinder with a radius of 2, height of 10, and axis along the X-axis
        Cylinder cylinder = new Cylinder(2,
                            new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 10);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test normal vector calculation for a point on the side surface of the cylinder
        Point point1 = new Point(2, 2, 0);
        Vector expectedNormal1 = new Vector(0, 1, 0).normalize();
        assertEquals(expectedNormal1,
                    cylinder.getNormal(point1),
                    "ERROR: cylinder getNormal() does not work correctly for a general point on the curved surface");

        // =============== Boundary Values Tests ==================
        // TC11: Test normal vector calculation for a point on the top base at the center
        Point point3 = new Point(10, 0, 0);
        Vector expectedNormal3 = new Vector(1, 0, 0).normalize();
        assertEquals(expectedNormal3,
                    cylinder.getNormal(point3),
                    "ERROR: cylinder getNormal() does not work correctly for a point on the top base center");

        // TC12: Test normal vector calculation for a point on the edge of the bottom base
        Point point4 = new Point(0, 2, 0);
        Vector expectedNormal4 = new Vector(-1, 0, 0).normalize();
        assertEquals(expectedNormal4,
                    cylinder.getNormal(point4),
                    "ERROR: cylinder getNormal() does not work correctly for a point on the bottom base edge");

        // TC13: Test normal vector calculation for a point on the edge of the top base
        Point point5 = new Point(10, 2, 0);
        Vector expectedNormal5 = new Vector(1, 0, 0).normalize();
        assertEquals(expectedNormal5,
                    cylinder.getNormal(point5),
                    "ERROR: cylinder getNormal() does not work correctly for a point on the top base edge");
    }

    /**
     * Test method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Cylinder cylinder = new Cylinder(1.0, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 2.0);


        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray crosses the cylinder (2 points)
        Ray ray1 = new Ray(new Point(-2, 0, 1), new Vector(1, 0, 0));
        List<Point> result1 = cylinder.findIntersections(ray1);
        assertEquals(List.of(new Point(-1, 0, 1), new Point(1, 0, 1)), result1, "Ray crosses the cylinder");

        // TC02: Ray touches the cylinder (1 point)
        Ray ray2 = new Ray(new Point(1, 0, 1), new Vector(1, 0, 0));
        List<Point> result2 = cylinder.findIntersections(ray2);
        assertEquals(List.of(new Point(1, 0, 1)), result2, "Ray touches the cylinder");

        // TC03: Ray passes outside the cylinder (0 points)
        Ray ray3 = new Ray(new Point(-2, 0, 1), new Vector(-1, 0, 0));
        assertNull(cylinder.findIntersections(ray3), "Ray passes outside the cylinder");

        // TC04: Ray starts inside the cylinder (1 point)
        Ray ray4 = new Ray(new Point(0.5, 0, 1), new Vector(1, 0, 0));
        List<Point> result3 = cylinder.findIntersections(ray4);
        assertEquals(List.of(new Point(1, 0, 1)), result3, "Ray starts inside the cylinder");

        // TC05: Ray starts and ends outside the cylinder but intersects (2 points)
        Ray ray5 = new Ray(new Point(-2, 0, 1), new Vector(3, 0, 0));
        List<Point> result4 = cylinder.findIntersections(ray5);
        assertEquals(List.of(new Point(-1, 0, 1), new Point(1, 0, 1)), result4, "Ray starts and ends outside the cylinder but intersects");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the cylinder axis
        // TC11: Ray is parallel to the cylinder and outside (0 points)
        Ray ray6 = new Ray(new Point(2, 0, 1), new Vector(0, 0, 1));
        assertNull(cylinder.findIntersections(ray6), "Ray is parallel to the cylinder and outside");

        // TC12: Ray is parallel to the cylinder and inside (0 points)
        Ray ray7 = new Ray(new Point(0.5, 0, 1), new Vector(0, 0, 1));
        assertNull(cylinder.findIntersections(ray7), "Ray is parallel to the cylinder and inside");

        // TC13: Ray is parallel to the cylinder and on the surface (0 points)
        Ray ray8 = new Ray(new Point(1, 0, 1), new Vector(0, 0, 1));
        assertNull(cylinder.findIntersections(ray8), "Ray is parallel to the cylinder and on the surface");

        // **** Group: Ray is orthogonal to the cylinder axis
        // TC21: Ray is orthogonal to the cylinder and starts inside (1 point)
        Ray ray9 = new Ray(new Point(0.5, 0, 1), new Vector(1, 0, 0));
        List<Point> result5 = cylinder.findIntersections(ray9);
        assertEquals(List.of(new Point(1, 0, 1)), result5, "Ray is orthogonal to the cylinder and starts inside");

        // TC22: Ray is orthogonal to the cylinder and starts outside (2 points)
        Ray ray10 = new Ray(new Point(-2, 0, 1), new Vector(1, 0, 0));
        List<Point> result6 = cylinder.findIntersections(ray10);
        assertEquals(List.of(new Point(-1, 0, 1), new Point(1, 0, 1)), result6, "Ray is orthogonal to the cylinder and starts outside");

        // TC23: Ray is orthogonal to the cylinder and starts on the surface (1 point)
        Ray ray11 = new Ray(new Point(1, 0, 1), new Vector(1, 0, 0));
        List<Point> result7 = cylinder.findIntersections(ray11);
        assertEquals(List.of(new Point(1, 0, 1)), result7, "Ray is orthogonal to the cylinder and starts on the surface");

        // **** Group: Ray is tangent to the cylinder (all tests 0 points)
        // TC31: Ray starts before the tangent point
        Ray ray12 = new Ray(new Point(-1, 1, 1), new Vector(2, 0, 0));
        assertNull(cylinder.findIntersections(ray12), "Ray starts before the tangent point");

        // TC32: Ray starts at the tangent point
        Ray ray13 = new Ray(new Point(0, 1, 1), new Vector(2, 0, 0));
        assertNull(cylinder.findIntersections(ray13), "Ray starts at the tangent point");

        // TC33: Ray starts after the tangent point
        Ray ray14 = new Ray(new Point(1, 1, 1), new Vector(2, 0, 0));
        assertNull(cylinder.findIntersections(ray14), "Ray starts after the tangent point");

        // **** Group: Special cases
        // TC41: Ray's line is outside, ray is orthogonal to ray start to cylinder's center line
        Ray ray15 = new Ray(new Point(2, 0, 1), new Vector(1, 1, 0));
        assertNull(cylinder.findIntersections(ray15), "Ray's line is outside, ray is orthogonal to ray start to cylinder's center line");
    }
}