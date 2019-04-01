package com.DataVectis.Clustering

import org.apache.spark.sql.SparkSession
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
    val inputData = new prop

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

    // Ploting Individuals according to Clusters laels
    val plot = new DataViz
    plot.View(clusters.toDF(),"Clusters")

  }
}
