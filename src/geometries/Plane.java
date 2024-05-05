package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{

    public Plane(Point p1, Point p2, Point p3) {
    }

    public Vector getNormal() {
        return null;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
