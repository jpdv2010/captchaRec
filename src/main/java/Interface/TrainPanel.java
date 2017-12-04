package Interface;
import Util.AreaImage;
import Util.ImageFilter;
import Util.Train;
import Util.WeightsStorer;

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

public class TrainPanel extends JFrame{
    private Container container = new Container();
    private JButton btnOpenArchive = new JButton();
    private JButton btnTrain = new JButton();
    private AreaImage areaImage=new AreaImage();
    private JTextField txtLetter = new JTextField();
    private double[] weights;
    private WeightsStorer weightsStorer;
    private BufferedImage image;

    public TrainPanel(){
        super("Treinar a rede");
        setSize(200,300);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        initCompnnents();
    }

    private void initCompnnents() {

        container=new Container();
        container.setSize(200,200);

        btnOpenArchive = new JButton("Open Image");
        btnOpenArchive.setBounds(25,10,150,30);
        btnOpenArchive.addActionListener(openImage);
        btnOpenArchive.setVisible(true);
        container.add(btnOpenArchive,BorderLayout.CENTER);

        areaImage=new AreaImage();
        areaImage.setBounds(25,70,45,57);
        areaImage.setVisible(true);
        container.add(areaImage,BorderLayout.CENTER);

        txtLetter = new JTextField();
        txtLetter.setBounds(25,150,150,30);
        txtLetter.setVisible(true);
        txtLetter.setEnabled(false);
        txtLetter.addActionListener(enableTextButton);
        container.add(txtLetter,BorderLayout.CENTER);

        btnTrain = new JButton("Train Network");
        btnTrain.setBounds(25,200,150,30);
        btnTrain.addActionListener(trainNetwork);
        btnTrain.setVisible(true);
        btnTrain.setEnabled(false);
        container.add(btnTrain,BorderLayout.CENTER);

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
                        txtLetter.setEnabled(true);
                        btnTrain.setEnabled(true);
                    }

                }
            }
        };
    }


    private ActionListener enableTextButton = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(!txtLetter.getText().equals(""))
                btnTrain.setEnabled(true);
        }
    };

    private ActionListener trainNetwork = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (image != null) {
                try {
                    weightsStorer = new WeightsStorer(txtLetter.getText());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }//-------------------------------------try to create the weight storer------------

                String letter = txtLetter.getText();

                weights=new double[image.getHeight()*image.getWidth()];
                try {
                    weights = weightsStorer.getWeights();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Train train = new Train(letter, image, weights);
                weights = train.getWeights();
                weightsStorer.setWeights(weights);
            }
            dispose();
        }
    };
}