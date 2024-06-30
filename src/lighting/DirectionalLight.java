package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The {@code DirectionalLight} class represents a light source with a specific direction and infinite distance.
 * It extends the {@code Light} class and implements the {@code LightSource} interface.
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * The direction of the directional light.
     */
    private Vector direction;

    /**
     * Constructs a new {@code DirectionalLight} with the specified intensity and direction.
     *
     * @param intensity the intensity of the light source
     * @param direction the direction of the light source
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Returns the intensity of the light source at a specific point.
     * Since the directional light is considered to be at an infinite distance,
     * the intensity is constant regardless of the point.
     *
     * @param p the point at which the intensity is to be calculated
     * @return the intensity of the light source at the specified point
     */
    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    /**
     * Returns the direction vector of the light source.
     *
     * @param p the point to which the direction vector is to be calculated
     * @return the direction vector of the light source
     */
    @Override
    public Vector getL(Point p) {
        return direction;
    }

    /**
     * Returns the distance from the light source to a specific point.
     * Since the directional light is considered to be at an infinite distance,
     * this method always returns {@code Double.POSITIVE_INFINITY}.
     *
     * @param point the point to which the distance is to be calculated
     * @return {@code Double.POSITIVE_INFINITY} indicating an infinite distance
     */
    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}

