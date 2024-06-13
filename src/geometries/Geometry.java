package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;


/**
 * Abstract class representing a geometric object that can be intersected by rays.
 * Extends the Intersectable class.
 */
public abstract class Geometry extends Intersectable {

    /**
     * The emission color of the geometry. Defaults to black.
     */
    protected Color emission = Color.BLACK;

    private Material material = new Material();

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Gets the emission color of the geometry.
     *
     * @return The emission color of the geometry.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The new emission color.
     * @return The current instance of Geometry for method chaining.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Computes the normal vector at the given point on the geometry's surface.
     *
     * @param point The point on the geometry's surface.
     * @return The normal vector at the given point.
     */
    public abstract Vector getNormal(Point point);
}


