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
import java.io.IOException;

public class TestPanel extends JFrame{
    private Container container = new Container();
    private JButton btnOpenArchive = new JButton();
    private JButton btnRecognize = new JButton();
    private JTextField txtTextRecognized = new JTextField();
    private BufferedImage image;
    private AreaImage areaImage=new AreaImage();

    public TestPanel(){
        super("Test");
        setSize(450,300);
        setResizable(false);
        setLocationRelativeTo(null);
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
                    }

                }
            }
        };
    }

    private ActionListener recognizeImage = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            TextRecognizer textRecognizer = new TextRecognizer(image);
            txtTextRecognized.setText(textRecognizer.letters);
        }
    };
}
