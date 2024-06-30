package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The {@code PointLight} class represents a point light source with a position and attenuation factors.
 * It extends the {@code Light} class and implements the {@code LightSource} interface.
 */
public class PointLight extends Light implements LightSource {

    // The position of the light source in the 3D space
    private Point position;
    // Attenuation factors
    private double kC = 1d, kL = 0d, kQ = 0d;

    /**
     * Constructs a new {@code PointLight} with the specified intensity and position.
     *
     * @param intensity the intensity of the light source
     * @param position the position of the light source
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor.
     *
     * @param kC the constant attenuation factor
     * @return this {@code PointLight} instance for method chaining
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor.
     *
     * @param kL the linear attenuation factor
     * @return this {@code PointLight} instance for method chaining
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     *
     * @param kQ the quadratic attenuation factor
     * @return this {@code PointLight} instance for method chaining
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Returns the intensity of the light source at a specific point.
     * The intensity is calculated using the inverse-square law with attenuation factors.
     *
     * @param p the point at which the intensity is to be calculated
     * @return the intensity of the light source at the specified point
     */
    @Override
    public Color getIntensity(Point p) {
        double distance = position.distance(p);
        double distanceSquared = position.distanceSquared(p);
        return intensity.scale(1 / (kC + kL * distance + kQ * distanceSquared));
    }

    /**
     * Returns the direction vector from the light source to a specific point.
     *
     * @param p the point to which the direction vector is to be calculated
     * @return the direction vector from the light source to the specified point
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    /**
     * Returns the distance from the light source to a specific point.
     *
     * @param point the point to which the distance is to be calculated
     * @return the distance from the light source to the specified point
     */
    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}

