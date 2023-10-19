package connection

import java.sql.*

object ConnectJDBC {
  private val url = "jdbc:postgresql://localhost:5432/Orders"
  private val user = "postgres"
  private val password = "postgres"
  var connection: Connection = null

  def consultDB(value: String): List[List[String]] = {
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

  def transformInEntities[T](listString: List[List[String]], columnsTypes: List[String])(implicit tag: reflect.ClassTag[T]): List[T] = {
    def auxConvertValue(valueString: String, fieldType: String): Any = {
      fieldType match {
        case "String" => valueString
        case "Int" => valueString.toInt
        case "Double" => valueString.toDouble
        case "Long" => valueString.toLong
        case "BigDecimal" => BigDecimal(valueString)
        case "LocalDateTime" =>
          val timestamp = Timestamp.valueOf(valueString)
          timestamp.toLocalDateTime
        case _ => throw new IllegalArgumentException(s"Unsupported field type: $fieldType")
      }
    }
    var resultList = List.empty[T]
    for (valueList <- listString) {
      val values = valueList.zip(columnsTypes).map {
        case (valueString, fieldType) => auxConvertValue(valueString, fieldType)
      }
      val constructor = tag.runtimeClass.getConstructors.find(_.getParameterCount == values.length)
      constructor match {
        case Some(cons) =>
          val newObject = cons.newInstance(values: _*).asInstanceOf[T]
          resultList = resultList :+ newObject
        case None =>
          throw new IllegalArgumentException("No suitable constructor found.")
      }
    }
    resultList
  }


//  def transformToList[T](resultSet: ResultSet, columns: List[(String, String)])(implicit tag: reflect.ClassTag[T]): List[T] = {
//    var resultList = List.empty[T]
//    while (resultSet.next()) {
//      val values = columns.map {
//        case (columnName, fieldType) =>
//          fieldType match {
//            case "String" => resultSet.getString(columnName)
//            case "Int" => resultSet.getInt(columnName)
//            case "Double" => resultSet.getDouble(columnName)
//            case "Long" => resultSet.getLong(columnName)
//            case "BigDecimal" => scala.math.BigDecimal(resultSet.getBigDecimal(columnName))
//            case "LocalDateTime" => {
//              val timestamp = resultSet.getTimestamp(columnName)
//              timestamp.toLocalDateTime.asInstanceOf[Any]
//            }
//            case _ => throw new IllegalArgumentException(s"Unsupported field type: $fieldType")
//          }
//      }
//      val constructor = tag.runtimeClass.getConstructors.head
//      val item = constructor.newInstance(values: _*).asInstanceOf[T]
//      resultList = resultList :+ item
//    }
//    resultList
//  }

}
