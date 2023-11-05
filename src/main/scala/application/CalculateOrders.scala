package application

import entity.Order
import validator.Validator

import java.time.LocalDateTime
import java.util.Scanner
import scala.collection.mutable.ListBuffer

object CalculateOrders {
  case class RegularInterval(start: Int, end: Int)
  case class ComparativeInterval(operator: String, value: Int)

  def filterOrdersByDate(orders: List[Order], startDate: LocalDateTime, endDate: LocalDateTime): List[Order] = {
    orders.filter{order =>
      order.getRequestDate.isAfter(startDate) && order.getRequestDate.isBefore(endDate) &&
      order.getItem.forall(item => item.getProduct.getCreationDate.isAfter(startDate) && item.getProduct.getCreationDate.isBefore(endDate))
    }
  }

  private def calculateIntervalOrders(orders: List[Order], start: Int, end: Int): Unit = {
    val currentDate = LocalDateTime.now()
    val result = orders.to(LazyList).count((order: Order) =>
      order.getRequestDate.isBefore(currentDate.minusMonths(start)) && order.getRequestDate.isAfter(currentDate.minusMonths(end)))

    println(s"$start-$end months: $result")
  }

  private def calculateIntervalOrdersByComparative(orders: List[Order], comparator: String, month: Int): Unit = {
    val currentDate = LocalDateTime.now()
    val result = comparator match {
      case ">" => orders.count((order: Order) =>
        order.getRequestDate.isAfter(currentDate.minusMonths(999)) && order.getRequestDate.isBefore(currentDate.minusMonths(month))
      )
      case "<" => orders.count((order: Order) =>
        order.getRequestDate.isAfter(currentDate.minusMonths(month)) && order.getRequestDate.isBefore(currentDate.minusMonths(0)))
      case _ => 0
    }

    println(s"$comparator$month months: $result")
  }

  def chooseIntervalOrders(orders: List[Order], scanner: Scanner, indexesList: ListBuffer[Option[Either[RegularInterval, ComparativeInterval]]]): Unit = {
    println("Choose your interval (ex: 1-6 or >15): ")
    val input = scanner.nextLine()
    val interval = parseInterval(input)
    if(interval.nonEmpty) indexesList += interval

    var loop = true

    while(loop) {
      println("Do you want to continue? (Yes or No)")
      val stop = scanner.nextLine().toLowerCase()
      stop match {
        case "yes" =>
          loop = false
          chooseIntervalOrders(orders, scanner, indexesList)

        case "no" =>
          loop = false
          processIntervals(orders, indexesList)

        case _ => println("Invalid Argument")
      }
    }
  }

  private def parseInterval(input: String): Option[Either[RegularInterval, ComparativeInterval]] = {
      if (input.contains("-")) {
        val parts = input.split("-")
        if (parts.length == 2) {
          val start = parts(0).trim().toInt
          val end = parts(1).trim().toInt
          Validator.validateIntervalParameters(start, end)
          Some(Left(RegularInterval(start, end)))
        } else None
      } else if (input.startsWith(">") || input.startsWith("<")) {
        val operator = input.substring(0, 1)
        val value = input.substring(1).trim().toInt
        Some(Right(ComparativeInterval(operator, value)))
      } else None
  }

  private def processIntervals(orders: List[Order], indexesList: ListBuffer[Option[Either[RegularInterval, ComparativeInterval]]]): Unit = {
    def auxProcessIndexes(index: Option[Either[RegularInterval, ComparativeInterval]]): Unit = {
      index match {
        case Some(Right(ComparativeInterval(">", month: Int))) => calculateIntervalOrdersByComparative(orders, ">", month)
        case Some(Right(ComparativeInterval("<", month))) => calculateIntervalOrdersByComparative(orders, "<", month)
        case Some(Left(RegularInterval(start, end))) => CalculateOrders.calculateIntervalOrders(orders, start, end)
        case _ => println("Invalid Argument")
      }
    }

    println("Result: ")
    indexesList.foreach(index => auxProcessIndexes(index))

  }

  def defaultIntervalOrders(orders: List[Order]): Unit = {
    println("Result: ")
    calculateIntervalOrders(orders, 1, 3)
    calculateIntervalOrders(orders, 4, 6)
    calculateIntervalOrders(orders, 7, 12)
    calculateIntervalOrdersByComparative(orders, ">", 12)
  }

}
