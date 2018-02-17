package com.generics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * Created by Alex Grischenko on 9/4/2017.
 */
public class ComparingScreenshots {

    final static Logger logger = Logger.getLogger (ComparingScreenshots.class);

    //Using java to compare 2 images:
    public static void subtractImages(String BaseLineImg, String CurrentImg, String DiffImg) throws IOException, AWTException {

            int total_no_ofPixels = 0;
            int image1_PixelColor, red, blue, green;
            int image2_PixelColor, red2, blue2, green2;
            float differenceRed, differenceGreen, differenceBlue, differenceForThisPixel;
            double nonSimilarPixels = 0l, non_Similarity = 0l;
            double roundOff = Math.round (non_Similarity * 100) / 100;

            BufferedImage img1 = ImageIO.read (new File (BaseLineImg));
            BufferedImage img2 = ImageIO.read (new File (CurrentImg));

            ColorModel model = img1.getColorModel ( );
            WritableRaster raster = img1.copyData (null);
            BufferedImage img3 = new BufferedImage (model, raster, model.isAlphaPremultiplied ( ), null);

            //Setting the color that would be used to mark visual differences
            Color myColour = new Color (255, 31, 4, 180);
            int rgb = myColour.getRGB ( );


            if (img1.getWidth ( ) == img2.getWidth ( ) && img1.getHeight ( ) == img2.getHeight ( )) {

                try {

                    logger.info ("Baseline screenshot and actual screenshot have the SAME resolution: " + img1.getHeight ( ) + " X " + img1.getWidth ( ));
                    // System.out.println ("Baseline screenshot and actual screenshot have the SAME resolution: " + img1.getHeight ( ) + " X " + img1.getWidth ( ));


                    for (int row = 0; row < img1.getWidth ( ); row++) {
                        for (int column = 0; column < img1.getHeight ( ); column++) {


                            image1_PixelColor = img1.getRGB (row, column);
                            red = (image1_PixelColor & 0x00ff0000) >> 16;
                            green = (image1_PixelColor & 0x0000ff00) >> 8;
                            blue = image1_PixelColor & 0x000000ff;

                            image2_PixelColor = img2.getRGB (row, column);
                            red2 = (image2_PixelColor & 0x00ff0000) >> 16;
                            green2 = (image2_PixelColor & 0x0000ff00) >> 8;
                            blue2 = image2_PixelColor & 0x000000ff;


                            if (red != red2 || green != green2 || blue != blue2) {
                                differenceRed = red - red2 / 255;
                                differenceGreen = (green - green2) / 255;
                                differenceBlue = (blue - blue2) / 255;
                                differenceForThisPixel = (differenceRed + differenceGreen + differenceBlue) / 3;
                                nonSimilarPixels += differenceForThisPixel;
                            }
                            total_no_ofPixels++;

                            if (image1_PixelColor != image2_PixelColor) {

                                img3.setRGB (row, column, rgb);
                            }

                        }

                    }
                    ImageIO.write (img3, "png", new File (DiffImg));

                    non_Similarity = nonSimilarPixels / total_no_ofPixels;

                    //Rounding the similarity value to 2 digits after the decimal point
                    double non_Similarity_rounded = Math.round (non_Similarity * 100.0) / 100.0;

                    if (non_Similarity_rounded > 0) {
                        logger.warn ("Total No of pixels : " + total_no_ofPixels + "\t Non-Similarity is : " + non_Similarity_rounded + "%");
                        //System.out.println ("Total No of pixels : " + total_no_ofPixels + "\t Non-Similarity is : " + non_Similarity_rounded + "%");
                    }else{
                        logger.info ("Total No of pixels : " + total_no_ofPixels + "\t Non-Similarity is : " + non_Similarity_rounded + "%");
                    }

                } catch (IOException e11) {
                    logger.error ("Error in 'subtractImages' method: " + e11.getMessage ( ));
                }

            } else {

                // get screen size using the Toolkit class
                Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );


                logger.warn ("Images are not of the same size!" + "\n" +
                        "Baseline screenshot resolution: " + img1.getHeight ( ) + " X " + img1.getWidth ( ) + "\n" +
                        "Actual screenshot resolution: " + img2.getHeight ( ) + " X " + img2.getWidth ( ) + "\n" +
                        "To run the test all Baseline screenshots should be made with screen resolution: " + screenSize.getHeight ( ) + " X " + screenSize.getWidth ( ));
                //System.out.println ("To run the test all Baseline screenshots should be made with screen resolution: " + screenSize.getHeight ( ) + " X " + screenSize.getWidth ( ));


            }

    }
}