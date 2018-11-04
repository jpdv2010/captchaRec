package Interface;

import Util.AreaImage;
import Util.ImageFilter;
import Util.TextRecognizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.List;

public class TestPanel extends JFrame{
    private Container container = new Container();
    private JButton btnOpenArchive = new JButton();
    private JButton btnRecognize = new JButton();
    private JButton btnOpenFilterImageList = new JButton();
    private JTextField txtTextRecognized = new JTextField();
    private BufferedImage image;
    private AreaImage areaImage=new AreaImage();
    private List<BufferedImage> imageList;
    private TextRecognizer textRecognizer;

    public TestPanel(){
        super("Test");
        setSize(450,300);
        setResizable(false);
        setVisible(true);
        initCompnnents();
    }

    private void initCompnnents() {
        container=new Container();
        container.setSize(200,200);

        btnOpenArchive = new JButton("Open Captcha");
        btnOpenArchive.setBounds(25,10,150,30);
        btnOpenArchive.addActionListener(openImage);
        btnOpenArchive.setVisible(true);
        container.add(btnOpenArchive,BorderLayout.CENTER);

        btnOpenFilterImageList = new JButton("Filter Image List");
        btnOpenFilterImageList.setBounds(280,10,150,30);
        btnOpenFilterImageList.addActionListener(openFilterSubImagesPanel);
        btnOpenFilterImageList.setVisible(true);
        btnOpenFilterImageList.setEnabled(false);
        container.add(btnOpenFilterImageList,BorderLayout.CENTER);


        areaImage=new AreaImage();
        areaImage.setBounds(25,70,332,97);
        areaImage.setVisible(true);
        container.add(areaImage,BorderLayout.CENTER);

        btnRecognize = new JButton("Recognize");
        btnRecognize.setBounds(25,200,150,30);
        btnRecognize.addActionListener(recognizeImage);
        btnRecognize.setVisible(true);
        btnRecognize.setEnabled(false);
        container.add(btnRecognize,BorderLayout.CENTER);

        txtTextRecognized = new JTextField();
        txtTextRecognized.setBounds(200,200,230,30);
        txtTextRecognized.setVisible(true);
        txtTextRecognized.setEnabled(false);
        container.add(txtTextRecognized,BorderLayout.CENTER);

        container.setVisible(true);
        this.add(container);

    }

    private ActionListener openImage;

    {
        openImage = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fChooser = new JFileChooser();
                fChooser.setCurrentDirectory(new File("C:/Users/joaop/OneDrive/Documentos/computação/PDI-all/v2Images"));
                int res = fChooser.showOpenDialog(null);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File archive = fChooser.getSelectedFile();
                    image = null;

                    try {
                        image = new ImageFilter(ImageIO.read(archive)).image;
                    } catch (IOException exc) {
                        JOptionPane.showMessageDialog(null, "Erro ao carregar a imagem: " + exc.getMessage());
                    }
                    if(image != null){
                        areaImage.image = image;
                        areaImage.repaint();
                        btnRecognize.setEnabled(true);
                        textRecognizer = new TextRecognizer(image);
                        imageList = textRecognizer.subImageList;
                        btnOpenFilterImageList.setEnabled(true);
                    }

                }
            }
        };
    }

    private ActionListener recognizeImage = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                textRecognizer.getImageText();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            txtTextRecognized.setText(textRecognizer.letters);
        }
    };

    private ActionListener openFilterSubImagesPanel = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            FilterSubImagesPanel filterSubImagesPanel = new FilterSubImagesPanel(imageList);
        }
    };
}
