package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * The {@code SpotLight} class represents a spotlight source with a specific direction
 * in addition to the position and attenuation factors.
 * It extends the {@code PointLight} class.
 */
public class SpotLight extends PointLight {

    /**
     * The direction of the spotlight.
     */
    private Vector direction;

    /**
     * Constructs a new {@code SpotLight} with the specified intensity, position, and direction.
     *
     * @param intensity the intensity of the light source
     * @param position the position of the light source
     * @param direction the direction of the spotlight
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Sets the constant attenuation factor.
     *
     * @param kC the constant attenuation factor
     * @return this {@code SpotLight} instance for method chaining
     */
    @Override
    public SpotLight setkC(double kC) {
        return (SpotLight) super.setkC(kC);
    }

    /**
     * Sets the linear attenuation factor.
     *
     * @param kL the linear attenuation factor
     * @return this {@code SpotLight} instance for method chaining
     */
    @Override
    public SpotLight setkL(double kL) {
        return (SpotLight) super.setkL(kL);
    }

    /**
     * Sets the quadratic attenuation factor.
     *
     * @param kQ the quadratic attenuation factor
     * @return this {@code SpotLight} instance for method chaining
     */
    @Override
    public SpotLight setkQ(double kQ) {
        return (SpotLight) super.setkQ(kQ);
    }

    /**
     * Returns the intensity of the spotlight at a specific point.
     * The intensity is calculated using the inverse-square law with attenuation factors,
     * and it is scaled by the cosine of the angle between the light's direction and the vector to the point.
     *
     * @param p the point at which the intensity is to be calculated
     * @return the intensity of the spotlight at the specified point
     */
    @Override
    public Color getIntensity(Point p) {
        double d = alignZero(direction.dotProduct(getL(p)));
        if (d <= 0) return Color.BLACK;
        return super.getIntensity(p).scale(d);
    }

}
