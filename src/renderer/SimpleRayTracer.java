package renderer;

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
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;
    private static final boolean IS_ANTI_ALIASING = true;

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
        var intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null ? scene.background : calcColor(ray.findClosestGeoPoint(intersections), ray);
    }

    /**
     * Finds the closest intersection point of the given ray with the scene geometries.
     *
     * @param ray The ray to find the intersection with.
     * @return The closest intersection point as a GeoPoint, or null if no intersection is found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * Calculates the color at a given intersection point considering global effects like reflection and refraction.
     *
     * @param intersection the intersection point
     * @param ray          the ray intersecting the point
     * @param level        the current recursion level
     * @param k            the cumulative attenuation factor
     * @return the color at the intersection point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDirection(), level, k));
    }

    /**
     * Calculates the color at a given intersection point considering ambient light and global effects.
     *
     * @param gp  the intersection point
     * @param ray the ray intersecting the point
     * @return the color at the intersection point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * Constructs a reflected ray based on the normal at the intersection point.
     *
     * @param n     the normal vector at the intersection point
     * @param point the intersection point
     * @param inRay the incoming ray
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Vector n, Point point, Ray inRay) {
        Vector sub = n.scale(inRay.getDirection().dotProduct(n)).scale(2);
        Vector dir = inRay.getDirection().subtract(sub);
        return new Ray(point, dir, n);
    }

    /**
     * Constructs a refracted ray based on the normal at the intersection point.
     *
     * @param n     the normal vector at the intersection point
     * @param point the intersection point
     * @param inRay the incoming ray
     * @return the refracted ray
     */
    private Ray constructRefractedRay(Vector n, Point point, Ray inRay) {
        return new Ray(point, inRay.getDirection(), n);
    }

    /**
     * Calculates the global effects of reflection and refraction on the color at a given point.
     *
     * @param gp    the intersection point
     * @param v     the direction vector
     * @param level the current recursion level
     * @param k     the cumulative attenuation factor
     * @return the color at the intersection point considering global effects
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        Color color = Color.BLACK;
        Vector normal = gp.geometry.getNormal(gp.point);

        // Reflective effect
        if (!material.kR.equals(Double3.ZERO)) {
            Ray reflectedRay = constructReflectedRay(normal, gp.point, new Ray(gp.point, v));
            Color reflectedColor = calcGlobalEffect(reflectedRay, level, k, material.kR);
            color = color.add(reflectedColor);
        }

        // Refractive effect
        if (!material.kT.equals(Double3.ZERO)) {
            Ray refractedRay = constructRefractedRay(normal, gp.point, new Ray(gp.point, v));
            Color refractedColor = calcGlobalEffect(refractedRay, level, k, material.kT);
            color = color.add(refractedColor);
        }

        return color;
    }

    /**
     * Recursively calculates the global effects of reflection and refraction on the color.
     *
     * @param ray   the ray to trace
     * @param level the current recursion level
     * @param k     the cumulative attenuation factor
     * @param kx    the attenuation factor for the current material
     * @return the color after considering global effects
     */
