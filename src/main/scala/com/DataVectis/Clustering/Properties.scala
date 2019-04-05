package com.DataVectis.Clustering

import java.io.FileInputStream
import java.util.Properties

class prop {


  def getProp (propertyName: String) : String  = {

    val properties = new Properties()
    properties.load(new FileInputStream("DataClustering/config/application.properties"))

    val property = properties.getProperty(propertyName)
    property.toString()

  }
}