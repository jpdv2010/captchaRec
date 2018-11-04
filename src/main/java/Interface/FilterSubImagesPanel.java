package Interface;

import Util.AreaImage;
import Util.TextRecognizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by joaop on 12/12/2017.
 */
public class FilterSubImagesPanel extends JFrame {
    private Container container = new Container();
    private AreaImage areaImage = new AreaImage();
    private AreaImage nextImageArea = new AreaImage();
    private AreaImage previouslyImageArea = new AreaImage();
    private JButton btnNext = new JButton();
    private JButton btnPrev = new JButton();
    private JTextField txtTextRecognized = new JTextField();
    private List<BufferedImage> imageList;
    private BufferedImage nImage;
    private BufferedImage pImage;
    private BufferedImage aImage;
    private TextRecognizer textRecognizer;
    private int index;

    public FilterSubImagesPanel(List<BufferedImage> imageList){
        super("Filtrar as subimagens");
        this.imageList = imageList;
        index = 0;
        setSize(180,290);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        initCompnnents();
    }

    private void initCompnnents() {
        container=new Container();
        container.addKeyListener(nextImageKL);
        container.setSize(200,200);

        btnPrev = new JButton("<");
        btnPrev.setBounds(10,10,50,30);
        btnPrev.addActionListener(prevImage);
        btnPrev.setVisible(true);
        container.add(btnPrev, BorderLayout.CENTER);

        btnNext = new JButton(">");
        btnNext.setBounds(120,10,50,30);
        btnNext.addActionListener(nextImage);
        btnNext.setVisible(true);
        container.add(btnNext, BorderLayout.CENTER);

        aImage = imageList.get(0);
        areaImage=new AreaImage();
        areaImage.setBounds(70,80,45,57);
        areaImage.setVisible(true);
        areaImage.image = aImage;
        container.add(areaImage,BorderLayout.CENTER);

        nImage = imageList.get(1);
        nextImageArea=new AreaImage();
        nextImageArea.setBounds(70,150,45,57);
        nextImageArea.setVisible(true);
        nextImageArea.image = nImage;
        container.add(nextImageArea,BorderLayout.CENTER);

        previouslyImageArea=new AreaImage();
        previouslyImageArea.setBounds(70,10,45,57);
        previouslyImageArea.setVisible(true);
        container.add(previouslyImageArea,BorderLayout.CENTER);

        txtTextRecognized = new JTextField();
        txtTextRecognized.setBounds(10,220,160,30);
        txtTextRecognized.setVisible(true);
        txtTextRecognized.setEnabled(false);
        container.add(txtTextRecognized,BorderLayout.CENTER);

        container.setVisible(true);
        this.add(container);
    }

    private ActionListener prevImage = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(index > 0 && previouslyImageArea.image != null){ index-=100;}else{index = 0;}
            /*pImage = aImage;
            aImage = nImage;
            nImage = imageList.get(index + imageList.indexOf(nImage));
            previouslyImageArea.image = pImage;
            areaImage.image = aImage;
            nextImageArea.image = nImage;*/
        }
    };

    private KeyListener nextImageKL = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_W){
                changeImages();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };

    private ActionListener nextImage = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeImages();
        }
    };

    private void changeImages(){
        if(index < imageList.size()) {
            pImage = aImage;
            aImage = nImage;
            nImage = imageList.get(index++);
            previouslyImageArea.image = pImage;
            areaImage.image = aImage;
            nextImageArea.image = nImage;
            previouslyImageArea.repaint();
            areaImage.repaint();
            nextImageArea.repaint();
            textRecognizer = new TextRecognizer(aImage);
            try {
                textRecognizer.getImageText(aImage);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            txtTextRecognized.setText(textRecognizer.letters);
        }
        else
        {
            index = imageList.size();
        }
    }
}
