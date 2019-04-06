package com.DataVectis.Clustering

import java.io.File
import org.scalatest.BeforeAndAfterEach
import org.apache.spark.sql.SparkSession
import org.scalatest.FunSuite

class TDD extends FunSuite with BeforeAndAfterEach {


  var spark : SparkSession=_

  override def beforeEach(): Unit = {

    val appProperties = new prop

    val spark = SparkSession.builder.
      master(appProperties.getProp("master"))
      .appName(appProperties.getProp("appName"))
      .getOrCreate()



  }

  // testing if the data is successfully loaded

  test("Check existing Data") {
    val appProperties = new prop
    assert(new File(appProperties.getProp("DataInput")).isFile)
  }

  // testing if the input number of clusters is more than 1

  test("Check Clusters number") {

    val appProperties = new prop
    assert(appProperties.getProp("inputClusterNumber").toInt > 1)
  }


  // testing if the clustering is successfully done

  test("Checking Clustering") {
    val appProperties = new prop

    val BrisbaneCityBike = spark.read.json(appProperties.getProp("inputData"))

    // Creating an instance from "Classify.scala" Class to get the model of clustering
    val mod = new Clustering

    //Creating Model
    val modelToCluster = mod.getModel(BrisbaneCityBike)

    // Adding Cluster Labels to our data
    val clusters = modelToCluster.transform(BrisbaneCityBike)

    assert(clusters.toDF().columns.size > BrisbaneCityBike.columns.size)

  }

  override def afterEach(): Unit = {
    spark.stop()
  }

}
