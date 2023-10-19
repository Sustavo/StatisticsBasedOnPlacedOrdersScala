package connection

import entities.Item

import java.sql.{Connection, DriverManager, ResultSet, SQLException}

object ConnectJDBC {
  val url = "jdbc:postgresql://localhost:5432/Orders"
  val user = "postgres"
  val password = "postgres"
  var connection: Connection = null

  def consultDB(value: String) = {
    try {
      connection = DriverManager.getConnection(url, user, password)
      val result: ResultSet = connection.createStatement().executeQuery(value)
      result
    } catch {
      case e: SQLException => {
        e.printStackTrace()
        throw new RuntimeException("Error connecting to the database.")
      }
    } finally {
      if (connection != null) {
        connection.close()
      }
    }
  }

//  def whereConsultDB[T](valueWhere: String => String, listValues: List[Int])(implicit tag: reflect.ClassTag[T]): List[T] = {
//    var resultList = List.empty[T]
//
//    def auxWhereConsultDB(list: List[Int], resultList: List[T]): List[T] = {
//      list match {
//        case Nil => resultList
//        case head :: tail =>
//          val modifiedValue = valueWhere(head.toString) // Modifica o valor com a função valueWhere
//          val result = consultDB(modifiedValue) // Consulta o banco de dados
//          auxWhereConsultDB(tail, resultList ++ transformToList(result)) // Chama recursivamente para o restante da lista
//      }
//    }
//
//    auxWhereConsultDB(listValues, resultList)
//  }

  def transformToList[T](resultSet: ResultSet, columns: List[(String, String)])(implicit tag: reflect.ClassTag[T]): List[T] = {
    var resultList = List.empty[T]
    while (resultSet.next()) {
      val values = columns.map {
        case (columnName, fieldType) =>
          fieldType match {
            case "String" => resultSet.getString(columnName)
            case "Int" => resultSet.getInt(columnName)
            case "Double" => resultSet.getDouble(columnName)
            case "Long" => resultSet.getLong(columnName)
            case "BigDecimal" => scala.math.BigDecimal(resultSet.getBigDecimal(columnName))
            case "LocalDateTime" => {
              val timestamp = resultSet.getTimestamp(columnName)
              timestamp.toLocalDateTime.asInstanceOf[Any]
            }
            case _ => throw new IllegalArgumentException(s"Unsupported field type: $fieldType")
          }
      }
      val constructor = tag.runtimeClass.getConstructors.head
      val item = constructor.newInstance(values: _*).asInstanceOf[T]
      resultList = resultList :+ item
    }
    resultList
  }

}
