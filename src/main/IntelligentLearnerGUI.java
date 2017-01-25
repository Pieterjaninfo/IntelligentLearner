package main;

import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created by Janko on 1/19/2017.
 */
public class IntelligentLearnerGUI extends Component {
    private JFileChooser testFc;
    private JFileChooser fileFc;
    private boolean debug = true;
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JPanel initTab;
    private JPanel useTab;
    private JTextField kValueField;
    private JTextField chiValueField;
    private JButton openTrainDirButton;
    private JLabel trainDirLabel;
    private JButton trainButton;
    private JLabel trainLabel;
    private JRadioButton correctButton;
    private JRadioButton incorrectButton;
    private JPanel usePanel;
    private JButton openUserFileButton;
    private JLabel userFileLabel;
    private JPanel judgePanel;
    private JPanel resultPanel;
    private JButton classifyButton;
    private JLabel classifyLabel;
    private JButton finishButton;
    private JComboBox classComboBox;
    private JButton openTestDirButton;
    private JLabel testDirLabel;
    private JCheckBox updateCheckBox;
    private JTextArea logTextArea;
    private JButton testClassifierButton;
    private JFileChooser trainFc;

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
        //Create new file chooser for training directory
        trainFc = new JFileChooser();
        trainFc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        trainLabel.setText(trainFc.getCurrentDirectory().getPath());

        //Create new file chooser for classification file
        fileFc = new JFileChooser();
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Files (*.txt)","txt");
        fileFc.setFileFilter(txtFilter);
        fileFc.setAcceptAllFileFilterUsed(false);
        userFileLabel.setText("<No file selected>");

        //Create new file chooser for test directory
        testFc = new JFileChooser();
        testFc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        testDirLabel.setText(testFc.getCurrentDirectory().getPath());

        //Setup text area
        logTextArea.setEditable(false);

        //Setup Combo Box
        classComboBox.setEnabled(false);
        //TODO: Fill with actual classes
        String[] sa = new String[2];
        sa[0] = "Class1";
        sa[1] = "Class2";
        for (int i = 0; i < sa.length; i++ ){
            classComboBox.addItem(sa[i]);
        }

        openTrainDirButton.addActionListener((ActionEvent e) -> {
            //Handle open button action.
            int returnVal = trainFc.showOpenDialog(IntelligentLearnerGUI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File trainDir = trainFc.getSelectedFile();
                //TODO: Do something with the directory.
                trainDirLabel.setText(trainDir.getPath());
                if(debug)System.out.println("Opening: " + trainDir.getName() + ".");
            } else {
                if(debug)System.out.println("Open command cancelled by user.");
            }
        });

        openTestDirButton.addActionListener((ActionEvent e) -> {
            //Handle open button action.
            int returnVal = testFc.showOpenDialog(IntelligentLearnerGUI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File testDir = testFc.getSelectedFile();
                //TODO: Do something with the directory.
                testDirLabel.setText(testDir.getPath());
                if (debug) System.out.println("Opening: " + testDir.getName() + ".");
            } else {
                if (debug) System.out.println("Open command cancelled by user.");
            }
        });

        trainButton.addActionListener((ActionEvent e) -> {
            //TODO: Call classifier training method.
            if (debug) System.out.println(kValueField.getText());
            if (debug) System.out.println(chiValueField.getText());
            trainLabel.setText("<html>Bayesian Network has been created!<br>" +
                    "Please go on to next tab to test it.");
        });

        correctButton.addActionListener((ActionEvent e) -> {
            //TODO: Perform action based on correct classification.
            incorrectButton.setSelected(false);
            classComboBox.setEnabled(false);

        });

        incorrectButton.addActionListener((ActionEvent e) -> {
            //TODO: Perform action based on incorrect classification.
            correctButton.setSelected(false);
            classComboBox.setEnabled(true);
        });

        openUserFileButton.addActionListener((ActionEvent e) -> {
            //Handle open button action.
            int returnVal = fileFc.showOpenDialog(IntelligentLearnerGUI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileFc.getSelectedFile();
                file.getName().endsWith(".txt");
                //TODO: Do something with the file.
                userFileLabel.setText(file.getName());
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

        finishButton.addActionListener((ActionEvent e) -> {
            boolean update = updateCheckBox.isSelected();
            if(correctButton.isSelected()){
                //TODO: action when correct classification.
            } else if (incorrectButton.isSelected()){
                //TODO: update classifier based on file.
            } else {
                //TODO: add action when no radio button is selected.
            }
        });
    }
}
