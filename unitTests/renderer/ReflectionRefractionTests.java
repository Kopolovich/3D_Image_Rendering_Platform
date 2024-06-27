/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.*;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   /** Scene for the tests */
   private final Scene          scene         = new Scene("Test scene");
   /** Camera builder for the tests with triangles */
   private final Camera.Builder cameraBuilder = Camera.getBuilder()
      .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
      .setRayTracer(new SimpleRayTracer(scene));

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheres() {
      scene.geometries.add(
                           new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE))
                              .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                           new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED))
                              .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
      scene.lights.add(
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                          .setkL(0.0004).setkQ(0.0000006));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
         .setVpSize(150, 150)
         .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
         .build()
         .renderImage()
         .writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheresOnMirrors() {
      scene.geometries.add(
                           new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100))
                              .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)
                                 .setkT(new Double3(0.5, 0, 0))),
                           new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20))
                              .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000))
                              .setEmission(new Color(20, 20, 20))
                              .setMaterial(new Material().setkR(1)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000))
                              .setEmission(new Color(20, 20, 20))
                              .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));
      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
         .setkL(0.00001).setkQ(0.000005));

      cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
         .setVpSize(2500, 2500)
         .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
         .build()
         .renderImage()
         .writeToImage();
   }

   /** Produce a picture of a two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() {
      scene.geometries.add(
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150))
                              .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                              .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                           new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE))
                              .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)));
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      scene.lights.add(
                       new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                          .setkL(4E-5).setkQ(2E-7));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
         .setVpSize(200, 200)
         .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
         .build()
         .renderImage()
         .writeToImage();
   }

   /** Produce a picture of multiple geometries lighted by a spot light */
   @Test
   public void testMultipleObjects1() {
      scene.geometries.add(
              // Plane
              new Plane(new Point(0, 0, 0), new Vector(0, 0, 1))
                      .setEmission(new Color(0, 20, 20))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkR(0.02)),

              // Sphere 1
              new Sphere(40, new Point(0, -50, 50))
                      .setEmission(new Color(0, 0, 255))
                      .setMaterial(new Material().setkD(0.6).setkS(0.4).setnShininess(100).setkR(0.3)),

              // Sphere 2
              new Sphere(30, new Point(70, 70, 30))
                      .setEmission(new Color(255, 0, 0))
                      .setMaterial(new Material().setkD(0.7).setkS(0.5).setnShininess(80).setkT(0.35).setkR(0.1)),

              // Sphere 3
              new Sphere(20, new Point(-70, 50, 50))
                      .setEmission(new Color(0, 255, 0))
                      .setMaterial(new Material().setkD(0.6).setkS(0.4).setnShininess(100).setkR(0.3)),

              // Triangle 1
              new Triangle(new Point(-100, -100, 0), new Point(100, -100, 0), new Point(0, 100, 0))
                      .setEmission(new Color(0, 255, 0))
                      .setMaterial(new Material().setkD(0.6).setkS(0.4).setnShininess(50).setkR(0.2)),

              // Triangle 2
              new Triangle(new Point(-120, -50, 30), new Point(-120, 50, 30), new Point(0, 0, 100))
                      .setEmission(new Color(255, 255, 255))
                      .setMaterial(new Material().setkD(0.8).setkS(0.6).setnShininess(70).setkR(0.2))
      );

      scene.setAmbientLight(new AmbientLight(new Color(30, 30, 30), 0.1));

      scene.lights.add(new SpotLight(new Color(1000, 1000, 1000), new Point(-100, 100, 100), new Vector(1, -1, -1))
              .setkL(0.0004).setkQ(0.0001));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(850)
              .setVpSize(200, 200)
              .setImageWriter(new ImageWriter("MultipleObjects1", 600, 600))
              .build()
              .renderImage()
              .writeToImage();
   }




   @Test
   public void complexScene() {
      scene.setBackground(new Color(173, 216, 230)); // Light blue background

      scene.geometries.add(
              // Reflective Plane
              new Plane(new Point(0, -50, 0), new Vector(0, 1, 0))
                      .setEmission(new Color(200, 200, 200))
                      .setMaterial(new Material().setkR(0.6)),

              // Transparent Sphere
              new Sphere(30d, new Point(-60, 20, -100))
                      .setEmission(new Color(0, 0, 255))
                      .setMaterial(new Material().setkT(0.5).setkS(0.5).setnShininess(100)),

              // Reflective Sphere
              new Sphere(30d, new Point(60, 20, -100))
                      .setEmission(new Color(255, 0, 0))
                      .setMaterial(new Material().setkR(0.8).setkS(0.5).setnShininess(100)),

              // Semi-Transparent Sphere
              new Sphere(40d, new Point(0, 40, -150))
                      .setEmission(new Color(0, 255, 0))
                      .setMaterial(new Material().setkT(0.3).setkS(0.5).setnShininess(100)),

              // Triangle casting shadow on plane
              new Triangle(new Point(-50, 0, -50), new Point(50, 0, -50), new Point(0, 100, -50))
                      .setEmission(new Color(50, 50, 50))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100))
      );

      scene.lights.add(
              new SpotLight(new Color(1000, 600, 400), new Point(100, 100, 200), new Vector(-1, -1, -3))
                      .setkL(0.0004).setkQ(0.0000006)
      );

      cameraBuilder.setLocation(new Point(0, 200, 200)).setVpDistance(300)
              .setVpSize(200, 200)
              .setImageWriter(new ImageWriter("complexScene", 600, 600))
              .build()
              .renderImage()
              .writeToImage();
   }


}
