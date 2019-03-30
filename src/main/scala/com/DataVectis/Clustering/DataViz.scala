package com.DataVectis.Clustering

import org.apache.spark.sql.DataFrame
import vegas._
import vegas.sparkExt._

class DataViz{

   def View(dataFrame: DataFrame,title: String) : String ={

    val plot = Vegas(title, width = 600.0,height = 400.0).
    withDataFrame(dataFrame).
    mark(Point).
    encodeX("longitude", Quantitative ,bin = Bin(step = 0.00001) ,enableBin=false).
    encodeY("latitude", Quantitative ,bin = Bin(step = 0.00001) ,enableBin=false).
      encodeColor(
      field="cluster",
      dataType=Nominal,
      legend=Legend(orient="left", title="Clusters"))

     plot.show

     return  plot.html.plotHTML()
  }
}