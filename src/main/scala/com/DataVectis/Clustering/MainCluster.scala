package com.DataVectis.Clustering

import java.io.{ByteArrayOutputStream, File, PrintWriter}

import org.apache.spark.sql.{SparkSession}

import scala.Console.withOut
import java.util.logging.{Level, Logger}

object MainCluster {

  def main(args: Array[String]) {

    val logger = Logger.getLogger(getClass.getName)

    // Initializing SparkContext

    val master, appName, inputData, outPutData, fileOutPutName = new prop

    val spark = SparkSession.builder.
      master(master.getProp("master"))
      .appName(appName.getProp("appName"))
      .getOrCreate()

    logger.log(Level.INFO, "Spark context has been initialized", spark.sparkContext.appName)

    // Importing data
    //Get the appropriate path
    val BrisbaneCityBike = spark.read.json(inputData.getProp("inputData"))

    logger.log(Level.INFO, "Dataset has been imported", BrisbaneCityBike.count())


    // Creating an instance from "Classify.scala" Class to get the model of clustering
    val mod = new Clustering

    //Creating Model
    val modelToCluster = mod.getModel(BrisbaneCityBike)

    logger.log(Level.INFO, "Model Created")

    // Adding Cluster Labels to our data
    val clusters = modelToCluster.transform(BrisbaneCityBike)

    //Show Data
    clusters.drop("features").show()
    //saving Data

    val outCapture = new ByteArrayOutputStream
    withOut(outCapture) {
      clusters.rdd.map(_.mkString(",")).collect.foreach(println)
    }
    val result = new String(outCapture.toByteArray)

    val pw = new PrintWriter(
      new File(outPutData.getProp("outPutData") + "/" + fileOutPutName.getProp("fileOutPutName"))
    )
    pw.write("")
    pw.close
    new PrintWriter(outPutData.getProp("outPutData") + "/" + fileOutPutName.getProp("fileOutPutName")) {
      write(result); close
    }

    logger.log(Level.INFO, "Clustered data has been saved in ", outPutData.getProp("outPutData"))

    spark.close()
  }
}



