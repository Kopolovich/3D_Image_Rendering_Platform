package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    private final double DELTA = 0.000001;
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0) };
        Triangle tri = new Triangle(new Point(0, 0, 1),
                       new Point(1, 0, 0),
                       new Point(0, 1, 0));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tri.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = tri.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1,
                    result.length(),
                    DELTA,
                    "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 2; ++i)
            assertEquals(0d,
                        result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i-1])),
                        DELTA,
                        "Triangle's normal is not orthogonal to one of the edges");
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        final Point p1 = new Point(0, 0, 1);
        final Point p2 = new Point(1, 0, 0);
        final Point p3 = new Point(0, 1, 0);

        Triangle triangle = new Triangle(p1, p2, p3);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is inside the triangle (1 point)
        Point intersection1 = new Point(0.25, 0.25, 0.5);
        Ray ray1 = new Ray(new Point(0.25, 0.25, 1), new Vector(0, 0, -1));
        var result1 = triangle.findIntersections(ray1);
        assertNotNull(result1, "Ray's line should intersect the triangle");
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(List.of(intersection1), result1, "Ray intersects triangle");

        // TC02: Ray's line is outside the triangle against edge (0 points)
        Ray ray2 = new Ray(new Point(-1, 0.5, 1), new Vector(1, 0, -1));
        assertNull(triangle.findIntersections(ray2),
                    "Ray's line should be outside the triangle against edge");

        // TC03: Ray's line is outside the triangle against vertex (0 points)
        Ray ray3 = new Ray(new Point(-1, -1, 1), new Vector(1, 1, -1));
        assertNull(triangle.findIntersections(ray3),
                    "Ray's line should be outside the triangle against vertex");

        // =============== Boundary Values Tests ==================

        // TC11: Ray's line is on the edge of the triangle (0 points)
        Ray ray4 = new Ray(new Point(0.5, 0, 1), new Vector(0, 0, -1));
        assertNull(triangle.findIntersections(ray4),
                    "Ray's line should be on the edge of the triangle");

        // TC12: Ray's line is in the vertex of the triangle (0 points)
        Ray ray5 = new Ray(new Point(0, 0, 1), new Vector(0, 0, -1));
        assertNull(triangle.findIntersections(ray5),
                    "Ray's line should be in the vertex of the triangle");

        // TC13: Ray's line is on the continuation of the edge of the triangle (0 points)
        Ray ray6 = new Ray(new Point(2, 0, 1), new Vector(0, 0, -1));
        assertNull(triangle.findIntersections(ray6),
                    "Ray's line should be on the continuation of the edge of the triangle");
    }
}