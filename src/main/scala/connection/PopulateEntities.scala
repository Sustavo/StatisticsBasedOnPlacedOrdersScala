package connection
import entities.{Item, Product, Order}

object PopulateEntities {
  private val productTable = ConnectJDBC.consultDB("SELECT * FROM product")
  private val itemTable = ConnectJDBC.consultDB("SELECT * FROM item")
  private val orderTable = ConnectJDBC.consultDB("SELECT \"order\".order_id, string_agg(order_item.item_id::text, ',') as item_ids, \"order\".client_name, \"order\".contact, \"order\".shipping_address, \"order\".grand_total, \"order\".request_date FROM order_item INNER JOIN \"order\" ON \"order\".order_id = order_item.order_id GROUP BY \"order\".order_id ORDER BY \"order\".order_id;")
  private val productTableColumns = List(
    ("product_id", "Long"),
    ("name", "String"),
    ("category", "String"),
    ("weight", "Double"),
    ("price", "BigDecimal"),
    ("creationDate", "LocalDateTime"),
  )
  private val itemTableColumns = List(
    ("item_id", "Long"),
    ("product_id", "Long"),
    ("shippingfee", "BigDecimal"),
    ("taxamount", "BigDecimal"),
    ("item_cost", "BigDecimal")
  )

  private val orderTableColumns = List(
    ("order_id", "Long"),
    ("item_ids", "String"),
    ("client_name", "String"),
    ("contact", "String"),
    ("shipping_address", "String"),
    ("grand_total", "BigDecimal"),
    ("request_date", "LocalDateTime")
  )

  val productPopulate = ConnectJDBC.transformToList[Product](productTable, productTableColumns)
  val itemsPopulate = ConnectJDBC.transformToList[Item](itemTable, itemTableColumns)
  val orderPopulate = ConnectJDBC.transformToList[Order](orderTable, orderTableColumns)


}
