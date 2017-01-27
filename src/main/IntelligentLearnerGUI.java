package main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Janko on 1/19/2017.
 */
public class IntelligentLearnerGUI extends Component {
    private boolean debug = false;

    private JFileChooser testFc;
    private JFileChooser fileFc;
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JPanel initTab;
    private JPanel useTab;
    private JSpinner kValueField;
    private JSpinner chiValueField;
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
    private JPanel trainClfPanel;
    private JCheckBox updateClassifierCheckBox;
    private JSpinner intSpinner;
    private JFileChooser trainFc;

    private String selectedFileName = "";
    private String selectedClassName = "";
    private HashMap<String, Integer> selectedWords;

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
        DocumentProcessing dc = new DocumentProcessing();
        Classifier cf = new Classifier();

        //Disable use and test tabs
        tabbedPane1.setEnabledAt(1,false);
        tabbedPane1.setEnabledAt(2, false);

        //Set kValueField properties
        SpinnerNumberModel kModel = new SpinnerNumberModel(1, 1, null, 1);
        kValueField.setModel(kModel);

        //Set chiValueField properties
        SpinnerNumberModel chiModel = new SpinnerNumberModel(0.0, 0.0, null, 0.1);
        chiValueField.setModel(chiModel);

        //
        SpinnerNumberModel intModel = new SpinnerNumberModel(0, 0, null, 1);
        intSpinner.setModel(intModel);

        //Create new file chooser for training directory
        trainFc = new JFileChooser();
        trainFc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        trainFc.setSelectedFile(trainFc.getCurrentDirectory());
        trainDirLabel.setText(trainFc.getCurrentDirectory().getPath());

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

        //Setup Combo Box for manual classification
        classComboBox.setEnabled(false);

        //Setup automatic classification area
        testClassifierButton.setEnabled(false);
        logTextArea.setEditable(false);
        DefaultCaret caret = (DefaultCaret) logTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        //TODO: Fill with actual classes

        openTrainDirButton.addActionListener((ActionEvent e) -> {
            //Handle open button action.
            int returnVal = trainFc.showOpenDialog(IntelligentLearnerGUI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File trainDir = trainFc.getSelectedFile();
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

                testDirLabel.setText(testDir.getPath());
                testClassifierButton.setEnabled(true);
                if (debug) System.out.println("Opening: " + testDir.getName() + ".");
            } else {
                if (debug) System.out.println("Open command cancelled by user.");
            }
        });

