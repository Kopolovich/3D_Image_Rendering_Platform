package primitives;

/**
 * This class represents a vector in a three-dimensional space.
 * It extends the Point class and provides vector-specific operations.
 */
public class Vector extends Point {



    /**
     * Constructs a new Vector instance with the specified x, y, and z coordinates.
     *
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     * @param z The z-coordinate of the vector.
     * @throws IllegalArgumentException if the resulting vector is a zero vector.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (Double3.ZERO.equals(this.xyz))
            throw new IllegalArgumentException("Can not create zero vector");
    }

    /**
     * Constructs a new Vector instance with the specified Double3 instance.
     *
     * @param double3 The Double3 instance representing the vector's coordinates.
     * @throws IllegalArgumentException if the resulting vector is a zero vector.
     */
    public Vector(Double3 double3) {
        super(double3);
        if (Double3.ZERO.equals(this.xyz))
            throw new IllegalArgumentException("Can not create zero vector");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Vector other)
                && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "Vector{} " + super.toString();
    }

    /**
     * Adds the given vector to this vector.
     *
     * @param vec The vector to add.
     * @return A new vector that is the result of the addition.
     */
    public Vector add(Vector vec) {
        return new Vector(this.xyz.add(vec.xyz));
    }

    /**
     * Scales this vector by the given scalar value.
     *
     * @param num The scalar value to scale the vector by.
     * @return A new vector that is the result of the scaling.
     */
    public Vector scale(double num) {
        return new Vector(this.xyz.scale(num));
    }

    /**
     * Returns the squared length of the vector.
     *
     * @return The squared length of the vector.
     */
    public double lengthSquared() {
        return (this.xyz.d1) * (this.xyz.d1)
                + (this.xyz.d2) * (this.xyz.d2)
                + (this.xyz.d3) * (this.xyz.d3);
    }

    /**
     * Returns the length of the vector.
     *
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Computes the dot product of this vector with another vector.
     *
     * @param other The vector to compute the dot product with.
     * @return The dot product of the two vectors.
     */
    public double dotProduct(Vector other) {
        return this.xyz.d1 * other.xyz.d1
                + this.xyz.d2 * other.xyz.d2
                + this.xyz.d3 * other.xyz.d3;
    }

    /**
     * Computes the cross product of this vector with another vector.
     *
     * @param other The vector to compute the cross product with.
     * @return A new vector that is the result of the cross product.
     */
    public Vector crossProduct(Vector other) {
        double x1 = this.xyz.d1;
        double y1 = this.xyz.d2;
        double z1 = this.xyz.d3;
        double x2 = other.xyz.d1;
        double y2 = other.xyz.d2;
        double z2 = other.xyz.d3;
        return new Vector(y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2);
    }

    /**
     * Normalizes this vector (i.e., scales it to have a length of 1).
     *
     * @return A new vector that is the normalized version of this vector.
     */
    public Vector normalize() {
        double length = this.length();
        return new Vector(this.xyz.d1 / length, this.xyz.d2 / length, this.xyz.d3 / length);
    }
}
