package basics
import org.apache.spark.SparkContext
/**
  * Created by Jeremy on 7/6/2017.
  */
object Test123{
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext()
    val bla = sc.parallelize("BLALBLA")
    val q = bla.collect
    println(q)
    sc.stop()
  }
}
