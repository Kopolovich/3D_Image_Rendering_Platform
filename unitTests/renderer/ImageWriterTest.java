package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static java.awt.Color.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ImageWriterTest is a test class for the ImageWriter class.
 */
class ImageWriterTest {

    /**
     * Tests the writeToImage method of the ImageWriter class.
     * This method creates an ImageWriter object with dimensions 800x500,
     * writes pixels to create a 16x10 red grid on a yellow background, and saves the image.
     */
    @Test
    void testWriteToImage() {
        // Create an ImageWriter object with dimensions 800x500
        ImageWriter imageWriter = new ImageWriter("yellow", 800, 500);

        // Fill the entire image with yellow
        Color yellow = new Color(YELLOW);
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, yellow);
            }
        }

        // Draw the 16x10 red grid
        Color red = new Color(RED);
        for (int i = 0; i < imageWriter.getNx(); i += 50) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, red);
            }
        }
        for (int j = 0; j < imageWriter.getNy(); j += 50) {
            for (int i = 0; i < imageWriter.getNx(); i++) {
                imageWriter.writePixel(i, j, red);
            }
        }

        // Save the image
        imageWriter.writeToImage();
    }

}
