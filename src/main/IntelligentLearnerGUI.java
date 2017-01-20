package main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

/**
 * Created by Janko on 1/19/2017.
 */
public class IntelligentLearnerGUI extends Component {
    private JFileChooser fileFc;
    private boolean debug = true;
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JPanel initTab;
    private JPanel useTab;
    private JTextField kValueField;
    private JTextField chiValueField;
    private JButton openFileButton1;
    private JLabel fileLabel1;
    private JButton trainButton;
    private JLabel trainLabel;
    private JButton correctButton;
    private JButton incorrectButton;
    private JPanel usePanel;
    private JButton openFileButton2;
    private JLabel fileLabel2;
    private JPanel judgePanel;
    private JPanel resultPanel;
    private JButton classifyButton;
    private JLabel classifyLabel;
    private JComboBox classComboBox;
    private JFileChooser folderFc;

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

        //Create new file chooser for directories
        folderFc = new JFileChooser();
        folderFc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //Create new file chooser for classification file
        fileFc = new JFileChooser();
        //TODO: only accept .txt files.
        fileFc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        String[] sa = new String[2];
        sa[0] = "Class1";
        sa[1] = "Class2";
        //Combobox setup.
        for (int i = 0; i < sa.length; i++ ){
            classComboBox.addItem(sa[i]);
        }
        //Set fileLabel1 text.
        fileLabel1.setText(folderFc.getCurrentDirectory().getPath());

        openFileButton1.addActionListener((ActionEvent e) -> {
            //Handle open button action.
            int returnVal = folderFc.showOpenDialog(IntelligentLearnerGUI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = folderFc.getSelectedFile();
                //TODO: Do something with the file.
                fileLabel1.setText(file.getPath());
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

        correctButton.addActionListener((ActionEvent e) -> {
            //TODO: Perform action based on correct classification.
        });

        incorrectButton.addActionListener((ActionEvent e) -> {
            //TODO: Perform action based on incorrect classification.
        });

        openFileButton2.addActionListener((ActionEvent e) -> {
            //Handle open button action.
            int returnVal = fileFc.showOpenDialog(IntelligentLearnerGUI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileFc.getSelectedFile();
                //TODO: Do something with the file.
                fileLabel2.setText(file.getName());
                if(debug)System.out.println("Opening: " + file.getName() + ".");
            } else {
                if(debug)System.out.println("Open command cancelled by user.");
            }
        });

        classifyButton.addActionListener((ActionEvent e) -> {
            //TODO: Classify the file!
            classifyLabel.setText("<html>File classified as ...." +
                    "<br>Please give your feedback about the classification below!");
        });
    }
}
