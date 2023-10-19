package application

import entities.Order
import validator.Validator

import java.time.LocalDateTime
import java.util.Scanner
import scala.collection.mutable.ListBuffer

object CalculateOrders {
  def filterOrdersByDate(orders: List[(Order, List[LocalDateTime])], startDate: LocalDateTime, endDate: LocalDateTime): List[Order] = {
    orders.filter { case (order, dateProducts) =>
      order.getRequestDate.isAfter(startDate) && order.getRequestDate.isBefore(endDate) &&
        dateProducts.forall(date => date.isAfter(startDate) && date.isBefore(endDate))
    }.map(_._1)
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

  def chooseIntervalOrders(orders: List[Order], scanner: Scanner, startList: ListBuffer[Any], endList: ListBuffer[Any]): Unit = {
    println("Choose your interval (ex: 1-6 or >15): ")
    val input = scanner.nextLine()

    val interval = if (input.contains("-")) {
      val parts = input.split("-")
      if (parts.length == 2) {
        val start = Integer.parseInt(parts(0).trim())
        val end = Integer.parseInt(parts(1).trim())
        Validator.EndIsBiggerThanStart(start, end)
        List(start, end)
      } else {
        List.empty[Int]
      }
    } else if (input.startsWith(">") || input.startsWith("<")) {
      val operator = input.substring(0, 1)
      val value = Integer.parseInt(input.substring(1).trim())
      List(operator, value)
    } else {
      List.empty[Int]
    }

    startList += interval.head
    endList += interval(1)
    var loop = true

    while (loop) {
      println("Do you want to stop? (Yes or No)")
      val stop = scanner.nextLine().toLowerCase()
      stop match {
        case "yes" =>
          loop = false
          println("Result: ")
          for (i <- startList.indices) {
            if ((startList(i) == ">" || startList(i) == "<") && startList(i).isInstanceOf[String] && endList(i).isInstanceOf[Int]) {
              val operator = startList(i).toString
              val month = endList(i).asInstanceOf[Int]
              calculateIntervalOrdersByComparative(orders, operator, month)
            } else {
              val start = startList(i).asInstanceOf[Int]
              val end = endList(i).asInstanceOf[Int]
              CalculateOrders.calculateIntervalOrders(orders, start, end)
            }
          }
        case "no" =>
          loop = false
          chooseIntervalOrders(orders, scanner, startList, endList)
        case _ => println("Invalid Argument")
      }
    }

  }


  def defaultIntervalOrders(orders: List[Order]): Unit = {
    println("Result: ")
    calculateIntervalOrders(orders, 1, 3)
    calculateIntervalOrders(orders, 4, 6)
    calculateIntervalOrders(orders, 7, 12)
    calculateIntervalOrdersByComparative(orders, ">", 12)
  }

}
