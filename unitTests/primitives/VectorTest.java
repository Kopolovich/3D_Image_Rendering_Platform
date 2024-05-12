package primitives;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {
    Point  p1         = new Point(1, 2, 3);
    Point  p2         = new Point(2, 4, 6);
    Point  p3         = new Point(2, 4, 5);

    Vector v1         = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);
    Vector v2         = new Vector(-2, -4, -6);
    Vector v3         = new Vector(0, 3, -2);
    Vector v4         = new Vector(1, 2, 2);

    @Test
    void testConstructor(){
        assertThrows(IllegalArgumentException.class,
                    ()->new Vector(0, 0, 0),
                    "ERROR: zero vector does not throw an exception") ;
        assertThrows(IllegalArgumentException.class,
                ()->new Vector(Double3.ZERO),
                "ERROR: zero vector does not throw an exception") ;
    }

    @Test
    void testAdd() {
        assertThrows(IllegalArgumentException.class,
                    ()->v1.add(v1Opposite),
                    "ERROR: Vector + -itself does not throw an exception");
    }

    @Test
    void testScale() {
    }

    @Test
    void testLengthSquared() {
        assertTrue(isZero(v4.lengthSquared() - 9), "ERROR: lengthSquared() wrong value");
    }

    @Test
    void testLength() {
        assertTrue(isZero(v4.length() - 3), "ERROR: length() wrong value");
    }

    @Test
    void testDotProduct() {
    }

    @Test
    void testCrossProduct() {
    }

    @Test
    void testNormalize() {
    }
}