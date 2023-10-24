package connection

import entity.{Item, Order, Product}

import java.sql.*
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global

object ConnectJDBC {
//  private val url = "jdbc:postgresql://localhost:5432/Orders"
//  private val user = "postgres"
//  private val password = "postgres"
//  private var connection: Connection = null
//
//  private def consultDB(value: String): List[List[String]] = {
//    var resultList = List.empty[List[String]]
//    try {
//      connection = DriverManager.getConnection(url, user, password)
//      val result: ResultSet = connection.createStatement().executeQuery(value)
//      val metaData = result.getMetaData
//      val columnCount = metaData.getColumnCount
//      while (result.next()) {
//        var createStringTuples = List.empty[String]
//        for (i <- 1 to columnCount) {
//          val columnValue = result.getString(i)
//          createStringTuples = createStringTuples :+ columnValue
//        }
//        resultList = resultList :+ createStringTuples
//      }
//      resultList
//    } catch {
//      case e: SQLException =>
//        e.printStackTrace()
//        throw new RuntimeException("Error connecting to the database.")
//    } finally {
//      if (connection != null) {
//        connection.close()
//      }
//    }
//  }
//
//  def getOrders: List[Order] = {
//    val queryValue = "SELECT * FROM \"order\""
//    val ListOrders = consultDB(queryValue).map { listValues => {
//      val id = listValues.head.toLong
//      val items = getItemsForOrder(id)
//      val name = listValues(1)
//      val contact = listValues(2)
//      val shippingAddress = listValues(3)
//      val grandTotal = BigDecimal(listValues(4))
//      val requestDate = Timestamp.valueOf(listValues(5)).toLocalDateTime
//      Order(id, items, name, contact, shippingAddress, grandTotal, requestDate)
//    }}
//
//    ListOrders
//  }
//
//   private def getItemsForOrder(orderId: Long): List[Item] = {
//     val queryValue =
//     s"""
//       |SELECT item.item_id, product_id, shippingfee, taxamount, item_cost
//       |FROM order_item
//       |INNER JOIN item on order_item.item_id = item.item_id WHERE order_id = $orderId;
//     """.stripMargin
//
//    val listItems: List[Item] = consultDB(queryValue).map { listValues =>
//      val id = listValues.head.toLong
//      val product = getProductForItem(listValues(1).toLong)
//      val shippingFee = BigDecimal(listValues(2))
//      val taxAmount = BigDecimal(listValues(3))
//      val cost = BigDecimal(listValues(4))
//      Item(id, product, shippingFee, taxAmount, cost)
//    }
//
//     listItems
//  }
//
//  private def getProductForItem(itemId: Long): Product = {
//    val queryValue =
//      s"""
//       |SELECT product.product_id, product.name, product.category, product.weight, product.price, product.creationdate
//       |FROM item
//       |INNER JOIN product ON item.product_id = product.product_id
//       |WHERE item.product_id = $itemId;
//      """.stripMargin
//
//    val product = consultDB(queryValue).map { listValues =>
//      val id = listValues.head.toLong
//      val name = listValues(1)
//      val category = listValues(2)
//      val weight = listValues(3).toDouble
//      val price = BigDecimal(listValues(4))
//      val creationDate = Timestamp.valueOf(listValues(5)).toLocalDateTime
//      Product(id, name, category, weight, price, creationDate)
//    }
//
//    product.head
//  }
  val db: Database = Database.forConfig("postgres")

}
