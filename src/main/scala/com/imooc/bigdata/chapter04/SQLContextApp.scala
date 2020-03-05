package com.imooc.bigdata.chapter04

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SQLContext}

object SQLContextApp {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setAppName("SQLContextApp").setMaster("local")
    val sc: SparkContext = new SparkContext(sparkConf) // 此处一定要把SparkConf传进来
    val sqlContext: SQLContext = new SQLContext(sc)

    val df: DataFrame = sqlContext.read.text("file:///Users/rocky/IdeaProjects/imooc-workspace/sparksql-train/data/input.txt")
    df.show()

    sc.stop()
  }

}
