package cs9322.coffee.rest.models;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrdersList {
	
	private List<Order> ordersList;
	
	public OrdersList() {
	}
	
	public OrdersList(List<Order> aOrdersList) {
		this.ordersList = aOrdersList;
	}
	
	@XmlElement(name="order")
	public List<Order> getOrdersList() {
		return this.ordersList;
	}
	
	public void setOrdersList(List<Order> aOrdersList) {
		this.ordersList = aOrdersList;
	}
}
