import entity.{Item, Order, Product}
import org.scalatest.funsuite.AnyFunSuite
import validator.*

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PlaceOrdersSuite extends AnyFunSuite{
  val currentDateTime: LocalDateTime = LocalDateTime.now()
  val product1: Product = Product(1, "Soap", "Personal Care", 100, BigDecimal("2.50"), currentDateTime)
  val item1: Item = Item(1, product1, 6.0, 8.0, 1.0)
  val product2: Product = Product(2, "Pencil", "School Supplies", 10, BigDecimal("0.50"), currentDateTime)
  val item2: Item = Item(2, product2, 6.0, 8.0, 1.0)
  val product3: Product = Product(3, "Apple", "Groceries", 150, BigDecimal("1.00"), currentDateTime)
  val item3: Item = Item(3, product3, 6.0, 8.0, 1.0)
  val product4: Product = Product(4, "T-Shirt", "Clothing", 200, BigDecimal("15.00"), currentDateTime)
  val item4: Item = Item(4, product4, 6.0, 8.0, 1.0)
  val product5: Product = Product(5, "Toothpaste", "Oral Hygiene", 75, BigDecimal("3.50"), currentDateTime)
  val item5: Item = Item(5, product5, 6.0, 8.0, 1.0)
  val product6: Product = Product(6, "Screw", "Tools", 5, BigDecimal("0.10"), currentDateTime)
  val item6: Item = Item(6, product6, 12.0, 16.0, 1.0)
  val orders: List[Order] = List(
    Order(1, List(item1), "Cliente 1", "contato_cliente1@example.com", "Endereço Cliente 1", BigDecimal("8.00"), currentDateTime),
    Order(2, List(item2), "Cliente 2", "contato_cliente2@example.com", "Endereço Cliente 2", BigDecimal("8.00"), currentDateTime),
    Order(3, List(item3), "Cliente 3", "contato_cliente3@example.com", "Endereço Cliente 3", BigDecimal("8.00"), currentDateTime),
    Order(4, List(item4), "Cliente 4", "contato_cliente4@example.com", "Endereço Cliente 4", BigDecimal("8.00"), currentDateTime),
    Order(5, List(item5), "Cliente 5", "contato_cliente5@example.com", "Endereço Cliente 5", BigDecimal("8.00"), currentDateTime),
    Order(6, List(item6), "Cliente 6", "contato_cliente6@example.com", "Endereço Cliente 6", BigDecimal("16.00"), currentDateTime),
    Order(7, List(item1), "Cliente 7", "contato_cliente7@example.com", "Endereço Cliente 7", BigDecimal("8.00"), currentDateTime),
    Order(8, List(item2), "Cliente 8", "contato_cliente8@example.com", "Endereço Cliente 8", BigDecimal("8.00"), currentDateTime),
    Order(9, List(item3), "Cliente 9", "contato_cliente9@example.com", "Endereço Cliente 9", BigDecimal("8.00"), currentDateTime),
    Order(10, List(item4), "Cliente 10", "contato_cliente10@example.com", "Endereço Cliente 10", BigDecimal("8.00"), currentDateTime),
    Order(11, List(item5), "Cliente 11", "contato_cliente11@example.com", "Endereço Cliente 11", BigDecimal("8.00"), currentDateTime),
    Order(12, List(item6), "Cliente 12", "contato_cliente12@example.com", "Endereço Cliente 12", BigDecimal("16.00"), currentDateTime)
  )



  test("filterOrdersByDate should filter orders correctly") {
    val startDate = LocalDateTime.of(2020, 10, 21, 14, 30, 0)
    val endDate = LocalDateTime.of(2023, 9, 21, 14, 30, 0)
    val filteredOrders = application.CalculateOrders.filterOrdersByDate(orders, startDate, endDate)
    assert(filteredOrders.isEmpty)
  }

  test("filterOrdersByDate should filter orders correctly (equals 12)") {
    val startDate = LocalDateTime.of(2020, 10, 21, 14, 30, 0)
    val endDate = LocalDateTime.now()
    val filteredOrders = application.CalculateOrders.filterOrdersByDate(orders, startDate, endDate)
    assert(filteredOrders.size == 12)
  }
  
  






}
