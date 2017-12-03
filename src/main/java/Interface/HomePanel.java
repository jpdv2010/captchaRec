package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JFrame{
    private Container container = new Container();
    private JButton btnTrain = new JButton();
    private JButton btnTest = new JButton();
    private ActionListener openTrainNetwork = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            TrainPanel controlPanel = new TrainPanel();
        }
    };
    private ActionListener openTestPage = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            TestPanel testPanel = new TestPanel();
        }
    };

    public HomePanel(){
        super("Captcha Recognition");
        setSize(120,120);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        initComponnents();
    }

    private void initComponnents() {
        container = new Container();
        container.setSize(190,90);

        btnTrain = new JButton("Train Network");
        btnTrain.setSize(100,30);
        btnTrain.setLocation(10,10);
        btnTrain.setVisible(true);
        btnTrain.addActionListener(openTrainNetwork);
        container.add(btnTrain);

        btnTest = new JButton("Test");
        btnTest.setSize(100,30);
        btnTest.setLocation(10,50);
        btnTest.setVisible(true);
        btnTest.addActionListener(openTestPage);
        container.add(btnTest);

        container.setVisible(true);
        this.add(container);
    }
}
