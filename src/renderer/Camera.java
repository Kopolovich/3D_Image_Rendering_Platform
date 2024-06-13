package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Locale;
import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * Represents a camera in a 3D scene.
 * Provides functionality to construct rays through the camera's view plane.
 */
public class Camera implements Cloneable {

    @Override
    protected Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can not happen because this class implements Cloneable
        }
    }

    /**
     * A builder class for creating Camera objects.
     */
    public static class Builder {
        private final Camera camera;

        /**
         * Constructs a new Builder instance.
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * Sets the location of the camera.
         *
         * @param p The location of the camera.
         * @return The Builder instance for method chaining.
         * @throws IllegalArgumentException If the location is null.
         */
        public Builder setLocation(Point p) {
            if (p == null)
                throw new IllegalArgumentException("Location can not be null");

            camera.p0 = p;
            return this;
        }

        /**
         * Sets the direction and up vectors for the camera.
         *
         * @param vTo The direction vector of the camera.
         * @param vUp The up vector of the camera.
         * @return The Builder instance for method chaining.
         * @throws IllegalArgumentException If the direction or up vector is null, or if they are not orthogonal.
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null)
                throw new IllegalArgumentException("vTo and vUp can not be null");
            if (vTo.dotProduct(vUp) != 0)
                throw new IllegalArgumentException("vTo and vUp are not orthogonal");

            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * Sets the size of the camera's view plane.
         *
         * @param height The height of the view plane.
         * @param width  The width of the view plane.
         * @return The Builder instance for method chaining.
         * @throws IllegalArgumentException If the height or width is non-positive.
         */
        public Builder setVpSize(double height, double width) {
            if (height <= 0)
                throw new IllegalArgumentException("height has to be positive");
            if (width <= 0)
                throw new IllegalArgumentException("width has to be positive");

            camera.height = height;
            camera.width = width;
            return this;
        }

        /**
         * Sets the distance of the camera's view plane.
         *
         * @param distance The distance of the view plane.
         * @return The Builder instance for method chaining.
         * @throws IllegalArgumentException If the distance is non-positive.
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0)
                throw new IllegalArgumentException("VP distance has to be positive");

            camera.distance = distance;
            return this;
        }

        public Builder setImageWriter(ImageWriter imageWriter){
            if (imageWriter == null)
                throw new IllegalArgumentException("imageWriter can not be null");

            camera.imageWriter = imageWriter;
            return this;
        }

        public Builder setRayTracer(RayTracerBase rayTracer){
            if (rayTracer == null)
                throw new IllegalArgumentException("rayTracer can not be null");

            camera.rayTracer = rayTracer;
            return this;
        }


        /**
         * Builds and returns a Camera object with the specified parameters.
         *
         * @return The Camera object.
         * @throws MissingResourceException If any required rendering data is missing.
         */
        public Camera build() {
            final String GENERAL_DESCRIPTION = "Missing rendering data in camera class";

            if (camera.p0 == null)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "P0 is missing");
            if (camera.vTo == null)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "vTo is missing");
            if (camera.vUp == null)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "vUp is missing");
            if (camera.height == 0.0)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "height is missing");
            if (camera.width == 0.0)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "width is missing");
            if (camera.distance == 0.0)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "distance is missing");
            if (camera.imageWriter == null)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "imageWriter is missing");
            if (camera.rayTracer == null)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "rayTracer is missing");

            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            return camera.clone();
        }
    }

    private Point p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double height = 0.0;
    private double width = 0.0;
    private double distance = 0.0;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    private Camera() {
    }

    /**
     * Constructs a ray through the camera's view plane.
     *
     * @param nX The number of pixels in the view plane's width.
     * @param nY The number of pixels in the view plane's height.
     * @param j  The column index of the pixel in the view plane.
     * @param i  The row index of the pixel in the view plane.
     * @return The constructed ray.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pc = p0.add(vTo.scale(distance));
        double rY = height / nY;
        double rX = width / nX;
        double yi = -(i - (nY - 1) / 2.0) * rY;
        double xj = (j - (nX - 1) / 2.0) * rX;
        Point pij = pc;
        if (!isZero(xj))
            pij = pij.add(vRight.scale(xj));
        if (!isZero(yi))
            pij = pij.add(vUp.scale(yi));
        Vector vij = pij.subtract(p0);
        return new Ray(p0, vij.normalize());
    }

    /**
     * Renders the image by casting rays through all pixels in the view plane.
     */
    public Camera renderImage() {
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();
        for (int i = 0; i < ny; i++) {
            for (int j = 0; j < nx; j++) {
                castRay(nx, ny, j, i);
            }
        }
        return this;
    }

    /**
     * Casts a ray for each pixel in the view plane and writes the corresponding color to the image.
     *
     * @param Nx     The number of pixels in the view plane's width.
     * @param Ny     The number of pixels in the view plane's height.
     * @param column The column index of the pixel in the view plane.
     * @param row    The row index of the pixel in the view plane.
     */
    private void castRay(int Nx, int Ny, int column, int row) {
        Ray ray = constructRay(Nx, Ny, column, row);
        Color color = rayTracer.traceRay(ray);
        imageWriter.writePixel(column, row, color);
    }

    /**
     * Prints a grid of pixels on the image with the specified interval and color.
     *
     * @param interval The interval between grid lines.
     * @param color    The color of the grid lines.
     */
    public void printGrid(int interval, Color color) {
        // Iterate over the x-axis at the specified interval
        for (int i = 0; i < imageWriter.getNx(); i += interval) {
            // Draw vertical grid lines
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, color);
            }
        }

        // Iterate over the y-axis at the specified interval
        for (int j = 0; j < imageWriter.getNy(); j += interval) {
            // Draw horizontal grid lines
            for (int i = 0; i < imageWriter.getNx(); i++) {
                imageWriter.writePixel(i, j, color);
            }
        }
    }


    /**
     * Writes the pixel data to the image.
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }


    /**
     * Returns a new Builder instance for creating Camera objects.
     *
     * @return The Builder instance.
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }
}
