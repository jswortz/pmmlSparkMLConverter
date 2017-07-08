package basics
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorAssembler, VectorIndexer}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.util.MLUtils.loadLibSVMFile
import org.apache.spark.sql.SQLContext
import java.io._
/**
  * Created by Jeremy on 7/6/2017.
  */
object CreateModel {
  case class ExampleOfStuff(features: Double, label: Int)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("BuildModel").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._
    // Load training data
    val data = Seq(ExampleOfStuff(1.1,4), ExampleOfStuff(2.2,5), ExampleOfStuff(4.4,6), ExampleOfStuff(1.1,7), ExampleOfStuff(2,55)).toDF()
    val va = new VectorAssembler().setInputCols(Array("features")).setOutputCol("f2")
    val vi = new VectorIndexer().setInputCol("f2").setOutputCol("indexedFeatures")
    val dt = new DecisionTreeClassifier().setLabelCol("label").setFeaturesCol("indexedFeatures")

    val pipeline = new Pipeline().setStages(Array(va, vi, dt))
    val model = pipeline.fit(data)

    model.write.overwrite().save("exampleModel")
    val schema = data.schema
    val oos = new ObjectOutputStream(new FileOutputStream("schema.txt"))
    oos.writeObject(schema)
    oos.close
    sc.stop() // being tidy, sean
  }
}
