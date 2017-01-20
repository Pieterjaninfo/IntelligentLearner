package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created by Janko on 1/19/2017.
 */
public class IntelligentLearnerGUI extends Component {
    private boolean debug = true;
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JPanel initTab;
    private JPanel useTab;
    private JTextField kValueField;
    private JTextField chiValueField;
    private JButton openButton;
    private JLabel fileLabel;
    private JButton trainButton;
    private JLabel trainLabel;
    private JFileChooser fc;

    public static void main(String[] args) {
        //Let style automatically adapt to the operating system.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Intelligent Learner");
        frame.setContentPane(new IntelligentLearnerGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public IntelligentLearnerGUI() {

        //Create new file chooser
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //Set fileLabel text.
        fileLabel.setText(fc.getCurrentDirectory().getPath());

        openButton.addActionListener((ActionEvent e) -> {
            //Handle open button action.
            int returnVal = fc.showOpenDialog(IntelligentLearnerGUI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //TODO: Do something with the file.
                fileLabel.setText(file.getPath());
                if(debug)System.out.println("Opening: " + file.getName() + ".");
            } else {
                if(debug)System.out.println("Open command cancelled by user.");
            }
        });
        trainButton.addActionListener((ActionEvent e) -> {
            //TODO: Call classifier training method.
            trainLabel.setText("<html>Bayesian Network has been created!<br>" +
                    "Please go on to next tab to test it.");
        });
    }
}
