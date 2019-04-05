package com.DataVectis.Clustering

import org.apache.spark.sql.DataFrame
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.Pipeline

class Clustering{

  def getModel(dataFrame: DataFrame) = {

    // Assembling the Vectors used in clustering (latitude and longitude)
    val assembler = new VectorAssembler()
      .setInputCols(Array("latitude","longitude"))
      .setOutputCol("features")


    // Initializing the KMeans clustering model (3 clusters to have)
    val inputClusterNumber,nameOfColumnCluster = new prop

    val kmeans = new KMeans()
      .setK(inputClusterNumber.getProp("inputClusterNumber").toInt)
      .setSeed(1L)
      .setFeaturesCol("features")
      .setPredictionCol(nameOfColumnCluster.getProp("nameOfColumnCluster"))

    // Building the Pipeline for clustering
    val pipeline = new Pipeline().setStages(Array(assembler, kmeans))

    // Running the clustering pipeline
    val model = pipeline.fit(dataFrame)
    model

  }

}