package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

public class SpotLight extends PointLight{

    private Vector direction;

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    public SpotLight setkC(double kC) {
        return (SpotLight) super.setkC(kC);
    }

    public SpotLight setkL(double kL) {
        return (SpotLight) super.setkC(kL);
    }

    public SpotLight setkQ(double kQ) {
        return (SpotLight) super.setkC(kQ);
    }

    @Override
    public Color getIntensity(Point p) {
        return (super.getIntensity(p)).scale(Math.max(0,getL(p).dotProduct(direction)));

    }


}
