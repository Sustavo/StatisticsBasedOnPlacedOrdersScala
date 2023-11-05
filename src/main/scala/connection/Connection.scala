package connection

import entity.{Item, Order, Product}

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global

object Connection {
  val db: Database = Database.forConfig("mydb")
}
