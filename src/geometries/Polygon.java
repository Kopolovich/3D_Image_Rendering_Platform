package geometries;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan
 */
public class Polygon implements Geometry {
   /** List of polygon's vertices */
   protected final List<Point> vertices;
   /** Associated plane in which the polygon lays */
   protected final Plane plane;
   /** The size of the polygon - the amount of the vertices in the polygon */
   protected final int size;

   /**
    * Polygon constructor based on vertices list. The list must be ordered by edge
    * path. The polygon must be convex.
    * @param  vertices                 list of vertices according to their order by
    *                                  edge path
    * @throws IllegalArgumentException in any case of illegal combination of
    *                                  vertices:
    *                                  <ul>
    *                                  <li>Less than 3 vertices</li>
    *                                  <li>Consequent vertices are in the same
    *                                  point
    *                                  <li>The vertices are not in the same
    *                                  plane</li>
    *                                  <li>The order of vertices is not according
    *                                  to edge path</li>
    *                                  <li>Three consequent vertices lay in the
    *                                  same line (180&#176; angle between two
    *                                  consequent edges)
    *                                  <li>The polygon is concave (not convex)</li>
    *                                  </ul>
    */
   public Polygon(Point... vertices) {
      if (vertices.length < 3)
         throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
      this.vertices = List.of(vertices);
      size = vertices.length;

      // Generate the plane according to the first three vertices and associate the
      // polygon with this plane.
      // The plane holds the invariant normal (orthogonal unit) vector to the polygon
      plane = new Plane(vertices[0], vertices[1], vertices[2]);
      if (size == 3) return; // no need for more tests for a Triangle

      Vector  n        = plane.getNormal();
      // Subtracting any subsequent points will throw an IllegalArgumentException
      // because of Zero Vector if they are in the same point
      Vector  edge1    = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
      Vector  edge2    = vertices[0].subtract(vertices[vertices.length - 1]);

      // Cross Product of any subsequent edges will throw an IllegalArgumentException
      // because of Zero Vector if they connect three vertices that lay in the same
      // line.
      // Generate the direction of the polygon according to the angle between last and
      // first edge being less than 180 deg. It is hold by the sign of its dot product
      // with the normal. If all the rest consequent edges will generate the same sign
      // - the polygon is convex ("kamur" in Hebrew).
      boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
      for (var i = 1; i < vertices.length; ++i) {
         // Test that the point is in the same plane as calculated originally
         if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
         // Test the consequent edges have
         edge1 = edge2;
         edge2 = vertices[i].subtract(vertices[i - 1]);
         if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
      }
   }

   /**
    * Returns the normal vector to the polygon by given point
    *
    * @param point a point on the geometry's surface.
    * @return The normal vector to the polygon by given point
    */
   @Override
   public Vector getNormal(Point point) { return plane.getNormal(); }

   /**
    * Returns a list of intersection points between the given ray and the polygon.
    * If the ray does not intersect the polygon, or if the intersection point is on an edge or vertex,
    * the method returns null.
    *
    * @param ray The ray to find intersections with the polygon
    * @return A list containing the intersection point if the ray intersects the interior of the polygon,
    *         or null otherwise
    */
   @Override
   public List<Point> findIntersections(Ray ray) {
      // Find the intersection with the plane
      List<Point> planeIntersections = plane.findIntersections(ray);
      if (planeIntersections == null) {
         return null;
      }

      // There should be exactly one intersection point with the plane
      Point intersectionPoint = planeIntersections.getFirst();

      // Check if the intersection point is on one of the polygon's edges or vertices
      Point p1, p2;
      Vector edge, vp1, vp2;
      Vector normalToEdge;
      for (int i = 0; i < size; i++) {
         p1 = vertices.get(i);
         p2 = vertices.get((i + 1) % size);
         edge = p2.subtract(p1);

         // If the intersection point is equal to a vertex, return null
         if (p1.equals(intersectionPoint) || p2.equals(intersectionPoint)) {
            return null;
         }

         vp1 = intersectionPoint.subtract(p1);
         vp2 = p2.subtract(intersectionPoint);
         normalToEdge = edge.crossProduct(plane.getNormal());

         // If the intersection point lies on the edge, return null
         if (isZero(vp1.dotProduct(normalToEdge)) && alignZero(edge.dotProduct(vp1)) >= 0 && alignZero(edge.dotProduct(vp2)) >= 0) {
            return null;
         }
      }

      // Check if the intersection point is inside the polygon
      boolean sameSign = true;
      double product1 = 0, product2 = 0;

      for (int i = 0; i < size; i++) {
         p1 = vertices.get(i);
         p2 = vertices.get((i + 1) % size);
         edge = p2.subtract(p1);

         vp1 = intersectionPoint.subtract(p1);
         vp2 = intersectionPoint.subtract(p2);
         normalToEdge = edge.crossProduct(plane.getNormal());

         // Align the normal to the edge
         normalToEdge = normalToEdge.normalize();

         product1 = alignZero(vp1.dotProduct(normalToEdge));
         product2 = alignZero(vp2.dotProduct(normalToEdge));

         if (product1 * product2 < 0) {
            sameSign = false;
            break;
         }
      }

      if (!sameSign) {
         return null;
      }

      // Additional check: ensure the intersection point is within the polygon's bounds
      double edgeProduct;
      for (int i = 0; i < size; i++) {
         p1 = vertices.get(i);
         p2 = vertices.get((i + 1) % size);
         edge = p2.subtract(p1);
         vp1 = intersectionPoint.subtract(p1);
         edgeProduct = alignZero(edge.dotProduct(vp1));

         if (edgeProduct < 0) {
            return null;
         }
      }
      return List.of(intersectionPoint);
   }
}
