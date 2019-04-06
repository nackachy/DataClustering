#!/bin/sh

spark-submit --class com.DataVectis.Clustering.MainCluster --master yarn-cluster --files /home/chiheb/DataClustering/config/application.properties /home/chiheb/DataClustering/scripts/jars/dataclustering_2.11-0.1.0-SNAPSHOT.jar
