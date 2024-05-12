package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class PointTest {

    Point  p1         = new Point(1, 2, 3);
    Point  p2         = new Point(2, 4, 6);
    Point  p3         = new Point(2, 4, 5);

    Vector v1         = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);

    @Test
    void testSubtract() {
        assertEquals(v1, p2.subtract(p1), "ERROR: (point2 - point1) does not work correctly");
        assertThrows(IllegalArgumentException.class,
                    ()->p1.subtract(p1),
                    "ERROR: (point - itself) does not throw an exception");
    }

    @Test
    void testAdd() {
        assertEquals(p2, p1.add(v1), "ERROR: (point + vector) = other point does not work correctly");
        assertEquals(Double3.ZERO,
                    p1.add(v1Opposite).xyz,
                    "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    @Test
    void testDistanceSquared() {
        assertTrue(isZero(p1.distanceSquared(p1)), "ERROR: point squared distance to itself is not zero");
        assertTrue(isZero(p1.distanceSquared(p3) - 9), "ERROR: squared distance between points is wrong");
        assertTrue(isZero(p3.distanceSquared(p1) - 9), "ERROR: squared distance between points is wrong");
    }

    @Test
    void testDistance() {
        assertTrue(isZero(p1.distance(p1)), "ERROR: point squared distance to itself is not zero");
        assertTrue(isZero(p1.distance(p3) - 3), "ERROR: distance between points is wrong");
        assertTrue(isZero(p3.distance(p1) - 3), "ERROR: distance between points is wrong");
    }
}