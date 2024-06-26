package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{

    private Point position;
    private double kC = 1d, kL = 0d, kQ = 0d;

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double distance = position.distance(p);
        double distanceSquared = position.distanceSquared(p);
        return intensity.scale(1 / (kC + kL * distance + kQ * distanceSquared));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }

    protected double getReduction(Point p) {
        double dSquare = position.distanceSquared(p);
        return (kC + kL * Math.sqrt(dSquare) + kQ * dSquare);
    }
}
