package com.DataVectis.Clustering

import java.io.{ByteArrayOutputStream, File, PrintWriter}

import org.apache.spark.sql.{SaveMode, SparkSession}
import java.util.regex.Pattern

import scala.Console.withOut
import scala.util.control.Breaks.{break, breakable}

object MainCluster {

  def main(args: Array[String]) {

    // Initializing SparkContext

    val master, appName, inputData, outPutData, fileOutPutName = new prop

    val spark = SparkSession.builder.
      master(master.getProp("master"))
      .appName(appName.getProp("appName"))
      .getOrCreate()


    // Importing data
    //Get the appropriate path
    val BrisbaneCityBike = spark.read.json(inputData.getProp("inputData"))

    // Creating an instance from "Classify.scala" Class to get the model of clustering
    val mod = new Clustering

    //Creating Model
    val modelToCluster = mod.getModel(BrisbaneCityBike)

    // Adding Cluster Labels to our data
    val clusters = modelToCluster.transform(BrisbaneCityBike)

    //Show Data
    clusters.show()
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


  }
}



