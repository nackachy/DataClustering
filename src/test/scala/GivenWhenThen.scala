
import org.scalatest.BeforeAndAfterEach
import com.DataVectis.Clustering.Classify
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
    val dataset = spark.read.json("Brisbane_CityBike.json").toDF()

    assert(dataset.columns.size > 0)
  }


  // testing if the clustering is successfully done

  test("Checking Clustering") {

    val dataset = spark.read.json("Brisbane_CityBike.json").toDF()
    val mod = new Classify

    val modelToClussify = mod.getModel(dataset)

    val clusters = modelToClussify.transform(dataset)

    assert(clusters.toDF().columns.size > dataset.columns.size)

  }

  override def afterEach(): Unit = {
    spark.stop()
  }

}