//    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
//        Double3 kkx = k.product(kx);
//        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
//        GeoPoint gp = findClosestIntersection(ray);
//        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
//    }

    /*
     * Recursively calculates the global effects of reflection and refraction on the color.
     *
     * @param ray   the ray to trace
     * @param level the current recursion level
     * @param k     the cumulative attenuation factor
     * @param kx    the attenuation factor for the current material
     * @return the color after considering global effects
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if(gp == null)
            return scene.background;
        Vector n = gp.geometry.getNormal(gp.point);
        var rays = Blackboard.generateBeam(n, 1, 1, ray, IS_ANTI_ALIASING ? 10 : 1);
        return calcAverageColor(rays, level, kx, kkx);
    }

    /**
     * Calculates the local effects of light on the color at a given intersection point.
     *
     * @param gp  the intersection point
     * @param ray the ray intersecting the point
     * @param k   the cumulative attenuation factor
     * @return the color at the intersection point considering local effects
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission(); // Start with the emission color of the geometry

        Vector v = ray.getDirection(); // View direction vector
        Vector n = gp.geometry.getNormal(gp.point); // Normal vector at the intersection point

        double nv = alignZero(n.dotProduct(v)); // Dot product between the normal and view direction vectors

        if (nv == 0)
            return color;

        Material material = gp.geometry.getMaterial(); // Material of the intersected geometry

        // Iterate over all light sources in the scene
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point); // Light direction vector at the intersection point
            double nl = alignZero(n.dotProduct(l)); // Dot product between the normal and light direction vectors

            if (nl * nv > 0) {
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(new Point(new Double3(gp.point.getX(), gp.point.getY(), gp.point.getZ()).product(ktr))); // Intensity of the light source at the intersection point

                    // Calculate the contributions of diffuse and specular reflections
                    color = color.add(
                            calcDiffusive(material.kD, nl, iL),
                            calcSpecular(material.kS, n, l, nl, v, iL, material.nShininess)
                    );
                }
            }
        }
        return color;
    }

    /**
     * Calculates the specular reflection color at a given intersection point.
     *
     * @param kS         The specular reflection coefficient of the material
     * @param n          The surface normal vector at the intersection point
     * @param l          The direction vector towards the light source
     * @param nl         The dot product between the surface normal and light direction vectors
     * @param v          The view direction vector
     * @param iL         The intensity of the light source at the intersection point
     * @param nShininess The shininess factor of the material
     * @return The color resulting from the specular reflection at the intersection point
     */
    private Color calcSpecular(Double3 kS, Vector n, Vector l, double nl, Vector v, Color iL, int nShininess) {
        Vector r = l.subtract(n.scale(nl * 2)); // Calculate the reflection direction vector
        double minusVR = -alignZero(v.dotProduct(r)); // Calculate the dot product between the view direction and reflection direction vectors

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
        Double3 diffuseCoefficient = kD.scale(Math.abs(nl)); // Calculate the diffuse reflection coefficient
        return iL.scale(diffuseCoefficient); // Scale the intensity of the light source by the diffuse coefficient
    }

    /**
     * Determines if a point is unshaded by checking for intersections between the point and the light source.
     *
     * @param gp    the intersection point
     * @param l     the light direction vector
     * @param n     the normal vector at the intersection point
     * @param light the light source
     * @return true if the point is unshaded, false otherwise
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource light) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA); // Apply delta to avoid self-shadowing
        Point point = gp.point.add(delta);

        Ray lightRay = new Ray(point, lightDirection); // Create the shadow ray

        var intersections = scene.geometries.findGeoIntersections(lightRay); // Find intersections with the scene geometries

        if (intersections == null) return true; // If there are no intersections, return true (no shadow)

        double lightDistance = light.getDistance(gp.point); // Calculate the distance to the light source

        for (GeoPoint geop : intersections) {
            if (alignZero(geop.point.distance(gp.point) - lightDistance) <= 0) {
                Material material = geop.geometry.getMaterial();
                if (material.kT.equals(Double3.ZERO)) {
                    return false; // If an intersection is found within the distance to the light source and the material is opaque, it's in shadow
                }
            }
        }

        return true;
    }

    /**
     * Calculates the transparency factor for a point by checking intersections between the point and the light source.
     *
     * @param gp    the intersection point
     * @param light the light source
     * @param l     the light direction vector
     * @param n     the normal vector at the intersection point
     * @return the transparency factor as a Double3 value
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        Point point = lightRay.getHead();
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return Double3.ONE;
        Double3 ktr = Double3.ONE;
        for (GeoPoint element : intersections) {
            if (light.getDistance(point) > point.distance(element.point))
                ktr = ktr.product(element.geometry.getMaterial().kT);
        }/**/
        return ktr;
    }

    Color calcAverageColor(List<Ray> rays,int level, Double3 kx, Double3 kkx){
        Color color = Color.BLACK;

        for(Ray ray : rays){
            GeoPoint gp = findClosestIntersection(ray);
            Color rayColor = (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
            color = color.add(rayColor);
        }

        return color.reduce(rays.size());
    }
}
