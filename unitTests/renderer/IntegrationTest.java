package renderer;

import org.junit.jupiter.api.Test;

public class IntegrationTest {
    @Test
    void test(){

    }
}

public class CameraIntegrationTests {

    private void assertIntersections(Camera camera, Intersectable geometricalObject, int expectedIntersections) {
        int nX = 3;
        int nY = 3;
        int intersections = 0;

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRay(nX, nY, j, i);
                var intersectionsList = geometricalObject.findIntersections(ray);
                if (intersectionsList != null) {
                    intersections += intersectionsList.size();
                }
            }
        }

        assertEquals(expectedIntersections, intersections, "Wrong amount of intersections");
    }

    @Test
    public void testSphereIntersections() {
        Camera camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, -1))
                .setDirection(new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();

        // Test cases based on course slides
        Sphere sphere = new Sphere(1, new Point(0, 0, 2));
        assertIntersections(camera, sphere, 2);  // Example value, adjust as needed
    }

    @Test
    public void testPlaneIntersections() {
        Camera camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, -1))
                .setDirection(new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();

        // Test cases based on course slides
        Plane plane = new Plane(new Point(0, 0, 5), new Vector(0, 0, 1));
        assertIntersections(camera, plane, 9);  // Example value, adjust as needed
    }

    @Test
    public void testTriangleIntersections() {
        Camera camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, -1))
                .setDirection(new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setVpSize(3, 3)
                .setVpDistance(1)
                .build();

        // Test cases based on course slides
        Triangle triangle = new Triangle(new Point(0, 1, 2), new Point(1, -1, 2), new Point(-1, -1, 2));
        assertIntersections(camera, triangle, 1);  // Example value, adjust as needed
    }
}
