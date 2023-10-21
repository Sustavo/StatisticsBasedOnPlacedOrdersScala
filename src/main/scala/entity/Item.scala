package entity

class Item(
            private val id: Long,
            private val product: Product,
            private val shippingFee: BigDecimal,
            private val taxAmount: BigDecimal,
            private val cost: BigDecimal
          ) {

  def getId: Long = id

  def getProduct: Product = product

  def getShippingFee: BigDecimal = shippingFee

  def getTaxAmount: BigDecimal = taxAmount

  def getCost: BigDecimal = cost

  override def toString: String = {
    s"id: $id Product: ${product.toString}, Cost: $cost, Shipping Fee: $shippingFee,  Tax Amount: $taxAmount \n"
  }
}