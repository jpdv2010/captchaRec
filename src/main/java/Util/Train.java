package Util;
import Interface.TestArray;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Train {
    private double[] weights;

    public Train(String symbol, BufferedImage image, double[] weights){
        this.weights=weights;
        double[] deltaW=new double[image.getHeight()*image.getWidth()];
        LetterReconning letterReconning=new LetterReconning();
        int[] neurons=letterReconning.getLetterArray(image);
        TestArray testArray = new TestArray(neurons);

        double majorError = 1;
        int desiredOutput;
        double quadraticError;
        int cicles = 0;

        double minimError = 0.001;
        while (majorError > minimError && cicles<10)
        {
            majorError = 0;
            for (String aSymbolList : Definitions.BASIC_ALPHANUMERIC_LIST) {
                if (symbol.equals(aSymbolList))//DEFINE A SAIDA DESEJADA
                {
                    desiredOutput = 1;
                } else {
                    desiredOutput = -1;
                }
                double somatory = 0;
                for (int k = 0; k < image.getHeight() * image.getWidth(); k++)//-----------calculo de somatorio
                {
                    somatory += (neurons[k] * this.weights[k]);
                }
                if (somatory >= 0) {
                    somatory = 1;
                } else {
                    somatory = -1;
                }

                double erro = desiredOutput - somatory;//CALCULA O ERRO
                quadraticError = Math.pow(erro, 2) / 2;//CALCULA O ERRO QUADRATICO
                if (quadraticError > majorError) {
                    majorError = quadraticError;
                }//ATUALOZA O MAIOR ERRO Q.

                for (int j = 0; j < image.getWidth() * image.getHeight(); j++)//ATUALIZA OS PESOS DA LETRA
                {
                    double learningRate = 0.02;
                    deltaW[j] = erro * neurons[j] * learningRate;
                    this.weights[j] += deltaW[j];
                }
                try {
                    WeightsStorer weightsStorer = new WeightsStorer(symbol);
                    weightsStorer.setWeights(this.weights);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            cicles ++;
        }
        System.out.println("Ciclos: "+cicles);

    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }
}