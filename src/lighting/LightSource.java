package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The {@code LightSource} interface represents a light source in a 3D space.
 * Implementing classes should provide specific behavior for the light source.
 */
public interface LightSource {

    /**
     * Returns the intensity of the light source at a specific point.
     *
     * @param p the point at which the intensity is to be calculated
     * @return the intensity of the light source at the specified point
     */
    Color getIntensity(Point p);

    /**
     * Returns the direction vector from the light source to a specific point.
     *
     * @param p the point to which the direction vector is to be calculated
     * @return the direction vector from the light source to the specified point
     */
    Vector getL(Point p);

    /**
     * Returns the distance from the light source to a specific point.
     *
     * @param point the point to which the distance is to be calculated
     * @return the distance from the light source to the specified point
     */
    double getDistance(Point point);
}
