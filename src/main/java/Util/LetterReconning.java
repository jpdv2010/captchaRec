package Util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LetterReconning {

    public LetterReconning (){

    }

    public int[] getLetterArray(BufferedImage image){
        int[] neurArray = new int[ image.getHeight() * image.getWidth() ];

        //int[] histogram = getHistogram(image);
        //int major = getMajor(histogram);
        //int minor=getMinor(histogram);
        double average = 0;

        int rgb = image.getRGB(0,0);
        int index = 0;
        for( int i = 0 ; i < image.getWidth() ; i++ ){
            for( int j = 0 ; j < image.getHeight() ; j++ ){
                average+=image.getRGB(i,j);
            }
        }
        average = average/(image.getWidth()*image.getHeight());

        for( int i = 0 ; i < image.getWidth() ; i++ ){
            for( int j = 0 ; j < image.getHeight() ; j++ ){
                if( image.getRGB(i,j) > average){
                    neurArray[index++] = -1;
                } else {
                    neurArray[index++] = 1;
                }
            }
        }

        return neurArray;
    }

    /*private int getMinor(int[] histogram) {
        int minor=0;
        for(int i:histogram){
            if(i<minor)minor=i;
        }
        return minor;
    }*/

    private int getMajor(int[] histogram) {
        int major = 0;
        for(int i : histogram){
            major = Math.max(major,i);
        }
        return major;
    }

    private int[] getHistogram(BufferedImage image) {
        int[] histogram = new int[256];

        for(int i = 0 ; i < image.getWidth() ; i++){
            for(int j = 0 ; j < image.getHeight() ; i++){
                Color c = new Color( image.getRGB(i,j) );
                histogram[c.getRed()]++;
                histogram[c.getBlue()]++;
                histogram[c.getGreen()]++;
            }
        }
        return histogram;
    }
}
