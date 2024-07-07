package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.LinkedList;
import java.util.List;
import static primitives.Util.*;

public class Blackboard {
    /**
     * Generates a beam of rays from a given point in a given direction.
     *
     * @param n         The normal vector of the geometry that the rays are intersecting.
     * @param distance  The distance from the starting point of the beam to the center of the beam.
     * @param radius    The radius of the beam.
     * @param ray       The ray to generate the beam from.
     * @param numOfRays The number of rays to generate.
     * @return A list of rays representing the beam.
     */
    public static List<Ray> generateBeam(Vector n, double distance, double radius, Ray ray,int numOfRays) {
        List<Ray> rays = new LinkedList<>();
        rays.add(ray);
        if(numOfRays == 1 || isZero(radius))
            return rays;

        Vector dir = ray.getDirection();
        Point p0 = ray.getHead();
        Vector vX = dir.createNormal();
        Vector vY = dir.crossProduct(vX);

        Point center = isZero(distance) ? p0 : p0.add(dir.scale(distance));
        double x, y, delta = radius / numOfRays;
        Point randPoint;
        double nv = alignZero(n.dotProduct(dir));

        for (int i = 0; i < numOfRays; i++) {
            randPoint = center;
            x = random(-radius, radius);
            y = Math.sqrt(radius * radius - x * x) *randomSign();
            try{
                randPoint = randPoint.add(vX.scale(x));
                randPoint = randPoint.add(vY.scale(y));
            }
            catch (Exception e){
                i--;
            }
            Vector v12 = randPoint.subtract(p0).normalize();
            double nt = alignZero(n.dotProduct(v12));
            if(nt*nv > 0){
                rays.add(new Ray(p0, v12));
                radius -= delta;
            }
            else{
                i--;
            }
        }

        return rays;
    }


}