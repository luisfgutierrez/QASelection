# QARank - Answer Selection and Ranking Tool for Community Question Answering sites
QARank is licensed under ASL 2.0 and other lenient licenses, allowing its use for academic and commercial purposes without restrictions.

## Downloading QARank
* Download the jar file of the project from here.
* Alternatively, download the [zip](https://github.com/tudarmstadt-lt/QASelection/archive/master.zip) of the java project and import it as a **maven** project in eclipse for experimentation.

## Downloading Data
* QARank requires a training file, a test file and unannotated data to train models.
* The system was trained and tested on **SemEval 2016 - Task 3: Community Question Answering - Subtask A** data.
* Create a directory named **xml_files** in your local machine.
* The training+dev data can be downloaded from [here](http://alt.qcri.org/semeval2016/task3/data/uploads/semeval2016-task3-cqa-ql-traindev-v3.2.zip).
* The original test data can be downloaded from
[here](http://alt.qcri.org/semeval2016/task3/data/uploads/semeval2016_task3_tests.zip).
* After unzipping this folder, move to `semeval2016-task3-cqa-ql-traindev-v3.2/v3.2/train/`. The entire training data for Task 3 can be found here. 
* Choose any of the *subtask A* train files for training and copy it to *xml_files* directory. Rename the training file **train.xml**.
* Alternatively, combine various training xml files into one file *train.xml* for larger training data. Make sure to preserve the XML tree structure while doing this.
* Similarly, choose one of the *subtask A* files in `semeval2016-task3-cqa-ql-traindev-v3.2/v3.2/dev/` or `semeval2016_task3_tests/SemEval2016_task3_test/English/` as test data and rename it **test.xml**.
* The unannotated data can be downloaded from [here](http://alt.qcri.org/semeval2016/task3/data/uploads/QL-unannotated-data-subtaskA.xml.zip).
* After unzipping the this folder, move to `QL-unannotated-data-subtaskA.xml/QL-unannotated-data-subtaskA.xml` and copy this file to *xml_files* directory and rename it **unannotated.xml**.
* This file is large and requires a lot of memory to train models. To avoid larger training time, one can use training data for the same task. Make sure to rename the file to **unannotated.xml**.

## Running QARank
* Run QARank jar as
```
java -Xmx10g -jar QARank.jar [path-to-xml_files-folder]
```
* The system will generate all folders and required files.
* The final MAP scores of the system and the SVM accuracy can be found in **result_files/final_scores.txt** file.
* Users can run the system on a different dataset, given the training and test files are in the format as in SemEval 2016 - Task 3.  
* The evaluation scripts used in the system can be looked up [here](http://alt.qcri.org/semeval2016/task3/data/uploads/semeval2016_task3_submissions_and_score.zip).
