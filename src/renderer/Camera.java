package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Locale;
import java.util.MissingResourceException;

public class Camera implements Cloneable{

    @Override
    protected Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can not happen because this class implements Cloneable
        }
    }

    public static class Builder{
        private final Camera camera;

        public Builder() {
            this.camera = new Camera();
        }

        public Builder setLocation(Point p){
            if(p == null)
                throw new IllegalArgumentException("Location can not be null");

            camera.p0 = p;
            return this;
        }

        public Builder setDirection(Vector vTo, Vector vUp){
            if(vTo == null || vUp == null)
                throw new IllegalArgumentException("vTo and vUp can not be null");
            if(vTo.dotProduct(vUp) != 0)
                throw new IllegalArgumentException("vTo and vUp are not orthogonal");

            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        public Builder setVpSize (double height, double width){
            if(height <= 0)
                throw new IllegalArgumentException("height has to be positive");
            if(width <= 0)
                throw new IllegalArgumentException("width has to be positive");

            camera.height = height;
            camera.width = width;
            return this;
        }

        public Builder setVpDistance(double distance){
            if(distance <= 0)
                throw new IllegalArgumentException("VP distance has to be positive");

            camera.distance = distance;
            return this;
        }

        public Camera build(){
            final String GENERAL_DESCRIPTION = "Missing rendering data in camera class";

            if(camera.p0 == null)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "P0 is missing");
            if(camera.vTo == null)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "vTo is missing");
            if(camera.vUp== null)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "vUp is missing");
            if(camera.height == 0.0)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "height is missing");
            if(camera.width == 0.0)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "width is missing");
            if(camera.distance == 0.0)
                throw new MissingResourceException(GENERAL_DESCRIPTION, "Camera", "distance is missing");

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

    private Camera() {
    }

    public Ray constructRay(int nX, int nY, int j, int i){
        Point pc = p0.add(vTo.scale(distance));
        double rY = height / nY;
        double rX = width / nX;
        double yi = (i - (nY - 1) / 2.0) * rY;
        double xj = (j - (nX - 1) / 2.0) * rX;
        Point pij = pc.add(vRight.scale(xj)).add(vUp.scale(-yi));
        Vector vij = pij.subtract(p0);
        return new Ray(p0, vij.normalize());
    }

    public static Builder getBuilder(){
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

    //    public Camera(Point p0, Vector vTo, Vector vUp) {
//        this.p0 = p0;
//        this.vTo = vTo;
//        this.vUp = vUp;
//
//        vRight = vTo.crossProduct(vUp).normalize();
//    }
}
