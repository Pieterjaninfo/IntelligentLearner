package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Janko on 1/19/2017.
 */
public class IntelligentLearnerGUI extends Component {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel tab1;
    private JPanel tab2;
    private JTextField kValueField;
    private JTextField textField2;
    private JButton openButton;
    private JFileChooser fc;

    public static void main(String[] args) {
        JFrame frame = new JFrame("IntelligentLearnerGUI");
        frame.setContentPane(new IntelligentLearnerGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public IntelligentLearnerGUI() {
        //Create new file chooser
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                int returnVal = fc.showOpenDialog(IntelligentLearnerGUI.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    System.out.println("Opening: " + file.getName() + "." + "\n");
                } else {
                    System.out.println("Open command cancelled by user." + "\n");
                }
            }
        });
    }
}
