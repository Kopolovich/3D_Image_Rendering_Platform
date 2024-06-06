package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * AmbientLight represents ambient lighting in a scene.
 * It provides functionality to calculate the intensity of ambient light.
 */
public class AmbientLight {
    /** The intensity of the ambient light. */
    private final Color intensity;

    /** Represents no ambient light. */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Constructs an AmbientLight with the given intensity and coefficient.
     *
     * @param IA The color of the ambient light.
     * @param KA The coefficient of the ambient light.
     */
    public AmbientLight(Color IA, Double3 KA) {
        intensity = IA.scale(KA);
    }

    /**
     * Constructs an AmbientLight with the given intensity and coefficient.
     *
     * @param IA The color of the ambient light.
     * @param KA The coefficient of the ambient light.
     */
    public AmbientLight(Color IA, double KA) {
        intensity = IA.scale(KA);
    }

    /**
     * Gets the intensity of the ambient light.
     *
     * @return The intensity of the ambient light.
     */
    public Color getIntensity() {
        return intensity;
    }
}

