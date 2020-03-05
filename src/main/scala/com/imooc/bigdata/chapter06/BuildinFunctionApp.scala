package com.imooc.bigdata.chapter06

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

object BuildinFunctionApp {

  def main(args: Array[String]): Unit = {


    val spark: SparkSession = SparkSession.builder()
      .master("local").appName("HiveSourceApp")
      .getOrCreate()


    // 工作中，源码托管在gitlab，svn，cvs，你clone下来以后，千万不要手贱，做代码样式的格式化
    val userAccessLog = Array (
      "2016-10-01,1122",  // day  userid
      "2016-10-01,1122",
      "2016-10-01,1123",
      "2016-10-01,1124",
      "2016-10-01,1124",
      "2016-10-02,1122",
      "2016-10-02,1121",
      "2016-10-02,1123",
      "2016-10-02,1123"
    )

    import spark.implicits._

    // Array ==> RDD
    val userAccessRDD: RDD[String] = spark.sparkContext.parallelize(userAccessLog)

    val userAccessDF: DataFrame = userAccessRDD.map(x => {
      val splits: Array[String] = x.split(",")
      Log(splits(0), splits(1).toInt)
    }).toDF

    //userAccessDF.show()

    import org.apache.spark.sql.functions._

    // select day, count(user_id) from xxx group by day;
    // userAccessDF.groupBy("day").agg(count("userId").as("pv")).show()

    userAccessDF.groupBy("day").agg(countDistinct("userId").as("uv")).show()

    // TODO... 使用JDBC数据源把统计结果输出到MySQL表中


    spark.stop()
  }

  case class Log(day:String,userId:Int)
}
