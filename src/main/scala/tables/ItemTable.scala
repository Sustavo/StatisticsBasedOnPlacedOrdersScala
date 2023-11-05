package tables

import connection.Connection
import slick.jdbc.GetResult
import entity.{Item}
import slick.jdbc.PostgresProfile.api._
import tables.ProductTable.ProductTable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

object ItemTable {
  case class ItemWithoutProduct(id: Long, productId: Long, shippingFee: BigDecimal, taxAmount: BigDecimal, cost: BigDecimal)

  class ItemTable(tag: Tag) extends Table[ItemWithoutProduct](tag, "item") {
    def id = column[Long]("item_id", O.PrimaryKey, O.AutoInc)
    def product_id = column[Long]("product_id")
    def shippingFee = column[BigDecimal]("shipping_fee")
    def taxAmount = column[BigDecimal]("tax_amount")
    def cost = column[BigDecimal]("cost")

    def * = (id, product_id, shippingFee, taxAmount, cost) <> ((ItemWithoutProduct.apply _).tupled, ItemWithoutProduct.unapply)

  }

  def getItems: List[Item] = {
    def auxGetItems(): Future[Vector[ItemWithoutProduct]] = {
      implicit val getResultItem: GetResult[ItemWithoutProduct] =
        GetResult(positionedResult => ItemWithoutProduct(
          positionedResult.<<,
          positionedResult.<<,
          positionedResult.<<,
          positionedResult.<<,
          positionedResult.<<,
        ))

      val query = sql"""select * FROM item""".as[ItemWithoutProduct]
      Connection.db.run(query)
    }

    val products = ProductTable.getProducts
    val ListItems = Await.result(auxGetItems().map(_.toList), 10.seconds).map {item =>
      val newProduct = products.filter(product => product.getId == item.productId).head
      Item(item.id, newProduct, item.shippingFee, item.taxAmount, item.cost)
    }

    ListItems
  }



}
