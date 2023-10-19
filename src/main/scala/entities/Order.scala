package entities

import java.time.{LocalDate, LocalDateTime}
import scala.collection.mutable.ListBuffer

class Order (
              private var id: Long,
              private var item: Any,
              private var clientName: String,
              private var contact: String,
              private var shippingAddress: String,
              private var grandTotal: BigDecimal,
              private var requestDate: LocalDateTime,
            ) {
  def getId: Long = id

  def getItem: Any = item

  def setItem(newItem: Any): Unit = item = newItem

  def getClientName: String = clientName

  def getContact: String = contact

  def getShippingAddress: String = shippingAddress

  def getGrandTotal: BigDecimal = grandTotal

  def getRequestDate: LocalDateTime = requestDate

  def copy(
            id: Long = this.id,
            item: Any = this.item,
            clientName: String = this.clientName,
            contact: String = this.contact,
            shippingAddress: String = this.shippingAddress,
            grandTotal: BigDecimal = this.grandTotal,
            requestDate: LocalDateTime = this.requestDate
          ): Order = {
    new Order(id, item, clientName, contact, shippingAddress, grandTotal, requestDate)
  }

  override def toString: String = {
    s"id: $id items: ${item}, Client Name: $clientName, Contact: $contact,  Shipping Address: $shippingAddress, Grand Total: $grandTotal, Request Date: $requestDate \n"
  }


}
