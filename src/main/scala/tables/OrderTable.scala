package tables

import entity.{Order, Product}
import connection.Connection
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._
import tables.ProductTable.ProductTable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object OrderTable {
  case class OrderItem(id: Long, order_id: Long, item_id: Long)
  case class OrderWithoutItems(id: Long, name: String, contact: String, shipping_address: String, grandTotal: BigDecimal, request_date:LocalDateTime)

  class OrderItemTable(tag: Tag) extends Table[OrderItem](tag, "order_item") {
    def id = column[Long]("order_item_id", O.PrimaryKey, O.AutoInc)
    def order_id = column[Long]("order_id")
    def item_id = column[Long]("item_id")

    def * = (id, order_id, item_id) <> ((OrderItem.apply _).tupled, OrderItem.unapply)
  }

  class OrderTable(tag: Tag) extends Table[OrderWithoutItems](tag, "order") {
    def id = column[Long]("\"order\".order_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("client_name")
    def contact = column[String]("contact")
    def shipping_address = column[String]("shipping_address")
    def grandTotal = column[BigDecimal]("grandTotal")
    def request_date = column[LocalDateTime]("request_date")

    def * = (id, name, contact, shipping_address, grandTotal, request_date) <> ((OrderWithoutItems.apply _).tupled, OrderWithoutItems.unapply)
  }

  def getOrders: List[Order] = {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    def auxGetOrderItems(): Future[Vector[OrderItem]] = {
      implicit val getResultOrderItem: GetResult[OrderItem] =
        GetResult(positionedResult => OrderItem(
          positionedResult.<<,
          positionedResult.<<,
          positionedResult.<<
        ))
      val query = sql"""select * from order_item""".as[OrderItem]

      Connection.db.run(query)
    }

    def auxGetOrders(): Future[Vector[OrderWithoutItems]] = {
      implicit val getResultOrder: GetResult[OrderWithoutItems] =
        GetResult(positionedResult => OrderWithoutItems(
          positionedResult.<<,
          positionedResult.<<,
          positionedResult.<<,
          positionedResult.<<,
          positionedResult.<<,
          LocalDateTime.parse(positionedResult.nextString(), formatter),
        ))
      val query =
        sql"""select * from "order" """.as[OrderWithoutItems]

      Connection.db.run(query)
    }

    val itemList = ItemTable.getItems
    val listOrderItems = Await.result(auxGetOrderItems().map(_.toList), 10.seconds)
    val listOrderWithoutItems = Await.result(auxGetOrders().map(_.toList), 10.seconds)

    val listOrders = listOrderWithoutItems.map{ order =>
      val id = order.id
      val items = listOrderItems.filter(orderItem => orderItem.order_id == id).flatMap{ orderItem =>
        val correspondingItem = itemList.find(item => item.getId == orderItem.item_id)
        correspondingItem
      }
      val name = order.name
      val contact = order.contact
      val shippingAddress = order.shipping_address
      val grandTotal = order.grandTotal
      val requestDate = order.request_date

      Order(id, items, name, contact, shippingAddress, grandTotal, requestDate)
    }

    listOrders
  }


}
