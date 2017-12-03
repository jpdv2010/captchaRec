package Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextRecognizer {
    private BufferedImage image;
    private List<BufferedImage> subImageList = new ArrayList<BufferedImage>();
    private String[] symbolList = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"
            , "U", "V", "W", "X", "Y", "Z" , "1" , "2" , "3" , "4" , "5" , "6" , "7" ,"8" , "9" , "0"};
    public String letters = "";

    public TextRecognizer(BufferedImage image){
        this.image = image;
        try {
            getImageText();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void getSubImages(){
        for(int i = 22 ; i < this.image.getWidth()-22 ; i++){
            for(int j = 28 ; j < this.image.getHeight()-28 ; j++){
                this.subImageList.add(generateSubMatrix(i,j));
            }
        }
    }

    private void getImageText() throws FileNotFoundException, UnsupportedEncodingException {
        getSubImages();
        for(BufferedImage subImage : subImageList){
            LetterReconning letterReconning = new LetterReconning();
            int[] neurons = letterReconning.getLetterArray(subImage);
            for(String symbol : symbolList){
                int somatory = 0;
                double[] weights = new double[0];
                WeightsStorer weightsStorer = new WeightsStorer();
                try {
                    weights = weightsStorer.getLocalWeights(symbol);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(weights != null){
                for(int i = 0; i < neurons.length; i++){
                    somatory += neurons[i]*weights[i];
                }
                }
                if(somatory>0)letters += symbol + " ";
            }
        }
    }


    private BufferedImage generateSubMatrix(int i, int j){
        BufferedImage subImage = new BufferedImage(45,57,BufferedImage.TYPE_BYTE_GRAY);
        int z=0,w=0;
        for(int x=-22;x<=22;x++){
            for(int y=-28;y<=28;y++){
                subImage.setRGB(new Color(image.getRGB(i+x,j+y)).getRGB(),z,w);
                w++;
            }
            z++;
        }
        return subImage;
    }
}
