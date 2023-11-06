package validator

import java.time.{LocalDate, LocalDateTime}
import java.time.format.{DateTimeFormatter, DateTimeParseException}

object Validator {
  def validateArgs(args: Array[String]): Unit = {
    if (args.length != 2)  throw new IllegalArgumentException("you need to enter two dates:\n" + "Start Date and End Date")
  }

  def validateDateFormat(args: Array[String], formatter: DateTimeFormatter): Unit = {
    try {
      val start = LocalDate.parse(args(0), formatter)
      val end = LocalDate.parse(args(1), formatter)

      if (start.isAfter(end)) throw new IllegalArgumentException("The start date cannot be later than the end date.")

    } catch
      case _: DateTimeParseException => println("An error occurred when trying to use the arguments passed.\nit is necessary to use the format: \"YYYY-MM-DD HH:mm:ss\"")
  }

  def validateIntervalParameters(start: Int, end: Int): Unit = {
    if (end < start) throw new IllegalArgumentException("The value of 'end' must be greater than 'start'.")
  }
  
}
