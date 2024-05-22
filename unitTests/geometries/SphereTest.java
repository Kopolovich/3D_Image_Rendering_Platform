package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
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

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {

        final Point p001 = new Point(0, 0, 1);
        final Point p100 = new Point(1, 0, 0);
        final Vector v001 = new Vector(0, 0, 1);

        Sphere sphere = new Sphere(1d, p100);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        var result1 = sphere.findIntersections(new Ray(p01, v310));
        if (result1 != null) {
            result1 = result1.stream()
                    .sorted(Comparator.comparingDouble(p -> p.distance(p01)))
                    .toList();
            assertEquals(2, result1.size(), "Wrong number of points");
            assertEquals(exp, result1, "Ray crosses sphere");
        } else {
            fail("Expected non-null result");
        }

        // TC03: Ray starts inside the sphere (1 point)
        var result2 = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), v310));
        if (result2 != null) {
            assertEquals(1,
                        result2.size(),
                        "Wrong number of points for ray starting inside the sphere");
            assertEquals(List.of(gp2),
                        result2,
                        "Ray starts inside the sphere");
        } else {
            fail("Expected non-null result");
        }

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), v310)),
                    "Ray's line starts after the sphere");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        var result3 = sphere.findIntersections(new Ray(p100, v310));
        if (result3 != null) {
            assertEquals(1,
                        result3.size(),
                        "Wrong number of points for ray starting at the sphere and going inside");
            assertEquals(List.of(gp2),
                        result3,
                        "Ray starts at the sphere and goes inside");
        } else {
            fail("Expected non-null result");
        }

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p100, v110)),
                    "Ray's line starts at the sphere and goes outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        var result4 = sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(2, 0, 0)));
        if (result4 != null) {
            result4 = result4.stream()
                    .sorted(Comparator.comparingDouble(p -> p.distance(new Point(-1, 0, 0)))).toList();
            assertEquals(2,
                        result4.size(),
                        "Wrong number of points for ray going through the center");
            assertEquals(List.of(new Point(0, 0, 0),
                        new Point(2, 0, 0)),
                        result4, "Ray goes through the center");
        } else {
            fail("Expected non-null result");
        }

        // TC14: Ray starts at sphere and goes inside (1 point)
        var result5 = sphere.findIntersections(new Ray(p100, new Vector(-1, 0, 0)));
        if (result5 != null) {
            assertEquals(1,
                        result5.size(),
                        "Wrong number of points for ray starting at the sphere and going inside");
            assertEquals(List.of(new Point(0, 0, 0)),
                        result5,
                        "Ray starts at the sphere and goes inside");
        } else {
            fail("Expected non-null result");
        }

        // TC15: Ray starts inside (1 point)
        var result6 = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0)));
        if (result6 != null) {
            assertEquals(1,
                        result6.size(),
                        "Wrong number of points for ray starting inside the sphere");
            assertEquals(List.of(new Point(2, 0, 0)),
                        result6,
                        "Ray starts inside the sphere");
        } else {
            fail("Expected non-null result");
        }

        // TC16: Ray starts at the center (1 point)
        var result7 = sphere.findIntersections(new Ray(p100, new Vector(0, 0, 1)));
        if (result7 != null) {
            assertEquals(1,
                        result7.size(),
                        "Wrong number of points for ray starting at the center of the sphere");
            assertEquals(List.of(new Point(1, 0, 1)),
                        result7,
                        "Ray starts at the center of the sphere");
        } else {
            fail("Expected non-null result");
        }

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p100,
                    new Vector(1, 0, 0))),
                    "Ray starts at sphere and goes outside");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0),
                    new Vector(1, 0, 0))),
                    "Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, -1, 0),
                    new Vector(2, 2, 0))),
                    "Ray starts before the tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, -1, 0),
                    new Vector(0, 1, 0))),
                    "Ray starts at the tangent point");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, -1, 0),
                    new Vector(0, 1, 0))),
                    "Ray starts after the tangent point");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0),
                    new Vector(0, 1, 0))),
                    "Ray is orthogonal to ray start to sphere's center line");
    }
}