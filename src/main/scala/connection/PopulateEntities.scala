package connection
import entity.Order

import java.sql.Timestamp
import java.time.LocalDateTime

object PopulateEntities {
  private val orderTableQuery =
    """
      |SELECT "order".order_id, string_agg(order_item.item_id::text, ',') as item_ids,
      |"order".client_name, "order".contact, "order".shipping_address, "order".grand_total,
      |"order".request_date
      |FROM order_item
      |INNER JOIN "order" ON "order".order_id = order_item.order_id
      |GROUP BY "order".order_id
      |ORDER BY "order".order_id;
    """.stripMargin
  private def createCreationDateProductsQuery(itemIds: String): String =
    s"""
       |SELECT product.creationdate
       |FROM item
       |INNER JOIN product ON item.product_id = product.product_id
       |WHERE item_id IN ($itemIds);
    """.stripMargin
  private val orderTableColumns = List("Long", "String", "String", "String", "String", "BigDecimal", "LocalDateTime")
  private val orderTable = ConnectJDBC.consultDB(orderTableQuery)
  private val orderPopulate = ConnectJDBC.transformInEntities[Order](orderTable, orderTableColumns)

  private val datesProducts = orderPopulate.map { order =>
    val itemIds = order.getItem
    val dateProductsQuery = createCreationDateProductsQuery(itemIds)
    val dateList = ConnectJDBC.consultDB(dateProductsQuery)
    dateList.flatten.map(date => Timestamp.valueOf(date).toLocalDateTime)
  }

  lazy val orderAndProducts: List[(Order, List[LocalDateTime])] = orderPopulate.zip(datesProducts)

}
