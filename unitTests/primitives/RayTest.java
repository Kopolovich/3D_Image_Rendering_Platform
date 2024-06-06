package primitives;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Ray class.
 */
class RayTest {

    /**
     * Tests the findClosestPoint method of the Ray class.
     */
    @Test
    void findClosestPoint() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: A point in the middle of the list is closest to the ray's head
        List<Point> points = Arrays.asList(new Point(3, 3, 3), new Point(1, 1, 1), new Point(4, 4, 4));
        assertEquals(new Point(1, 1, 1), ray.findClosestPoint(points), "The closest point should be (1, 1, 1)");

        // =============== Boundary Values Tests ==================
        // TC11: Empty list (should return null)
        assertNull(ray.findClosestPoint(Collections.emptyList()), "An empty list should return null");

        // TC12: The first point is the closest to the ray's head
        points = Arrays.asList(new Point(2, 2, 2), new Point(3.5, 3, 3), new Point(4, 4, 4));
        assertEquals(new Point(2, 2, 2), ray.findClosestPoint(points), "The closest point should be the first one (2, 2, 2)");

        // TC13: The last point is the closest to the ray's head
        points = Arrays.asList(new Point(3, 3, 3), new Point(4, 4, 4), new Point(0.5, 0.5, 0.5));
        assertEquals(new Point(0.5, 0.5, 0.5), ray.findClosestPoint(points), "The closest point should be the last one (0.5, 0.5, 0.5)");
    }
}