package geometries;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import geometries.Polygon;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Testing Polygons
 * @author Dan
 */
public class PolygonTests {
   /**
    * Delta value for accuracy when comparing the numbers of type 'double' in
    * assertEquals
    */
   private final double DELTA = 0.000001;

   /** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
   @Test
   public void testConstructor() {
      // ============ Equivalence Partitions Tests ==============

      // TC01: Correct concave quadrangular with vertices in correct order
      assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1),
                                           new Point(1, 0, 0),
                                           new Point(0, 1, 0),
                                           new Point(-1, 1, 1)),
                         "Failed constructing a correct polygon");

      // TC02: Wrong vertices order
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                   "Constructed a polygon with wrong order of vertices");

      // TC03: Not in the same plane
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                   "Constructed a polygon with vertices that are not in the same plane");

      // TC04: Concave quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0.5, 0.25, 0.5)), //
                   "Constructed a concave polygon");

      // =============== Boundary Values Tests ==================

      // TC10: Vertex on a side of a quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0, 0.5, 0.5)),
                   "Constructed a polygon with vertix on a side");

      // TC11: Last point = first point
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                   "Constructed a polygon with vertice on a side");

      // TC12: Co-located points
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                   "Constructed a polygon with vertice on a side");

   }

   /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
   @Test
   public void testGetNormal() {
      // ============ Equivalence Partitions Tests ==============
      // TC01: There is a simple single test here - using a quad
      Point[] pts =
         { new Point(0, 0, 1),
           new Point(1, 0, 0),
           new Point(0, 1, 0),
           new Point(-1, 1, 1) };
      Polygon pol = new Polygon(pts);
      // ensure there are no exceptions
      assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
      // generate the test result
      Vector result = pol.getNormal(new Point(0, 0, 1));
      // ensure |result| = 1
      assertEquals(1,
                 result.length(),
                 DELTA,
                 "Polygon's normal is not a unit vector");
      // ensure the result is orthogonal to all the edges
      for (int i = 0; i < 3; ++i)
         assertEquals(0d,
                       result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])),
                       DELTA,
                      "Polygon's normal is not orthogonal to one of the edges");
   }

   /**
    * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
    */
   @Test
   public void testFindIntersections() {
      final Point p1 = new Point(1, 1, 1);
      final Point p2 = new Point(3, 1, 1);
      final Point p3 = new Point(3, 3, 1);
      final Point p4 = new Point(1, 3, 1);

      Polygon polygon = new Polygon(p1, p2, p3, p4);

      // ============ Equivalence Partitions Tests ==============

      //TC01: Ray's line is inside the polygon (1 point)
      Point intersection1 = new Point(2, 2, 1);
      Ray ray1 = new Ray(new Point(2, 2, 2), new Vector(0, 0, -1));
      var result1 = polygon.findIntersections(ray1);
      assertNotNull(result1, "Ray's line should intersect the polygon");
      assertEquals(1, result1.size(), "Wrong number of points");
      assertEquals(List.of(intersection1), result1, "Ray intersects polygon");

      // TC02: Ray's line is outside the polygon against edge (0 points)
      Ray ray2 = new Ray(new Point(4, 2, 2), new Vector(0, 0, -1));
      assertNull(polygon.findIntersections(ray2),
               "Ray's line should be outside the polygon against edge");

      // TC03: Ray's line is outside the polygon against vertex (0 points)
      Ray ray3 = new Ray(new Point(4, 4, 2), new Vector(0, 0, -1));
      assertNull(polygon.findIntersections(ray3),
               "Ray's line should be outside the polygon against vertex");

      // =============== Boundary Values Tests ==================

      // TC11: Ray's line is on the edge of the polygon (0 points)
      Ray ray4 = new Ray(new Point(2, 1, 2), new Vector(0, 0, -1));
      assertNull(polygon.findIntersections(ray4),
               "Ray's line should be on the edge of the polygon");

      // TC12: Ray's line is in the vertex of the polygon (0 points)
      Ray ray5 = new Ray(new Point(3, 1, 2), new Vector(0, 0, -1));
      assertNull(polygon.findIntersections(ray5),
               "Ray's line should be in the vertex of the polygon");

      // TC13: Ray's line is on the continuation of the edge of the polygon (0 points)
      Ray ray6 = new Ray(new Point(4, 1, 2), new Vector(0, 0, -1));
      assertNull(polygon.findIntersections(ray6),
               "Ray's line should be on the continuation of the edge of the polygon");
   }
}
