package com.DataVectis.Clustering

import java.io.{ByteArrayOutputStream, File, PrintWriter}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import java.io.PrintWriter

import scala.Console.withOut
import java.util.logging.{Level, Logger}

object MainCluster {

  def main(args: Array[String]) {

    val logger = Logger.getLogger(getClass.getName)

    // Initializing SparkContext

    val appProperties = new prop

    val spark = SparkSession.builder.
      master(appProperties.getProp("master"))
      .appName(appProperties.getProp("appName"))
      .getOrCreate()

    logger.log(Level.INFO, "Spark context has been initialized", spark.sparkContext.appName)

    // Importing data
    //Get the appropriate path
    val BrisbaneCityBike = spark.read.json(appProperties.getProp("inputData"))

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
    //getting the result
    val result = new String(outCapture.toByteArray)

    //getting the file creation's time
    val getDate = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm").format(LocalDateTime.now)

    //configuration
    val conf = new Configuration()
    val fs= FileSystem.get(conf)
    val os = fs.create(new Path(appProperties.getProp("DataOutput")
              +"/"+
              appProperties.getProp("fileOutPutName")
              +getDate+
              ".txt")
    )

    //writing the file
    os.write(result.getBytes)

    logger.log(Level.INFO, "Clustered data has been saved in ", appProperties.getProp("outputData"))

    spark.close()
  }
}



