package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * IntegrationTest class for testing intersections between camera rays and geometrical objects.
 */
public class IntegrationTest {

    /**
     * Helper method to assert the number of intersections between camera rays and geometrical objects.
     *
     * @param expected The expected number of intersections.
     * @param camera The camera used for generating rays.
     * @param geometry The geometrical object to intersect with.
     * @param nX The number of pixels in the view plane's width.
     * @param nY The number of pixels in the view plane's height.
     */
    private void assertIntersections(int expected, Camera camera, Intersectable geometry, int nX, int nY) {
        int intersections = 0;

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRay(nX, nY, j, i);
                var intersectionsList = geometry.findIntersections(ray);
                if (intersectionsList != null) {
                    intersections += intersectionsList.size();
                }
            }
        }

        assertEquals(expected, intersections, "Wrong amount of intersections");
    }

    // Define cameras for testing
    Camera camera1 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(new Point(0, 0, 0))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(3, 3)
            .setVpDistance(1)
            .build();

    Camera camera2 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(new Point(0, 0, 0.5))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(3, 3)
            .setVpDistance(1)
            .build();

    /**
     * Tests intersection between camera rays and spheres.
     */
    @Test
    public void testSphereIntersections() {

        // TC01: Sphere with 2 intersections
        Sphere sphere1 = new Sphere(1, new Point(0, 0, -3));
        assertIntersections(2, camera1, sphere1, 3, 3);

        // TC02: Sphere with 18 intersections
        Sphere sphere2 = new Sphere(2.5, new Point(0, 0, -2.5));
        assertIntersections(18, camera2, sphere2, 3, 3);

        // TC03: Sphere with 10 intersections
        Sphere sphere3 = new Sphere(2, new Point(0, 0, -2));
        assertIntersections(10, camera2, sphere3, 3, 3);

        // TC04: Sphere with no intersections (behind the camera)
        Sphere sphere4 = new Sphere(0.5, new Point(0, 0, 1));
        assertIntersections(0, camera1, sphere4, 3, 3);

        // TC05: Sphere with 9 intersections
        Sphere sphere5 = new Sphere(4, new Point(0, 0, -1));
        assertIntersections(9, camera1, sphere5, 3, 3);
    }

    /**
     * Tests intersection between camera rays and planes.
     */
    @Test
    public void testPlaneIntersections() {

        // TC06: Plane with 9 intersections
        Plane plane1 = new Plane(new Point(0, 0, -5), new Vector(0, 0, 1));
        assertIntersections(9, camera1, plane1, 3, 3);

        // TC07: Plane with 9 intersections (different normal vector)
        Plane plane2 = new Plane(new Point(0, 0, -5), new Vector(0, 0.2, -1));
        assertIntersections(9, camera1, plane2, 3, 3);

        // TC08: Plane with 6 intersections (different normal vector)
        Plane plane3 = new Plane(new Point(0, 0, -5), new Vector(0, 1, -1));
        assertIntersections(6, camera1, plane3, 3, 3);
    }

    /**
     * Tests intersection between camera rays and triangles.
     */
    @Test
    public void testTriangleIntersections() {

        // TC09: Triangle with 1 intersection
        Triangle triangle1 = new Triangle(new Point(0, 1, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));
        assertIntersections(1, camera1, triangle1, 3, 3);

        // TC10: Triangle with 2 intersections
        Triangle triangle2 = new Triangle(new Point(0, 20, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));
        assertIntersections(2, camera1, triangle2, 3, 3);
    }
}
