/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prashant.resources;

/**
 *
 * @author Prashant
 */
import com.prashant.stocks.addItem;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

public class dir {

    private static String fileName, text;

    public static void makeit(String file) {
        File theDir = new File("Data");
        if (!theDir.exists()) {

            try {
                theDir.mkdirs();
                BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
                out.close();
            } catch (Exception ex) {
                System.out.println("Some error occured while setting up the application...");
                javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        }
    }

    public static void writeFile(String file, String txt) {

        fileName = file;
        text = txt;
        makeit(fileName);
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("Data/" + fileName, true));
            out.write(text + '\n');
            out.close();
        } catch (Exception ex) {
            System.out.println("Couldn't write records to backup...");
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public static String readFile(String path) {
        String text = "", line;
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(path);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                text += line + "\n";
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(Exception ex) {
            System.out.println("Couldn't read records...");
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
        }                
        
        return text;
    }

    public static boolean hasSpecial(String str) {
        boolean b;
        char[] strc = str.toCharArray();
        for (char c : strc) {
            String s = "" + c;
            if (s.matches("[^a-z A-Z0-9]")) {
                return true;
            }
        }
        return false;
    }

    public static void resImage(File image, String dst) {
        try {
            Dimension img = getImageDimension(image);

            Dimension newImg = getScaledDimension(img, new Dimension(237, 255));

            resize(image.getAbsolutePath(), new File(dst).getAbsolutePath(), newImg.width, newImg.height);
        } catch (Exception ex) {
            dir.writeFile("errLog", "ERROR IN RESIZING THE IMAGE   " + ex.getMessage() + "\n");
        }
    }

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     *
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param scaledWidth absolute width in pixels
     * @param scaledHeight absolute height in pixels
     * @throws IOException
     */
    public static void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));

    }

    /**
     * Gets image dimensions for given file
     *
     * @param imgFile image file
     * @return dimensions of image
     * @throws IOException if the file is not a known image
     */
    public static Dimension getImageDimension(File imgFile) throws IOException {
        int pos = imgFile.getName().lastIndexOf(".");
        if (pos == -1) {
            throw new IOException("No extension for file: " + imgFile.getAbsolutePath());
        }
        String suffix = imgFile.getName().substring(pos + 1);
        Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
        while (iter.hasNext()) {
            ImageReader reader = iter.next();
            try {
                ImageInputStream stream = new FileImageInputStream(imgFile);
                reader.setInput(stream);
                int width = reader.getWidth(reader.getMinIndex());
                int height = reader.getHeight(reader.getMinIndex());
                return new Dimension(width, height);
            } catch (IOException e) {
                writeFile("errLog", "Error reading: " + imgFile.getAbsolutePath() + e.getMessage() + "\n");
            } finally {
                reader.dispose();
            }
        }

        throw new IOException("Not a known image file: " + imgFile.getAbsolutePath());
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }

}
