package Util;

import java.io.*;

public class WeightsStorer {
    private double[] weights = new double[45*57];
    private String[] symbolList = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"
            , "U", "V", "W", "X", "Y", "Z" , "1" , "2" , "3" , "4" , "5" , "6" , "7" ,"8" , "9" , "0"};
    private File directory = new File("/home/joao/Documents/Projects/neppo/SeleniumTreinamento-master/txtRec/src/main/store/weights/");
    private File file;
    private File[] allFiles = directory.listFiles();
    private String PATH = "C:/Users/joaop/OneDrive/Documentos/computação/PDI-all/captchaRec/src/main/store/weights/";
    private String fileName;

    public WeightsStorer(String letter) throws FileNotFoundException, UnsupportedEncodingException {
        this.fileName = "wheights_" + letter + ".txt";
    }

    public void createOrUpdateFile(File file) throws FileNotFoundException, UnsupportedEncodingException {
            double[] weights = new double[45*57];
            for (int i = 0 ; i< weights.length ; i++) weights[i] = 0.0;
            updateLocalWeights(file , weights);

    }

    public void updateLocalWeights(File file,double[] weights) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer;
        writer = new PrintWriter(PATH + fileName, "UTF-8");
        for (Double weight : weights) writer.println(weight.toString());
        writer.close();
    }

    private File existsFile(String fileName) {
        if (allFiles != null) {
            for (File f : allFiles) {
                if (f.getName().equals(fileName)) {
                    return f;
                }
            }
        }
        return new File(fileName);
    }

    public double[] getWeights() throws IOException {
        this.file = existsFile(PATH + fileName);
        FileReader fileReader = new FileReader(this.file);
        BufferedReader reader = new BufferedReader(fileReader);
        int i=0;
        String line = reader.readLine();
        while (line != null) {
            double weight = Double.parseDouble(line);
            this.weights[i++] = weight;
            line = reader.readLine();
        }
        if(i == 0){createOrUpdateFile(this.file);}
        return this.weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
        try {
            updateLocalWeights(existsFile(PATH+fileName),this.weights);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}