SYSTEM REQUIREMENTS:
Tested on:
Operating system: Windows 10
Java RE version 1.8
CPU: Intell core i7 - 2.4 GHz
GPU: Intell onboard graphics
RAM: 4GB 

HOW TO INSTALL:
1. Make sure Java RE version 1.8 is installed on your computer.
2. Download any corpus you like to use for the classifier.
3. Setup the corpus in the following structure:
	%corpus folder%/test/*className*
	%corpus folder%/train/*className*
	Where for every class, *className* should be replaced by the desired class name.
	After this, put all .txt documents of this class in the folder.
	It's recommended to keep the test:class ratio of documents to 1:9.
4. Remember the location of the corpus files, you will need them for the program.

HOW TO RUN:
1. Open "Intelligent Learner.jar"

GUI INTERACTIONS:
First tab (Initialise Classifier):
1. Enter natural values for the k-value and a real number for the Chi-value or natural for the max vocabulary size
	a. The k-value determines the smoothing factor for multinomial classification.
	b. A higher Chi value will make the classifier more accurate but limits its vocabulary.
	   Only use this for large corpora.
	c. [EXPERIMENTAL] Max vocabulary size determines the maximum number of unique words for each class.
2. Press Choose Training Directory... to select the %corpus%/train folder created in HOW TO INSTALL step 3.
3. Press Train Classifier to start training the classifier based on the given corpus.
   (Depending on the size of the corpus this can take a few moments)	
   When the training is done, the other tabs will activate.

Second tab (Use the classifier)
1. Press Choose .txt file to classify... and choose any .txt file that you want to classify.
2. Press Classify the file! to classify it.
3. If the assigned class is correct, go to 5.
4. If the assigned class is incorrect, press the Incorrect classification button.
   In the dropdown menu on the right select the correct class of the file.
5. If you wish to update the classifier based on the supplied file, click the Update classifier checkbox.
6. Press finish to classify a new file.

Third tab (Testing the classifier)
For advanced users.
1. Press the Choose Testing Directory... button and select the %corpus%/test folder that was created in HOW TO INSTALL step 3.
2. If you wish to update the classifier based on the testing data, check the Update classifier iteratively box.
3. Press the Test Classifier button.
4. In the Log window below the detailed test results will be displayed.
   This includes accuracy, precision and recall etc.