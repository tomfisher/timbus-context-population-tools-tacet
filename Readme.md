##	Time Series Annotation and Context Extraction Tool (TACET)

In the context of the Civil Engineering use-case and as part of the TIMBUS Extractors, we created the time-series annotation and context extraction tool (TACET). Its main purpose is to import time series monitoring and sensor data and annotate it with different labels, e.g. activities, status information or expert knowledge. This data can, then, be used as input for classifiers to predict future values or detect anomalies.
Each quantity will be displayed as an own sensor track. Measurements and annotations can be processed in parallel, i.e. different measurements can be labelled in combination. Examples of this are sensor values gathered by an extensometer. This facilitates an interpretation and extraction of context parameters.

The tool offers different features and capabilities:
- Import comma-separated values with header-support
- Ignore columns in CSV files
- Visualize data
- Annotate data on several tracks
- Statistics overview of data and annotations
- Save and load project
- Export to CSV file
- Undock window items to a separate window
- Play data
- Classify data

We tested the tool under Windows 7 and Windows Server and applied it to an Oracle 11g database instance. Both worked well and produced the expected outcome.

It is planned to extend this tool to enable data import directly from a SPARQL endpoint or from an OWL file. 


## Prerequisites

1. JavaVM


## Running TACET

One way to use the tool is to take the source code and start the tool directly via its *.jar or via an IDE (e.g. Eclipse) by running the MasterController.java. 


### Import - Best Practice

CSV-File
- Your csv-file should only contain a header and your values you want to display. Alternatively, you can import a file without header and add header information manually.
- The sperator should be ';' or ','. (Other separators are not well tested or will only be supported in a future version)
- For better performance, timestamps should have a regular frequency.
- Timestamps less than 1971 are not yet supported. (They might be in a future version)
- All floating points must be in local format (e.g. France has ',' and German '.').

Import
- Specify the location of the CSV file. If the file contains a header, check the box "Header" and define the element separator for the header.
- This will automatically create the names of the columns. If no header was included, you have to set the column names manually.
- Set the element separator for the columns and the line separator. Each column has to be specified. At least one column needs to be a timestamp (we recommend to use the first column).
- Define the timestamp format. The importer is guessing a timestamp, but make sure if it is correct. The time stamp can either be a Java SimpleDataformat (http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html) or an incrementing counter. If you are using custom timestamps you have to provide the format and seperator (e.g. m.s.S, separator: '\.'). 
- You have the possibility to add further rows, which are meant to be new annotation tracks. Allowed values should be stated.
- If you intend on performing a classification, you have to add a "NEW_TRAIN_ANNOTATION" track. There should also be an existing class track. If there is none, you have to add it during the import (e.g. "NEW_LABEL_ANNOTATION").


### Annotation

Once you have imported some data and created annotation tracks, you are ready to annotate. Mark a range on the annotation track by clicking and dragging while holding shift and release the mouse to annotate. Select the annotation track and the annotation value you defined before and click on annotate to add the annotation. You can also select the range on the track while the data is playing.


### Classification

To classify, you need to add annotations to the train track, specifying the data that shall be used for training (see Figure X). The rest of the data will be interpreted as "to be classified".
To run a classifier, you choose "classify" in the menu bar. Now, you have to specify the class track and the classifier that you want to use. So far, all WEKA classifiers are offered that support nominal class values. You may also specify the tracks that shall be considered for the classification. After the preparations are finished, you click "classify" to start the classification. Afterwards, the new, classified labels are shown in the class track .


### Statistics
The statistics view provides an overview of the current project. It shows some statistics of sensor data like average, median and deviation.
It also shows the number of annotations and percentage of annotated data on a track. You can also get information of the timestamps used in the program.

### Additional functions 
To export the project to a CSV file, set the location, element separator and line separator you wish.
To save the project, set the location to save the current project.
To load a project, specify the location to load an existing project. 



##	Usage Scenario
The Relational Database Extractor is a tool that is required in the Civil Engineering use case, especially the sensors scenario. All sensor measurements and additional meta-information are stored in a relational Oracle database. This data represents the sensors and their relations and dependencies which shall be captured and preserved by the sensors ontology. The necessary step is to populate the data from the storage to the ontology by mapping the data and relations to the concepts and properties in the ontology, i.e. extract the data and populate it into the sensors domain-specific ontology in due consideration of the presented concepts.
We tested the tool under Windows 7 and Windows Server and applied it to an Oracle 11g database instance. Both worked well and produced the expected outcome.


## Expected Output

As the project's main purpose is annotation, the expected outcome is annotated data. The original data shall not be touched. So far, the annotations can be exported into a new data file containing the additional information. 

It is planned to enhance the export in a manner that allows for updating the RDF graph underlying at a SPARQL endpoint that was already specified and used in the import process.


## Connection to the TIMBUS DIO

The annotatios made with this tool are intended to be added to instances in the sensors domain-specific ontology (sensorDSO).

If the above mentioned intended enhancements are implemented (import and export of ontology data), there will be a direct matching of annotations and instances in the sensorDSO. Those are connected to concepts in the TIMBUS domain-intependent ontology.