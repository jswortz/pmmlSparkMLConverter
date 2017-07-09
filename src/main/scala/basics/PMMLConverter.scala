package basics
import org.jpmml.sparkml.ConverterUtil
import org.apache.spark.ml.PipelineModel
import org.apache.spark.{SparkConf, SparkContext}
import java.io._
import org.apache.spark.sql.types.StructType
import org.jpmml.model.JAXBUtil
import javax.xml.transform.stream.StreamResult
/**
  * Created by Jeremy on 7/6/2017.
  */
object PMMLConverter {
  def main(args: Array[String]): Unit = {
    //args1 = schema args2 = model file, args3 = output
    val conf = new SparkConf().setMaster("local").setAppName("PMMLConverter")
    val sc = new SparkContext(conf)
    val model = PipelineModel.load(args(1))
    val ois = new ObjectInputStream(new FileInputStream(args(0)))
    val schma = ois.readObject.asInstanceOf[StructType]
    ois.close
    val pmml = ConverterUtil.toPMML(schma, model)
    JAXBUtil.marshalPMML(pmml, new StreamResult(args(2)))
    sc.stop()
  }
}
