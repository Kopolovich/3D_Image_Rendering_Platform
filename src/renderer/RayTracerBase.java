package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * RayTracerBase is an abstract class representing a base ray tracer.
 * It provides functionality to trace rays through a scene and determine the color at intersection points.
 */
public abstract class RayTracerBase {
    /** The scene to trace rays in. */
    protected Scene scene;

    /**
     * Constructs a RayTracerBase with the given scene.
     *
     * @param scene The scene to trace rays in.
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray through the scene and determines the color at the intersection point.
     *
     * @param ray The ray to trace through the scene.
     * @return The color at the intersection point.
     */
    public abstract Color traceRay(Ray ray);
}

