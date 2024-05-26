package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    double DELTA = 0.0001;

    /**
     * Test method for Tube getNormal.
     */
    @Test
    void testGetNormal() {
        Tube tube = new Tube(2, new Ray(new Point(0,0,0), new Vector(1,0,0)));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test calculation of normal vector for a point on the surface of the tube
        Vector normal = tube.getNormal(new Point(2,2,0));
        assertEquals(new Vector(0, 1, 0),
                    normal,
                    "ERROR: tube getNormal() does not work correctly");
        // ensure |result| = 1
        assertEquals(1,
                    normal.length(),
                    DELTA,
                    "Polygon's normal is not a unit vector");

        // =============== Boundary Values Tests ==================
        // TC11: Test calculation of normal vector for a point directly opposite the ray's origin (forming a right angle with the axis)
        normal = tube.getNormal(new Point(0, 2, 0));
        assertEquals(new Vector(0, 1, 0),
                    normal,
                    "ERROR: tube getNormal() does not work correctly at the boundary point");
        // ensure |result| = 1
        assertEquals(1,
                    normal.length(),
                    DELTA,
                    "Tube's normal is not a unit vector");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Tube tube = new Tube(1.0, new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray crosses the tube (2 points)
        Ray ray1 = new Ray(new Point(-2, 0, 2), new Vector(1, 0, 0));
        List<Point> result1 = tube.findIntersections(ray1);
        assertEquals(List.of(new Point(-1, 0, 2), new Point(1, 0, 2)), result1, "Ray crosses the tube");

        // TC02: Ray touches the tube (1 point)
        Ray ray2 = new Ray(new Point(1, 0, 2), new Vector(1, 0, 0));
        List<Point> result2 = tube.findIntersections(ray2);
        assertEquals(List.of(new Point(1, 0, 2)), result2, "Ray touches the tube");

        // TC03: Ray passes outside the tube (0 points)
        Ray ray3 = new Ray(new Point(-2, 0, 2), new Vector(-1, 0, 0));
        assertNull(tube.findIntersections(ray3), "Ray passes outside the tube");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the tube axis
        // TC11: Ray is parallel to the tube and outside (0 points)
        Ray ray4 = new Ray(new Point(2, 0, 2), new Vector(0, 0, 1));
        assertNull(tube.findIntersections(ray4), "Ray is parallel to the tube and outside");

        // TC12: Ray is parallel to the tube and inside (0 points)
        Ray ray5 = new Ray(new Point(0.5, 0, 2), new Vector(0, 0, 1));
        assertNull(tube.findIntersections(ray5), "Ray is parallel to the tube and inside");

        // TC13: Ray is parallel to the tube and on the surface (0 points)
        Ray ray6 = new Ray(new Point(1, 0, 2), new Vector(0, 0, 1));
        assertNull(tube.findIntersections(ray6), "Ray is parallel to the tube and on the surface");

        // **** Group: Ray is orthogonal to the tube axis
        // TC21: Ray is orthogonal to the tube and starts inside (1 point)
        Ray ray7 = new Ray(new Point(0.5, 0, 2), new Vector(1, 0, 0));
        List<Point> result3 = tube.findIntersections(ray7);
        assertEquals(List.of(new Point(1, 0, 2)), result3, "Ray is orthogonal to the tube and starts inside");

        // TC22: Ray is orthogonal to the tube and starts outside (2 points)
        Ray ray8 = new Ray(new Point(-2, 0, 2), new Vector(1, 0, 0));
        List<Point> result4 = tube.findIntersections(ray8);
        assertEquals(List.of(new Point(-1, 0, 2), new Point(1, 0, 2)), result4, "Ray is orthogonal to the tube and starts outside");

        // TC23: Ray is orthogonal to the tube and starts on the surface (1 point)
        Ray ray9 = new Ray(new Point(1, 0, 2), new Vector(1, 0, 0));
        List<Point> result5 = tube.findIntersections(ray9);
        assertEquals(List.of(new Point(1, 0, 2)), result5, "Ray is orthogonal to the tube and starts on the surface");

        // **** Group: Ray is tangent to the tube (all tests 0 points)
        // TC31: Ray starts before the tangent point
        Ray ray10 = new Ray(new Point(-1, 1, 2), new Vector(2, 0, 0));
        assertNull(tube.findIntersections(ray10), "Ray starts before the tangent point");

        // TC32: Ray starts at the tangent point
        Ray ray11 = new Ray(new Point(0, 1, 2), new Vector(2, 0, 0));
        assertNull(tube.findIntersections(ray11), "Ray starts at the tangent point");

        // TC33: Ray starts after the tangent point
        Ray ray12 = new Ray(new Point(1, 1, 2), new Vector(2, 0, 0));
        assertNull(tube.findIntersections(ray12), "Ray starts after the tangent point");

        // **** Group: Special cases
        // TC41: Ray's line is outside, ray is orthogonal to ray start to tube's center line
        Ray ray13 = new Ray(new Point(2, 0, 2), new Vector(1, 1, 0));
        assertNull(tube.findIntersections(ray13), "Ray's line is outside, ray is orthogonal to ray start to tube's center line");
    }
}