package Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created by joaop on 25/09/2017.
 * */
public class ImageFilter {
    public BufferedImage image;
    private static final int[][] filterMedian= {{1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}};

    public ImageFilter(BufferedImage image){
        this.image=image;
        this.image=imageShadedOfGray();
        this.image=filterImage();
    }

    private BufferedImage imageShadedOfGray() {
        BufferedImage processedImage=new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        for(int i=0;i<image.getWidth();i++){
            for(int j=0;j<image.getHeight();j++) {
                processedImage.setRGB(i,j,image.getRGB(i,j));
            }
        }
        return processedImage;
    }

    private BufferedImage filterImage(){
        BufferedImage filteredImage=new BufferedImage(image.getWidth(),image.getHeight(),1);
        int count=0;
        while(count<10){
            for(int i=0;i<image.getWidth();i++){
                for(int j=0;j<image.getHeight();j++){
                    if(i>1 && j>1 && i<image.getWidth()-1 && j<image.getHeight()-1){
                        Color c=getMedianFilter(i,j);
                        filteredImage.setRGB(i,j,c.getRGB());
                    }else{
                        filteredImage.setRGB(i,j,image.getRGB(i,j));
                    }
                }
            }
            count++;
        }

        return filteredImage;
    }
/*
    private Color getFilter1(int i, int j) {
        int r=0,g=0,b=0;
        List<Color> colorList=new ArrayList<Color>();
        for(int x=-1;x<=1;x++){
            for(int y=-1;y<=1;y++){
                colorList.add(new Color(image.getRGB(i+x,j+y)));
            }
        }
        for(Color c:colorList){
            r+=c.getRed();
            g+=c.getGreen();
            b+=c.getBlue();
        }
        return new Color(r/9,g/9,b/9);
    }*/

//---------------------logica baseada na que eu achei net---------------------

    private Color getMedianFilter(int i,int j){
        int r=0,g=0,b=0;
        Color[][] matCores=new Color[3][3];
        for(int x=-1;x<=1;x++){
            for(int y=-1;y<=1;y++){
                matCores[x+1][y+1]=new Color(image.getRGB(i+x,j+y));
            }
        }
        List<Color> listCores=new ArrayList<Color>();

        for(int x=0;x<3;x++){
            listCores.addAll(Arrays.asList(matCores[x]).subList(0, 3));
        }
        listCores=sortList(listCores);
        Color median=listCores.get(4);

        for(int x=-1;x<=1;x++){
            for(int y=-1;y<=1;y++){
                r+=median.getRed();
                g+=median.getGreen();
                b+=median.getBlue();
            }
        }
        return new Color(r/sum(),g/sum(),b/sum());
    }

    private List<Color> sortList(List<Color> colorList){
        for(int i=0;i<colorList.size();i++){
            for(int j=1;j<colorList.size();j++){
                if(colorList.get(i).getRGB()>colorList.get(j).getRGB()) {
                    Color aux = colorList.get(i);
                    colorList.set(i, colorList.get(j));
                    colorList.set(j, aux);
                }
            }

        }
        return colorList;
    }


/*
    private Color getFilter2(int i,int j){
        int r=0,g=0,b=0;
        for(int x=-1;x<=1;x++){
            for(int y=-1;y<=1;y++) {
                r += filterMedian[x + 1][y + 1] * (new Color(image.getRGB(i+x, j+y)).getRed());
                g += filterMedian[x + 1][y + 1] * (new Color(image.getRGB(i+x, j+y)).getGreen());
                b += filterMedian[x + 1][y + 1] * (new Color(image.getRGB(i+x, j+y)).getBlue());
            }
        }
        return new Color(r/sum(),g/sum(),b/sum());
    }*/

    private int sum() {
        int sum = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                sum += filterMedian[x][y];
            }
        }
        return sum;
    }
}