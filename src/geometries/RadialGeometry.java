package geometries;

/**
 * An abstract class representing a radial geometry in three-dimensional space.
 * Radial geometries are geometries that are defined by a single radius.
 */
public abstract class RadialGeometry implements Geometry {

    protected final double radius; // The radius of the radial geometry

    /**
     * Constructs a new RadialGeometry instance with the specified radius.
     *
     * @param radius The radius of the radial geometry.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}

