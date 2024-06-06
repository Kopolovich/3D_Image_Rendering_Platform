package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

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

        // Iterate over each pixel in the image
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                // Check if the current pixel is on a 16x10 red grid
                if (i % 50 == 0 || j % 50 == 0) {  // 800/16 = 50 and 500/10 = 50
                    // Write a red pixel
                    imageWriter.writePixel(i, j, new Color(java.awt.Color.RED));
                } else {
                    // Write a yellow pixel
                    imageWriter.writePixel(i, j, new Color(java.awt.Color.YELLOW));
                }
            }
        }

        // Save the image
        imageWriter.writeToImage();
    }
}
