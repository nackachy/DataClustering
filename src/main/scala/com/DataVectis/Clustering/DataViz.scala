package com.DataVectis.Clustering

import org.apache.spark.sql.DataFrame
import vegas._
import vegas.sparkExt._

class DataViz{

   def View(dataFrame: DataFrame,title: String) : ={

    val plot = Vegas(title, width = 600.0,height = 400.0).
    withDataFrame(dataFrame).
    mark(Point).
    encodeX("longitude", Quantitative,bin = Bin(0.00001)).
    encodeY("latitude", Quantitative ,bin = Bin(0.00001)).
    encodeColor(field="cluster", dataType=Nominal)

    plot.show

  }
}