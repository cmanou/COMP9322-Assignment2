package cs9322.coffee.rest.models;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PaymentList {
	
	private List<Payment> paymentList;
	
	public PaymentList() {
	}
	
	public PaymentList(List<Payment> aPaymentList) {
		this.paymentList = aPaymentList;
	}
	
	@XmlElement(name="payment")
	public List<Payment> getOrdersList() {
		return this.paymentList;
	}
	
	public void setOrdersList(List<Payment> aOrdersList) {
		this.paymentList = aOrdersList;
	}
}
