package com.DataVectis.Clustering

import java.io.File
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.feature.VectorAssembler
import org.scalatest.BeforeAndAfterEach
import org.apache.spark.sql.SparkSession
import org.scalatest.FunSuite

class TDD extends FunSuite with BeforeAndAfterEach {


  var spark : SparkSession=_

  override def beforeEach(): Unit = {

    spark = SparkSession.builder.
      master("local")
      .appName("DataClustering")
      .getOrCreate()


  }

  // testing if the data is successfully loaded

  test("Check existing Data") {
    assert(new File("DataInput/Brisbane_CityBike.json").isFile)
  }



  // testing if the clustering is successfully done

  test("Checking Clustering") {

    val BrisbaneCityBike = spark.read.json("DataInput/Brisbane_CityBike.json")

    val assembler = new VectorAssembler()
      .setInputCols(Array("latitude","longitude"))
      .setOutputCol("features")
    // Creating an instance from "Classify.scala" Class to get the model of clustering
    val kmeans = new KMeans()
      .setK(3)
      .setSeed(1L)
      .setFeaturesCol("features")
      .setPredictionCol("clusters")

    // Building the Pipeline for clustering
    val pipeline = new Pipeline().setStages(Array(assembler, kmeans))

    // Running the clustering pipeline
    val model = pipeline.fit(BrisbaneCityBike.toDF())

    // Adding Cluster Labels to our data
    val clusters = model.transform(BrisbaneCityBike)

    assert(clusters.toDF().columns.size > BrisbaneCityBike.columns.size)

  }

  override def afterEach(): Unit = {
    spark.stop()
  }

}
