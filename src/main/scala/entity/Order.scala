package entity

import java.time.LocalDateTime

class Order (
              private var id: Long,
              private var item: List[Item],
              private var clientName: String,
              private var contact: String,
              private var shippingAddress: String,
              private var grandTotal: BigDecimal,
              private var requestDate: LocalDateTime,
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
