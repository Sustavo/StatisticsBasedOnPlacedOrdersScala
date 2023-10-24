package test

import slick.jdbc.PostgresProfile
import slick.lifted.{MappedProjection, ProvenShape}


import java.time.LocalDateTime

object SlickTablesGeneric{
  case class Product(id: Long, name: String, category: String, weight: Double, price: BigDecimal, date: LocalDateTime)
  import slick.jdbc.PostgresProfile.api._

  class productTable(tag: Tag) extends Table[Product](tag, Some("product"), "Product") {
    def id = column[Long]("product_id")
    def name = column[String]("name")
    def category = column[String]("category")
    def weight = column[Double]("weight")
    def price = column[BigDecimal]("price")
    def date = column[LocalDateTime]("creationdate")

    val test = (id, name, category, weight, price, date) <> ((Product.apply _).tupled, Product.unapply)
    override def * : ProvenShape[Product] = test
  }

  lazy val tableProduct = TableQuery[productTable]

}