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


    val hdfs = FileSystem.get(new Configuration())

    def listFileNames(hdfsPath: String): List[String] = {

      hdfs
        .listStatus(new Path(hdfsPath))
        .flatMap { status =>
          // If it's a file:
          if (status.isFile)
            List(hdfsPath + "/" + status.getPath.getName)
          // If it's a dir and we're in a recursive option:
          else
            listFileNames(hdfsPath + "/" + status.getPath.getName)
        }
        .toList
        .sorted
    }

    val files = listFileNames("C:\\Users\\Lenovo\\Downloads\\Compressed\\citybikes-clustering-master")

    for (f <- files) {
      val p = Pattern.compile("(.+?)Brisbane_CityBike.json") // regex to identify files names and extensions
      val m = p.matcher(f)

      if (m.find()) {

        println(f.replaceAll("\\\\","/"))
      }
    }

/*
    //Getting path from "application.properties" using the method "Prop" from "Properties.scala"
    val inputData = new prop

    // Importing data
    val dataset = spark.read.json(inputData.getProp("inputData"))

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
*/
  }
}
