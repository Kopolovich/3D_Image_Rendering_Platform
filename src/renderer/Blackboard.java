package renderer;

import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The Blackboard class represents a virtual blackboard used for managing rays in soft shadow calculations.
 */
public class Blackboard {
    /**
     * Resolution (square root of the number of beams)
     */
    int res;
    /**
     * Size of each side (size of the light source)
     */
    double size;
    /**
     * Direction to move up on the board
     */
    Vector vUp;
    /**
     * Direction to move right on the board
     */
    Vector vRight;
    /**
     * The direction we move the board from p0
     */
    Vector vTo;
    /**
     * Center of the board
     */
    Point p0;

    /**
     * Constructs a Blackboard object with the given parameters.
     *
     * @param res  the resolution (square root of the number of beams)
     * @param size the size of each side (size of the light source)
     * @param vTo  the direction to move the board from p0
     * @param vUp  the direction to move up on the board
     * @param p0   the center of the board
     */
    public Blackboard(int res, double size, Vector vTo, Vector vUp, Point p0) {
        this.res = res;
        this.size = size;
        this.vUp = vUp;
        this.vTo = vTo;
        this.vRight = vUp.crossProduct(vTo);
        this.p0 = p0;
    }

    /**
     * Constructs a ray between the given point and a pixel on the board.
     * If the pixel is outside the circle, returns null.
     *
     * @param j the column index of the pixel
     * @param i the row index of the pixel
     * @param p the point the ray starts at (a point on the geometry)
     * @return a new Ray object representing the constructed ray, or null if the pixel is outside the circle
     */
    public Ray constructRay(int j, int i, Point p) {
        // Calculate the center of the board
        Point pc = p0.add(vTo.scale(size / 2));

        // Calculate the size of a single pixel
        double r = size / res;

        // Calculate the position of the pixel on the board
        double yi = (i - ((res - 1) / 2.0)) * r;
        double xj = (j - ((res - 1) / 2.0)) * r;
        Point pij = pc;
        if (!isZero(xj))
            pij = pij.add(vRight.scale(xj));
        if (!isZero(yi))
            pij = pij.add(vUp.scale(-yi));

        // Check if the pixel is outside the circle
        if (pij.distance(pc) > (size / 2))
            return null;

        // Calculate the direction vector of the ray
        Vector dir = p.subtract(pij);

        // Create and return the ray
        return new Ray(pij, dir);
    }
}