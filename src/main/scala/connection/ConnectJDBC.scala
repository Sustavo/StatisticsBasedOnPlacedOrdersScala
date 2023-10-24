package connection

import entity.{Item, Order, Product}

import java.sql.*

object ConnectJDBC {
  case class OrderItem(id: Long, order_id: Long, item_id: Long)

  private val url = "jdbc:postgresql://localhost:5432/Orders"
  private val user = "postgres"
  private val password = "postgres"
  private var connection: Connection = null

  private def consultDB(value: String): List[List[String]] = {
    var resultList = List.empty[List[String]]
    try {
      connection = DriverManager.getConnection(url, user, password)
      val result: ResultSet = connection.createStatement().executeQuery(value)
      val metaData = result.getMetaData
      val columnCount = metaData.getColumnCount
      while (result.next()) {
        var createStringTuples = List.empty[String]
        for (i <- 1 to columnCount) {
          val columnValue = result.getString(i)
          createStringTuples = createStringTuples :+ columnValue
        }
        resultList = resultList :+ createStringTuples
      }
      resultList
    } catch {
      case e: SQLException =>
        e.printStackTrace()
        throw new RuntimeException("Error connecting to the database.")
    } finally {
      if (connection != null) {
        connection.close()
      }
    }
  }

  def getOrderItems: List[OrderItem] = {
    val queryValue = "SELECT * FROM order_item"
    val listOrderItems = consultDB(queryValue).map { listValues => {
      val id = listValues.head.toLong
      val orderId = listValues(1).toLong
      val itemId = listValues(2).toLong
      OrderItem(id, orderId, itemId)
    }}

    listOrderItems
  }

  def getProducts: List[Product] = {
    val queryValue = "SELECT * FROM product"
    val listProducts = consultDB(queryValue).map { listValues => {
      val id = listValues.head.toLong
      val name = listValues(1)
      val category = listValues(2)
      val weight = listValues(3).toDouble
      val price = BigDecimal(listValues(4))
      val creationDate = Timestamp.valueOf(listValues(5)).toLocalDateTime
      Product(id, name, category, weight, price, creationDate)
    }}

    listProducts

  }

  def getItems: List[Item] = {
    val queryValue = "SELECT * FROM item"
    val products = getProducts
    val listItems = consultDB(queryValue).map { listValues => {
      val id = listValues.head.toLong
      val product = products.filter(product => product.getId == id).head
      val shippingFee = BigDecimal(listValues(2))
      val taxAmount = BigDecimal(listValues(3))
      val cost = BigDecimal(listValues(4))
      Item(id, product, shippingFee, taxAmount, cost)
    }}

    listItems
  }

  def getOrders: List[Order] = {
    val queryValue = "SELECT * FROM \"order\""
    val itemsList = ConnectJDBC.getItems
    val listOrders = consultDB(queryValue).map { listValues => {
      val id = listValues.head.toLong
      val items = getOrderItems.filter(orderItem => orderItem.order_id == id).flatMap { orderItem => {
        val correspondingItem = itemsList.find(item => item.getId == orderItem.item_id)
        correspondingItem
      }}
      val name = listValues(1)
      val contact = listValues(2)
      val shippingAddress = listValues(3)
      val grandTotal = BigDecimal(listValues(4))
      val requestDate = Timestamp.valueOf(listValues(5)).toLocalDateTime
      Order(id, items, name, contact, shippingAddress, grandTotal, requestDate)
    }}

    listOrders
  }

}
