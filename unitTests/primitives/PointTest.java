package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for the Point class.
 */
class PointTest {

    // Defining some sample points and vectors for testing
    Point  p1 = new Point(1, 2, 3);
    Point  p2 = new Point(2, 4, 6);
    Point  p3 = new Point(2, 4, 5);

    Vector v1 = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);

    /**
     *  Test method for point subtract method
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test subtraction of two points
        assertEquals(v1, p2.subtract(p1), "ERROR: (point2 - point1) does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: Test subtraction of a point from itself (expecting IllegalArgumentException)
        assertThrows(IllegalArgumentException.class,
                    ()->p1.subtract(p1),
                    "ERROR: (point - itself) does not throw an exception");
    }

    /**
     * Test method for point add method
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test addition of a vector to a point
        assertEquals(p2, p1.add(v1), "ERROR: (point + vector) = other point does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: Test addition of a vector to opposite point resulting in the center of coordinates
        assertEquals(Double3.ZERO,
                    p1.add(v1Opposite).xyz,
                    "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    /**
     * Test method for point DistanceSquared method
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test squared distance between two points
        assertTrue(isZero(p1.distanceSquared(p3) - 9), "ERROR: squared distance between points is wrong");
        assertTrue(isZero(p3.distanceSquared(p1) - 9), "ERROR: squared distance between points is wrong");

        // =============== Boundary Values Tests ==================
        // TC11: Test squared distance of a point to itself (expecting zero)
        assertTrue(isZero(p1.distanceSquared(p1)), "ERROR: point squared distance to itself is not zero");

    }

    /**
     * Test method for point Distance method
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test distance between two points
        assertTrue(isZero(p1.distance(p3) - 3), "ERROR: distance between points is wrong");
        assertTrue(isZero(p3.distance(p1) - 3), "ERROR: distance between points is wrong");

        // =============== Boundary Values Tests ==================
        // TC11: Test squared distance of a point to itself (expecting zero)
        assertTrue(isZero(p1.distance(p1)), "ERROR: point squared distance to itself is not zero");

    }
}