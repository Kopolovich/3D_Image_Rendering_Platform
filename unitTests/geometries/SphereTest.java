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
     * Help method that compares two points to check if they are equal within a specified tolerance.
     *
     * @param p1 the first point to compare
     * @param p2 the second point to compare
     * @return true if the points are equal within the tolerance, false otherwise
     */
    private boolean compareWithTolerance(Point p1, Point p2){
        final double TOLERANCE = 1e-6;
        return (p1.getX() - p2.getX()) < TOLERANCE
                && (p1.getY() - p2.getY()) < TOLERANCE
                && (p1.getZ() - p2.getZ()) < TOLERANCE;
    }

    /**
     * Test method for {@link Sphere#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        final Point p100 = new Point(1, 0, 0);
        Sphere sphere = new Sphere(1d, p100);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);
        final Point p200 = new Point(2, 0, 0);

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
        } else
            fail("Expected non-null result");

        // TC03: Ray starts inside the sphere (1 point)
        Ray ray = new Ray(new Point(0.5, 0, 0), new Vector(-1, 0, 1));
        var result2 = sphere.findIntersections(ray);
        if(result2 != null) {
            result2 = result2.stream()
                    .sorted(Comparator.comparingDouble(p -> p.distance(p01)))
                    .toList();
            assertEquals(1, result2.size(), "Wrong number of points-expected to 1");
            assertTrue(compareWithTolerance(new Point(0.5 - 0.41143782777,0, 0.41143782777),
                        result2.getFirst()),
                        "Ray crosses sphere");
        }
        else
            fail("Expected non-null result");

        // TC04: Ray starts after the sphere (0 points)
        ray = new Ray(new Point(5, 0, 0), new Vector(1, 1, 0));
        assertNull(sphere.findIntersections(ray), "Ray starts after the sphere ");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 point)
        ray = new Ray(p200, new Vector(-1, 1, 0));
        var result3 = sphere.findIntersections(ray);
        if (result3 != null) {
            result3 = result3.stream()
                    .sorted(Comparator.comparingDouble(p -> p.distance(p01)))
                    .toList();
            assertEquals(1,
                        result3.size(),
                        "Ray starts at sphere and goes inside-expected to 1 point");
            assertEquals(List.of(new Point(1, 1, 0)),
                        result3,
                        "wrong point when ray starts at sphere and goes inside");
        }
        else
            fail("Expected non-null result");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p200, new Vector(1, 0, 0))),
                "Ray starts at sphere and goes outside-expected 0 points");

        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        ray = new Ray(new Point(1, -2, 0), new Vector(0, 1, 0));
        var result4 = sphere.findIntersections(ray);
        if(result4 != null){
            result4 = result4.stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
            assertEquals(2,
                        result4.size(),
                        "Ray starts before the sphere (goes through the center) - expected 2 points");
            assertEquals(List.of(new Point(1, -1, 0), new Point(1, 1, 0)),
                        result4,
                        "wrong points when ray starts before the sphere (goes through the center)");
        }
        else
            fail("Expected non-null result");

        // TC14: Ray starts at sphere and goes inside (1 point)
        ray = new Ray(new Point(1, -1, 0), new Vector(0, 1, 0));
        var result5 = sphere.findIntersections(ray);
        if(result5 != null){
            result5 = result5.stream()
                    .sorted(Comparator.comparingDouble(p -> p.distance(p01)))
                    .toList();
            assertEquals(1,
                        result5.size(),
                        "Ray starts at sphere and goes inside(through the center)-expected to 1 point");
            assertEquals(List.of(new Point(1, 1, 0)),
                        result5,
                        "wrong point when ray starts at sphere and goes inside through the center");
        }
        else
            fail("Expected non-null result");

        // TC15: Ray starts inside (1 point)
        ray = new Ray(new Point(1, 0.5, 0), new Vector(0, 1, 0));
        var result6 = sphere.findIntersections(ray);
        if(result6 != null){
            result6 = result6.stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
            assertEquals(1, result6.size(), "Ray starts inside the sphere -expected to 1 point");
            assertEquals(List.of(new Point(1, 1, 0)), result6,
                    "wrong point when ray starts in the center");
        }
        else
            fail("Expected non-null result");


        // TC16: Ray starts at the center (1 point)
        ray = new Ray(new Point(1, 0, 0), new Vector(1, 0, 0));
        var result7 = sphere.findIntersections(ray);
        if(result7 != null){
            result7 = result7.stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
            assertEquals(1, result7.size(), "Ray starts in the center -expected to 1 point");
            assertEquals(List.of(p200), result7,
                    "wrong point when ray starts inside sphere through the center");
        }
        else
            fail("Expected non-null result");



        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p200, new Vector(1, 0, 0))),
                "Ray starts at sphere and goes outside (goes through the center) -expected 0 points");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
                "Ray starts after sphere -expected 0 points");


        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, -1, 0), new Vector(1, 0, 0))),
                "Ray starts before the tangent point -expected 0 points");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(1, 0, 0))),
                "Ray starts at the tangent point -expected 0 points");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, 2, 0), new Vector(0, 1, 0))),
                "Ray starts after the tangent point -expected 0 points");

        // **** Group: Special cases

        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(0, 1, 0))),
                "Ray's line is outside, ray is orthogonal to ray start to sphere's center line -expected 0 points");

        // TC23: Ray's line is inside, ray is orthogonal to ray start to sphere's center line (1 point)
        ray = new Ray(new Point(0.5, 0, 0), new Vector(0, 1, 0));
        var result8 = sphere.findIntersections(ray);
        if(result8 != null){
            result8 = result8.stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
            assertEquals(1, result8.size(), "Wrong number of points-expected to 1");
            assertTrue(compareWithTolerance(new Point(0.5, 0.86602540378, 0),
                        result8.getFirst()),
                        "Ray crosses sphere");
        }
        else
            fail("Expected non-null result");

    }
}