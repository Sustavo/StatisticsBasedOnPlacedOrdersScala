package tables

import connection.Connection
import slick.jdbc.GetResult
import entity.Product
import slick.jdbc.PostgresProfile.api._

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}

object ProductTable{
  class ProductTable(tag: Tag) extends Table[Product](tag, "product") {
    def id = column[Long]("product_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def category = column[String]("category")
    def weight = column[Double]("weight")
    def price = column[BigDecimal]("price")
    def date = column[LocalDateTime]("creationdate")

    def * = (id, name, category, weight, price, date) <> ((Product.apply _).tupled, Product.unapply)
  }

  val productTable = TableQuery[ProductTable]

  def getProducts: List[Product] = {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    def auxGetProducts(): Future[Vector[Product]] = {
      implicit val getResultProduct: GetResult[Product] =
        GetResult(positionedResult => Product(
          positionedResult.<<,
          positionedResult.<<,
          positionedResult.<<,
          positionedResult.<<,
          positionedResult.<<,
          LocalDateTime.parse(positionedResult.nextString(), formatter)
        ))

      val query = sql"""select * from product """.as[Product]
      Connection.db.run(query)
    }

    Await.result(auxGetProducts().map(_.toList), 10.seconds)
  }

}
