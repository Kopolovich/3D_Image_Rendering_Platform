package primitives;

public class Vector extends Point{

    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector(Double3 double3) {
        super(double3);
    }

    public double lengthSquared() {
        return Math.pow(this.xyz.d1, 2) + Math.pow(this.xyz.d2, 2) + Math.pow(this.xyz.d3, 2);
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public double dotProduct(Vector other) {
        return this.xyz.d1*other.xyz.d1
                + this.xyz.d2*other.xyz.d2
                + this.xyz.d3*other.xyz.d3;
    }

    public Vector crossProduct(Vector other) {
        double x1 = this.xyz.d1;
        double y1 = this.xyz.d2;
        double z1 = this.xyz.d3;
        double x2 = other.xyz.d1;
        double y2 = other.xyz.d2;
        double z2 = other.xyz.d3;

        double newX = y1 * z2 - z1 * y2;
        double newY = z1 * x2 - x1 * z2;
        double newZ = x1 * y2 - y1 * x2;

        return new Vector(newX, newY, newZ);
    }

    public Vector normalize() {
        double length = this.length();
        return new Vector(this.xyz.d1 / length, this.xyz.d2 / length,this.xyz.d3 / length);
    }
}
