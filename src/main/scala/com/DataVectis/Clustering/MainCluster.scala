package com.DataVectis.Clustering

import java.io.File

import org.apache.spark.sql.{SaveMode, SparkSession}
import java.util.regex.Pattern

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

object MainCluster {

  def main(args: Array[String]) {

    // Initializing SparkContext
    val spark = SparkSession.builder.
      master("local")
      .appName("DataClustering")
      .getOrCreate()



    //Getting path from "application.properties" using the method "Prop" from "Properties.scala"
    val inputData,outPutData = new prop

    // Importing data
    val dataset = spark.read.json(inputData.getProp("inputData"))

    // Creating an instance from "Classify.scala" Class to get the model of clustering
    val mod = new Clustering

    //Creating Model
    val modelToClustering = mod.getModel(dataset)

    // Adding Cluster Labels to our data
    val clusters = modelToClustering.transform(dataset)

    // Displaying Data Clussified
    clusters.drop("features").show()

    //Checking if output exists
    val checkOutputDataPath = new File(outPutData.getProp("outPutData")).mkdir()

    if (checkOutputDataPath) {
      // Saving the dataset with labels

      clusters
        .repartition(1)
        .write
        .mode(SaveMode.Overwrite)
        .format("com.databricks.spark.csv")
        .option("header", "true")
        .option("delimiter", ";")
        .save(outPutData.getProp("outPutData"))

      println("Data saved in : "+outPutData.getProp("outPutData"))
    } else{

      println("OutPut Data Path Not Found")
      println("Creatin Data Path Out Put")
      new File(outPutData.getProp("outPutData"))

      clusters
        .repartition(1)
        .write
        .mode(SaveMode.Overwrite)
        .format("com.databricks.spark.csv")
        .option("header", "true")
        .option("delimiter", ";")
        .save(outPutData.getProp("outPutData"))

      println("Data saved in : "+outPutData.getProp("outPutData"))
    }

    // Ploting Individuals according to Clusters laels
    val plot = new DataViz
    plot.View(clusters.toDF(),"Clusters")

  }
}
