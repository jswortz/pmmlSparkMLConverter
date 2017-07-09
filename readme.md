# JPMML Converter Function

Source code credit: https://github.com/jpmml/jpmml-sparkml

The idea behind this code is to with a:

1. Schema
2. Saved model (in a local location)
3. Output specification

Create a PMML representation of a SparkML pipeline object

There is an example with a pre-built model and schema saved to disk.

* schema.txt is the serialized schema
* exampleModel is the folder with the saved Spark model from the libsvm data

Example usage:

	spark-submit --class basics.PMMLConverter target\jpmml.converter-1.0-SNAPSHOT.jar schema.txt exampleModel outputPMML.xml

# Build Instructions:

	mvn install
