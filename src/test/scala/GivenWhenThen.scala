
import org.scalatest.BeforeAndAfterEach
import com.DataVectis.Clustering.Clustering
import com.DataVectis.Clustering.prop
import org.apache.spark.sql.SparkSession
import org.scalatest.FunSuite





class GivenWhenThen extends FunSuite with BeforeAndAfterEach {


  var spark : SparkSession=_

  override def beforeEach(): Unit = {

   spark = SparkSession.builder.
      master("local")
      .appName("DataClustering")
      .getOrCreate()


  }

  // testing if the data is successfully loaded

  test("Check Data Loading") {

    val inputData = new prop

    val dataset = spark.read.json(inputData.getProp("inputData")).toDF()

    assert(dataset.columns.size > 0)
  }

  // testing if the input number of clusters is more than 1

  test("Check Clusters number") {

    val inputClusterNumber = new prop
    assert(inputClusterNumber.getProp("inputClusterNumber").toInt > 1)
  }


  // testing if the clustering is successfully done

  test("Checking Clustering") {

    val inputData = new prop

    val dataset = spark.read.json(inputData.getProp("inputData")).toDF()

    val mod = new Clustering

    val modelToClustering = mod.getModel(dataset)

    val clusters = modelToClustering.transform(dataset)

    assert(clusters.toDF().columns.size > dataset.columns.size)

  }

  override def afterEach(): Unit = {
    spark.stop()
  }

}
