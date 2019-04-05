# DataClustering 

## Description

The purpose of this project is to cluster Brisbane_CityBike based on longitude and latitude. 

## Requirements

* [Java 8](https://www.java.com/fr/download/faq/java8.xml)
* [Scala 2.11.7](https://www.scala-lang.org/download/2.11.7.html)
* [SBT 1.2.8](https://piccolo.link/sbt-1.2.8.zip)
* [Spark 2.1.0](https://spark.apache.org/releases/spark-release-2-1-0.html)

## Steps

* Initializing Spark Session
* Importing Data
* Building the Pipeline
  * Assembling Vectors
  * Training Model
* Applying model to data
* View clustering

## Configuration

Before executing the code, you have to change the data path existing in "src/main/ressources/application.properties" and the cluster number of your choice (by default 3)

    inputData=DataClustering/DataInput/Brisbane_CityBike.json
    inputClusterNumber=
    master=
    appName=
    nameOfColumnCluster=
    outPutData=
    fileOutPutName=

* **inputData** is the path where Brisbane_CityBike.json exists.
* **inputClusterNumber** is the number of clusters chosen to cluster the data.
* **master
* **appName** is the name of the app.
* **nameOfColumnCluster** name of cluser columns.
* **outPutData** is the path where clustered Data will be saved.
* **fileOutPutName** is the name chosen for outputData.

## Running project on YARN

To build the project, run : 

    sbt clean assembly
    
This will produce a jar containing the compiled project

Then you can submit the job using **spark-submit** :

    spark-submit --class com.DataVectis.Clustering.MainCluster --master yarn DataClustering/scripts/dataclustering_2.11-0.1.0-    SNAPSHOT.jar

## Results

In this project, we've chosen to cluster our data in 3 clusters. The plot provided will show how the work was done ! 

This is how the result would look like.

***Data with Clusters***

![Data Clustered](https://github.com/nackachy/DataClustering/blob/master/dataWithClusters.PNG)

***Data Plot***

This plot is developped with an R code ***DataViz.R***.

![Data Plot](https://github.com/nackachy/DataClustering/blob/master/Map.png)




