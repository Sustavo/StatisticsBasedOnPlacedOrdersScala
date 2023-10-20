package application

import application.CalculateOrders
import application.CalculateOrders.{ComparativeInterval, RegularInterval}
import connection.PopulateEntities
import validator.Validator

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Scanner
import scala.collection.mutable.ListBuffer

object Main {
  def main(args: Array[String]): Unit = {
    val scanner = new Scanner(System.in)
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    if (Validator.validateArgs(args) && Validator.validateDateFormat(args, formatter)) return

    val startDate = LocalDateTime.of(2020, 10, 21, 14, 30, 0)
    val endDate = LocalDateTime.of(2023, 9, 21, 14, 30, 0)

    val orders = PopulateEntities.orderAndProducts
    println("----------------------------------------")
    println(s"Total orders: ${orders.size}")
    println("----------------------------------------")

    lazy val filteredOrders = CalculateOrders.filterOrdersByDate(orders, startDate, endDate)
    println("Total filtered orders: " + filteredOrders.size)
    println("Orders filtered based on: " + startDate + " and " + endDate)
    println("----------------------------------------")

    val indexesList = new ListBuffer[Option[Either[RegularInterval, ComparativeInterval]]]()
    var loop = true

    while (loop) {
      println("Would you like to choose the range? (Yes or No)")
      val choose = scanner.nextLine().toLowerCase()
      choose match {
        case "yes" =>
          loop = false
          CalculateOrders.chooseIntervalOrders(filteredOrders, scanner, indexesList)
        case "no" =>
          loop = false
          CalculateOrders.defaultIntervalOrders(filteredOrders)
        case _ => println("Incorrect argument")

      }
    }
  }
}