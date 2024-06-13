package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

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
        var intersections = scene.geometries.findGeoIntersections(ray); return intersections == null
                ? scene.background
                : calcColor(ray.findClosestGeoPoint(intersections), ray);
    }

    /**
     * returns the color of a given point
     *
     * @param intersection the point to calculate
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray));
    }

    /**
     * calculate the effects of the geometry itself without the effect of other
     * geometries on this object
     *
     * @param gp  the point to calculate the effects on
     * @param ray the ray we intersected with
     * @return the result color of the local effects
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission(); // Start with the emission color of the geometry

        Vector v = ray.getDirection(); // View direction vector
        Vector n = gp.geometry.getNormal(gp.point); // Normal vector at the intersection point

        double nv = alignZero(n.dotProduct(v)); // Dot product between the normal and view direction vectors

        // If the dot product is close to zero, the view direction and normal are orthogonal,
        // so there is no contribution from local effects
        if (nv == 0)
            return color;

        Material material = gp.geometry.getMaterial(); // Material of the intersected geometry

        // Iterate over all light sources in the scene
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point); // Light direction vector at the intersection point
            double nl = alignZero(n.dotProduct(l)); // Dot product between the normal and light direction vectors

            // Check if the light is on the same side as the view direction
            if (nl * nv > 0) {
                Color iL = lightSource.getIntensity(gp.point); // Intensity of the light source at the intersection point

                // Calculate the contributions of diffuse and specular reflections
                color = color.add(
                        calcDiffusive(material.kD, nl, iL),
                        calcSpecular(material.kS, n, l, nl, v, iL, material.nShininess)
                );
            }
        }
        return color;
    }


    /**
     * Calculates the specular reflection color at a given intersection point.
     *
     * @param kS The specular reflection coefficient of the material
     * @param n The surface normal vector at the intersection point
     * @param l The direction vector towards the light source
     * @param nl The dot product between the surface normal and light direction vectors
     * @param v The view direction vector
     * @param iL The intensity of the light source at the intersection point
     * @param nShininess The shininess factor of the material
     * @return The color resulting from the specular reflection at the intersection point
     */
    private Color calcSpecular(Double3 kS, Vector n, Vector l, double nl, Vector v, Color iL, int nShininess) {
        Vector r = l.subtract(n.scale(nl * 2)); // Calculate the reflection direction vector
        double minusVR = -alignZero(v.dotProduct(r)); // Calculate the dot product between the view direction and reflection direction vectors
        // If the dot product is less than or equal to zero, the reflection is in the opposite direction of the view,
        // so there is no contribution from specular reflection
        if (minusVR <= 0)
            return Color.BLACK;

        Double3 shine = kS.scale(Math.pow(minusVR, nShininess)); // Calculate the specular reflection color based on the shininess factor
        return iL.scale(shine); // Scale the color by the intensity of the light source
    }


    /**
     * Calculates the diffuse reflection color at a given intersection point.
     *
     * @param kD The diffuse coefficient of the material
     * @param nl The dot product between the normal and light direction vectors
     * @param iL The intensity of the light source
     * @return The calculated diffuse reflection color at the given intersection point
     */
    private Color calcDiffusive(Double3 kD, double nl, Color iL) {
        // Calculate the diffuse reflection of light on the surface using the Lambertian reflection model
        // Start by scaling the diffuse coefficient with the absolute value of the dot product between the surface normal and the light direction
        Double3 diffuseCoefficient = kD.scale(Math.abs(nl));

        // Scale the intensity of the light source with the diffuse coefficient to get the resulting color
        Color diffuseColor = iL.scale(diffuseCoefficient);

        // Return the calculated diffuse color
        return diffuseColor;
    }
}

