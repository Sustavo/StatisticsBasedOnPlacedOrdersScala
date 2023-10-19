package entities

import java.time.LocalDateTime

class Product(
                var id: Long,
                var name: String,
                var category: String,
                var weight: Double,
                var price: BigDecimal,
                var creationDate: LocalDateTime
             ) {
  def getId: Long = id

  def getName: String = name

  def getCategory: String = category

  def getWeight: Double = weight

  def getPrice: BigDecimal = price

  def getCreationDate: LocalDateTime = creationDate
  


  override def toString: String = {
    s"id: $id Name: ${name}, Category: $category, weight: $weight,  Price: $price, Creation Date: $creationDate \n"
  }

}
