package com.DataVectis.Clustering

import org.apache.spark.sql.SparkSession
import vegas._
import vegas.sparkExt._

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
    val dataset = spark.read.json(System.getProperty("user.dir").concat(inputData.getProp("inputData")))

    // Creating an instance from "Classify.scala" Class to get the model of clustering
    val mod = new Classify

    //Creating Model
    val modelToClussify = mod.getModel(dataset)

    // Adding Cluster Labels to our data
    val clusters = modelToClussify.transform(dataset)

    // Displaying Data Clussified
    clusters.drop("features").show()

    // Ploting Individuals according to Clusters laels
    val plot = new DataViz
    plot.View(clusters.toDF(),"Clusters")

  }
}