        //TRAIN THE CLASSES
        trainButton.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    trainButton.setEnabled(false);
                    ImageIcon loading = new ImageIcon("resources/images/ajax-loader.gif");
                    trainLabel.setIcon(loading);
                    trainLabel.setText("Please wait...");

                }
            });

            //Start training the classifier on a new thread
            Thread trainThread = new Thread() {
                public void run() {
                    String filepath = trainFc.getSelectedFile().getAbsolutePath() + "\\";
                    if (debug) System.out.println("path: " + filepath);
                    cf.setSmoothingFactor((int) kValueField.getValue());
                    Tokenizer.setChiSquareValue((double) chiValueField.getValue());
                    try {
                        dc.scanTrainDocuments(filepath);
                    } catch (EmptyFolderException e1) {
                        trainLabel.setIcon(null);
                        trainLabel.setText("<html>The selected folder did not contain any .txt files.<br>" +
                                "Please select a new training folder.");
                        e1.printStackTrace();
                        return;
                    }
                    DataClass.setupClasses(true);
                    // Activate relevant UI parts after classifier training is complete.
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            trainButton.setEnabled(true);
                            tabbedPane1.setEnabledAt(1, true);
                            tabbedPane1.setEnabledAt(2, true);
                            trainLabel.setIcon(null);
                            trainLabel.setText("<html>Bayesian Network has been created!<br>" +
                                    "Please go on to next tab to test it.");
                        }
                    });
                }
            };
            trainThread.start();
            if (debug) System.out.println("K value: " + kValueField.getValue());
            if (debug) System.out.println("CHI value: " + chiValueField.getValue());
        });

        correctButton.addActionListener((ActionEvent e) -> {
            //TODO: Perform action based on correct classification.
            incorrectButton.setSelected(false);
            correctButton.setSelected(true);
            classComboBox.setEnabled(false);

        });

        incorrectButton.addActionListener((ActionEvent e) -> {
            //TODO: Perform action based on incorrect classification.
            correctButton.setSelected(false);
            incorrectButton.setSelected(true);
            classComboBox.setEnabled(true);
        });

        openUserFileButton.addActionListener((ActionEvent e) -> {
            //Handle open button action.
            int returnVal = fileFc.showOpenDialog(IntelligentLearnerGUI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileFc.getSelectedFile();
                userFileLabel.setText(file.getName());
                classifyButton.setEnabled(true);
                if(debug)System.out.println("Opening: " + file.getName() + ".");
            } else {
                if(debug)System.out.println("Open command cancelled by user.");
            }
        });

        //CLASSIFY SINGLE FILE BUTTON
        classifyButton.addActionListener((ActionEvent e) -> {
            HashMap<String, Integer> words = dc.scanDocument(fileFc.getSelectedFile().getAbsolutePath() + "\\");
            DataClass dataClass = cf.multinomialClassifier(words);
            if (debug) System.out.println("FILENAME: " + fileFc.getSelectedFile().getName());
            setSelectedClass(fileFc.getSelectedFile().getName(), dataClass.getClassName(), words);  //Sets the className and words for use in other scope

            classComboBox.removeAllItems();
            for (String className : DataClass.getClasses().keySet()){
                if (!className.equals(dataClass.getClassName())) classComboBox.addItem(className);
            }

            classifyLabel.setText("<html>File classified as " + dataClass.getClassName() +
                    "<br>Please give your feedback about the classification below!");

            //Enable result elements.
            for (Component c : judgePanel.getComponents()){ c.setEnabled(true); }
            if (!incorrectButton.isSelected()) classComboBox.setEnabled(false);
        });

        finishButton.addActionListener((ActionEvent e) -> {
            boolean update = updateCheckBox.isSelected();
            if(correctButton.isSelected()){
                //TODO: action when correct classification.
                if (update) {
                    DataClass dataClass = DataClass.getClass(selectedClassName);
                    dataClass.addDocument(selectedFileName, selectedWords);

                    //Re-train the specific classes
                    dataClass.clearVocabulary();
                    dataClass.extractVocabulary();
                    dataClass.filterWords(true);

                    System.out.printf("Updated class: %s with file %s.\n", dataClass.getClassName(), selectedFileName);
                }
            } else if (incorrectButton.isSelected()){
                //TODO: update classifier based on file.
                if (update) {
                    DataClass dataClass = DataClass.getClass((String) classComboBox.getSelectedItem());
                    dataClass.addDocument(selectedFileName, selectedWords);

                    //Re-train the specific classes
                    dataClass.clearVocabulary();
                    dataClass.extractVocabulary();
                    dataClass.filterWords(true);

                    System.out.printf("Updated class: %s with file %s.\n", dataClass.getClassName(), selectedFileName);
                }
            } else {
                //TODO: add action when no radio button is selected.
                System.err.println("[IntelligentLearnerGUI.java] Error occurred: no radioButton selected");
            }
            if (update) {
                System.out.println("[IntelligentLearnerGUI.java] Updated classifier with selected file.");
            }
        });

        // Test button in Automatic testing tab
        testClassifierButton.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ImageIcon loading = new ImageIcon("resources/images/ajax-loader.gif");
                    testClassifierButton.setEnabled(false);
                    testClassifierButton.setIcon(loading);
                    testClassifierButton.setText("Please wait...");
                }
            });
            //Start testing the classifier on a new thread
            Thread testThread = new Thread() {
                public void run() {
                    boolean updateClassifier = updateClassifierCheckBox.isSelected();

                    String testpath = testFc.getSelectedFile().getAbsolutePath() + "\\";
                    if (debug) System.out.println("TESTPATH: " + testpath);

                    HashMap<String, HashMap<String, Integer>> stats = dc.scanTestDocuments(testpath, updateClassifier, true);

                    List<String> sortedClasses = new ArrayList(stats.keySet());
                    Collections.sort(sortedClasses);

                    int[][] table = Utils.createTable(stats);
                    Utils.printTable(table, sortedClasses);
                    Utils.getStatistics(table, sortedClasses);

                    String log = Utils.getLog();
                    logTextArea.append("\n");
                    logTextArea.append(getCurrentTime() + "\n");
                    logTextArea.append(log);
                    Utils.resetLog();

//                    DataClass.printClassesInfo();

                    //Set button back to default text
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            testClassifierButton.setEnabled(true);
                            testClassifierButton.setIcon(null);
                            testClassifierButton.setText("Test Classifier");
                        }
                    });
                }
            };
            testThread.start();
        });
    }

    // Returns the current date and time
    private String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void setSelectedClass(String fileName, String className, HashMap<String, Integer> words) {
        this.selectedFileName = fileName;
        this.selectedClassName = className;
        this.selectedWords = words;
    }
}
