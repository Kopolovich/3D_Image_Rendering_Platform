package lighting;

import primitives.Color;

/**
 * The {{@code @Light}} class represents a light source with a certain intensity.
 * This is an abstract class and cannot be instantiated directly.
 * Subclasses should provide specific implementations.
 */
abstract class Light {

    /**
     * The intensity of the light source.
     */
    protected Color intensity;

    /**
     * Constructs a new {@code Light} with the specified intensity.
     *
     * @param intensity the intensity of the light source
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity of the light source.
     *
     * @return the intensity of the light source
     */
    public Color getIntensity() {
        return intensity;
    }
}
