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

    val inputData = new prop

    // Importing data
    val dataset = spark.read.json(System.getProperty("user.dir").concat(inputData.getProp("inputData")))

    val mod = new Classify

    val modelToClussify = mod.getModel(dataset)

    val clusters = modelToClussify.transform(dataset)

    clusters.show()
  }
}
