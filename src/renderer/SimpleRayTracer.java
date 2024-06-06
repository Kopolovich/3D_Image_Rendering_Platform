package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * SimpleRayTracer is a basic implementation of RayTracerBase that calculates
 * the color of a ray by finding intersections with the 3D model of the scene.
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Constructs a SimpleRayTracer with the given scene.
     *
     * @param scene The scene to trace rays in.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }


    /**
     * Traces a ray through the scene and determines the color at the intersection point.
     *
     * @param ray The ray to trace through the scene.
     * @return The color at the intersection point, or the background color if no intersection is found.
     */
    @Override
    public Color traceRay(Ray ray) {
        // Find intersections between the ray and the geometries in the scene
        List<Point> intersections = scene.geometries.findIntersections(ray);

        // If no intersections are found, return the background color
        if (intersections == null || intersections.isEmpty()) {
            return scene.background;
        }

        // Find the closest intersection point to the ray's head
        Point closestPoint = ray.findClosestPoint(intersections);

        // Calculate the color at the closest intersection point
        return closestPoint != null ? calcColor(closestPoint) : scene.background;
    }

    /**
     * Calculates the color at the given intersection point.
     *
     * @param point The intersection point to calculate the color for.
     * @return The color at the intersection point.
     */
    private Color calcColor(Point point) {
        // For now, return the ambient light color of the scene.
        return scene.ambientLight.getIntensity();
    }
}

