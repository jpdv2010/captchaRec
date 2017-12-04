package Util;

import java.io.*;

public class WeightsStorer {
    private double[] weights = new double[45*57];
    private File directory = new File(Definitions.PATH);
    private File[] allFiles = directory.listFiles();
    private String fileName;

    public WeightsStorer(String letter) throws FileNotFoundException, UnsupportedEncodingException {
        this.fileName = "wheights_" + letter + ".txt";
    }

    private void createOrUpdateFile(File file) throws FileNotFoundException, UnsupportedEncodingException {
            double[] weights = new double[45*57];
            for (int i = 0 ; i< weights.length ; i++) weights[i] = 0.0;
            updateLocalWeights(file , weights);

    }

    private void updateLocalWeights(File file, double[] weights) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer;
        writer = new PrintWriter(Definitions.PATH + fileName, "UTF-8");
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
        File file = existsFile(Definitions.PATH + fileName);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        int i=0;
        String line = reader.readLine();
        while (line != null) {
            double weight = Double.parseDouble(line);
            this.weights[i++] = weight;
            line = reader.readLine();
        }
        if(i == 0){createOrUpdateFile(file);}
        return this.weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
        try {
            updateLocalWeights(existsFile(Definitions.PATH+fileName),this.weights);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}