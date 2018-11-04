package Interface;

import Util.AreaImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by joaop on 04/12/2017.
 */
public class TestArray extends JFrame {
    private Container container = new Container();
    private AreaImage areaImage=new AreaImage();
    private BufferedImage image = new BufferedImage(45,57,BufferedImage.TYPE_BYTE_GRAY);

    public TestArray(int[] neurons){
        super("Test");
        setSize(100,200);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        initCompnnents();
        setImage(neurons);
    }

    private void setImage(int[] neurons) {
        int index = 0;
        for(int i=0;i<45;i++){
            for(int j=0;j<57;j++){
                if(neurons[index++] == -1)
                {
                    image.setRGB(i,j,-1);
                } else {
                    image.setRGB(i, j, 255);
                }
            }
        }
        areaImage.image = image;
        areaImage.repaint();
    }

    private void initCompnnents() {
        container=new Container();
        container.setSize(200,200);

        areaImage=new AreaImage();
        areaImage.setBounds(25,70,332,97);
        areaImage.setVisible(true);
        container.add(areaImage,BorderLayout.CENTER);

        container.setVisible(true);
        this.add(container);
    }
}
