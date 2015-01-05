package AudioVisualProg;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Read each pixels off an image file and store RGB values in a 2D array. 
 * This 2D array can then be used to create a row vector (stored in a file - vector.txt) by dividing R/G/B. 
 * 
 * References: 
 * http://alvinalexander.com/blog/post/java/getting-rgb-values-for-each-pixel-in-image-using-java-bufferedi
 * http://stackoverflow.com/questions/11380062/what-does-value-0xff-do-in-java
 * 
 * @author Emil Tan
 */
public class ReadImage {

	/** A 2D array that stores RGB values of each pixels of the image. */
	private int[][] imageArray; 
	
	/**
	 * Constructor. Takes an image file as an argument,
	 * and stores RGB values of each pixels into 2D array imageArray. 
	 * 
	 * @param imageFile File path to the image file to analyse. 
	 */
	public ReadImage(String imageFile) {
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(imageFile));
			
			imageArray = imageToArray(bufferedImage); 
		} catch(Exception e) {
			System.out.println("An error occurred: " + e.getMessage()); 
			System.out.println("Exiting...");
			System.exit(0);
		}
	}
	
	/**
	 * Takes an image, read RGB values of each pixels and store it in a 2D array. 
	 * 
	 * @param bufferedImage An image file that has been read and buffered.
	 * @return 2D array that stores RGB values of each pixels of the image. 
	 */
	private int[][] imageToArray(BufferedImage bufferedImage) {
		int imageHeight = bufferedImage.getHeight(); 
		int imageWidth = bufferedImage.getWidth(); 
		
		int imageArea = imageHeight * imageWidth; 
		
		// Each pixels has R, G, and B value. 
		int[][] imageArray = new int[imageArea][3]; 
		
		int populatingElement = 0; 
		
		// Look into each pixels of the image
		for(int height = 0; height < imageHeight; height++) {
			for(int width = 0; width < imageWidth; width++) {
				int pixel = bufferedImage.getRGB(width, height);
				
				int red = (pixel >> 16) & 0xff; 
				int green = (pixel >> 8) & 0xff; 
				int blue = (pixel) & 0xff; 
				
				imageArray[populatingElement][0] = red; 
				imageArray[populatingElement][1] = green;
				imageArray[populatingElement][2] = blue;
				
				populatingElement++; 
			}
		}
		
		return imageArray; 
	}
	
	/**
	 * Get the array of RGB values stored in a 2D array.
	 * 
	 * @return 2D array of RGB values. 
	 */
	public int[][] getImageArray() {
		return imageArray; 
	}
	
	/** Print imageArray on screen. */
	public void printImageArray() {
		for(int row = 0; row < imageArray.length; row++) {
			for(int column = 0; column < imageArray[0].length; column++) {
				System.out.print(imageArray[row][column] + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Takes a 2D array of RGB values and produce a row vector (stored in a file).
	 * 
	 * @param imageArray 2D array of RGB values. 
	 */
	public void imageArrayToRowVector(int[][] imageArray) {
		try {
			Random randomGenerator = new Random(); 
			PrintWriter writer = new PrintWriter("vector.txt");
			
			for(int row = 0; row < imageArray.length; row++) {
				// Make sure we will not be dividing by 0. 
				for(int column = 0; column < imageArray[0].length; column++) {
					imageArray[row][column] = oneIfZero(imageArray[row][column]);
				}
				// Create element of row vector as R/G/B.
				double element = ((double)imageArray[row][0] / (double)imageArray[row][1] / (double)imageArray[row][2]);
				// Multiply by 10 so the value is not too small.
				element *= 10; 
				// Randomly gives it positive or negative value. 
				if(randomGenerator.nextInt(2) == 0) {
					element *= -1; 
				}
				writer.println(element);
			}
			
			writer.close();
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage()); 
			System.out.println("Exiting...");
			System.exit(0);
		}
	}
	
	/**
	 * Change the value to 1 if the it is 0. 
	 * 
	 * @param value An integer.
	 * @return 1 if the argument is 0, else return the value as it is. 
	 */
	private int oneIfZero(int value) {
		if(value == 0.0) {
			return 1; 
		} else {
			return value; 
		}
	}
}
