package entity

import java.time.LocalDateTime

case class Order (
              var id: Long,
              var item: List[Item],
              var clientName: String,
              var contact: String,
              var shippingAddress: String,
              var grandTotal: BigDecimal,
              var requestDate: LocalDateTime,
            ) {
  def getId: Long = id

  def getItem: List[Item] = item

  def getClientName: String = clientName

  def getContact: String = contact

  def getShippingAddress: String = shippingAddress

  def getGrandTotal: BigDecimal = grandTotal

  def getRequestDate: LocalDateTime = requestDate

  override def toString: String = {
    s"id: $id items: $item, Client Name: $clientName, Contact: $contact,  Shipping Address: $shippingAddress, Grand Total: $grandTotal, Request Date: $requestDate \n"
  }


}
