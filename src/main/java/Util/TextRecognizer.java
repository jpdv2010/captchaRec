package Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TextRecognizer {
    private BufferedImage image;
    public List<BufferedImage> subImageList = new ArrayList<BufferedImage>();
    public String letters = "";

    public TextRecognizer(BufferedImage image){
        this.image = image;
        getSubImages();
    }

    private void getSubImages(){
        for(int i = 22 ; i < this.image.getWidth()-22 ; i++){
            for(int j = 28 ; j < this.image.getHeight()-28 ; j++){
                this.subImageList.add(generateSubMatrix(i,j));
            }
        }
    }

    public void getImageText() throws FileNotFoundException, UnsupportedEncodingException {
        getSubImages();
        for(BufferedImage subImage : subImageList){
            LetterReconning letterReconning = new LetterReconning();
            int[] neurons = letterReconning.getLetterArray(subImage);
            for(String symbol : Definitions.BASIC_ALPHANUMERIC_LIST){
                int somatory = 0;
                double[] weights = new double[0];
                WeightsStorer weightsStorer = new WeightsStorer(symbol);
                try {
                    weights = weightsStorer.getWeights();
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

    public void getImageText(BufferedImage subImage) throws FileNotFoundException, UnsupportedEncodingException {
        getSubImages();
            LetterReconning letterReconning = new LetterReconning();
            int[] neurons = letterReconning.getLetterArray(subImage);
            for(String symbol : Definitions.BASIC_ALPHANUMERIC_LIST){
                int somatory = 0;
                double[] weights = new double[0];
                WeightsStorer weightsStorer = new WeightsStorer(symbol);
                try {
                    weights = weightsStorer.getWeights();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(weights != null){
                    for(int i = 0; i < neurons.length; i++){
                        somatory += neurons[i]*weights[i];
                    }
                }
                if(somatory>=0)letters += symbol + " ";
            }
    }


    private BufferedImage generateSubMatrix(int i, int j){
        BufferedImage subImage = new BufferedImage(45,57,BufferedImage.TYPE_BYTE_GRAY);
        int h,w=0;
        for(int x=-22;x<=22;x++){
            h=0;
            for(int y=-28;y<=28;y++){
                subImage.setRGB(w,h,new Color(image.getRGB(i+x,j+y)).getRGB());
                h++;
            }
            w++;
        }
        return subImage;
    }
}
