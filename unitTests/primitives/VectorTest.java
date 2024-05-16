package primitives;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
/**
 * Unit tests for the Vector class.
 */
class VectorTest {
    // Defining some sample points and vectors for testing
    Point p1 = new Point(2, 4, 6);
    Vector v1 = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);
    Vector v4 = new Vector(1, 2, 2);

    /**
     * Test method for ctors.
     */
    @Test
    void testConstructor(){
        // =============== Boundary Values Tests ==================
        // TC11: Test constructor with zero vector (expecting IllegalArgumentException)
        assertThrows(IllegalArgumentException.class,
                    ()->new Vector(0, 0, 0),
                    "ERROR: zero vector does not throw an exception") ;
        // TC12: Test constructor with zero vector (using Double3 constant) (expecting IllegalArgumentException)
        assertThrows(IllegalArgumentException.class,
                ()->new Vector(Double3.ZERO),
                "ERROR: zero vector does not throw an exception") ;
    }

    /**
     * Test method for vector add.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test addition of two vectors
        assertEquals(v1Opposite, v1.add(v2), "ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: Test addition of a vector to its opposite (expecting IllegalArgumentException)
        assertThrows(IllegalArgumentException.class,
                    ()->v1.add(v1Opposite),
                    "ERROR: Vector + -itself does not throw an exception");
    }

    /**
     * Test method for Vector subtract.
     */
    @Test
    void testSubtract(){
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test subtraction of two vectors
        assertEquals(new Vector(3,6,9), v1.subtract(v2), "ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: Test subtraction of a vector from itself (expecting IllegalArgumentException)
        assertThrows(IllegalArgumentException.class,
                    ()->v1.subtract(v1),
                    "ERROR: Vector - itself does not throw an exception");

    }

    /**
     * Test method for Vector scale.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test scaling of a vector by a scalar
        assertEquals(new Vector(p1.xyz), v1.scale(2), "ERROR: Scaling this vector does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: Test scaling of a vector by zero (expecting IllegalArgumentException)
        assertThrows(IllegalArgumentException.class,
                    ()-> v1.scale(0),
                    "ERROR: Scaling vector by 0 does not throw an exception");
    }

    /**
     * Test method for Vector lengthSquared.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test squared length of a vector
        assertTrue(isZero(v4.lengthSquared() - 9),
                   "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for Vector length.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test length of a vector
        assertTrue(isZero(v4.length() - 3), "ERROR: length() wrong value");
    }

    /**
     * Test method for Vector dotProduct.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC02: Test dot product of two vectors
        assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");

        // =============== Boundary Values Tests ==================
        // TC01: Test dot product of orthogonal vectors (expecting zero)
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for Vector crossProduct.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test cross product of two vectors
        Vector vr = v1.crossProduct(v3);
        assertTrue(isZero(vr.length() - v1.length() * v3.length()),
                   "ERROR: crossProduct() wrong result length");
        assertTrue(isZero(vr.dotProduct(v1)) && isZero(vr.dotProduct(v3)), "ERROR: crossProduct() result is not orthogonal to its operands");

        // =============== Boundary Values Tests ==================
        // TC11: Test cross product of parallel vectors (expecting IllegalArgumentException)
        assertThrows(IllegalArgumentException.class,
                ()-> v1.crossProduct(v2),
                "ERROR: crossProduct() for parallel vectors does not throw an exception");
        // TC12: Test cross product of opposite vectors (expecting IllegalArgumentException)
        assertThrows(IllegalArgumentException.class,
                    ()-> v1.crossProduct(v1Opposite),
                    "ERROR: crossProduct() for opposite vectors does not throw an exception");
    }

    /**
     * Test method for Vector normalize.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test normalization of a vector
        Vector u = v1.normalize();
        assertTrue(isZero(u.length() - 1), "ERROR: the normalized vector is not a unit vector");
        assertThrows(IllegalArgumentException.class,
                    ()-> v1.crossProduct(u),
                    "ERROR: the normalized vector is not parallel to the original one");
        assertFalse(v1.dotProduct(u) < 0, "ERROR: the normalized vector is opposite to the original one");
    }
}