package Util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AreaImage extends JPanel{
    public BufferedImage image;

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image,0,0,this);
    }
}
