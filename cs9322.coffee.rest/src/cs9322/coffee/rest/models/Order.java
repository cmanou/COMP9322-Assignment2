package cs9322.coffee.rest.models;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
public class Order {
	
	private int id;
    private String drink;
    private List<String> additions;
    private double cost;
    private String status; //PLACED, CANCELLED, SERVED, PREPARING
    private String paymentStatus; //PAID UNPAID
    private List<Link> link; //ie self and parent
    
  
    public static final String STATUS_PLACED = "PLACED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_SERVED = "SERVED";
    public static final String STATUS_PREPARING = "PREPARING";

    public static final String PAID = "PAID";
    public static final String UNPAID = "UNPAID";
    
    public Order(){
    	additions = new ArrayList<String>();
    	cost = 0;
    	link = new ArrayList<Link>();
    }
    
    public Order(int id, String drink, List<String> additions, double cost, String status, String paymentStatus){
    	this.id = id;
    	this.drink = drink;
    	this.additions = additions;
    	this.cost = cost;
    	this.status = status;
    	this.link = new ArrayList<Link>();
    	this.paymentStatus = paymentStatus;

    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDrink() {
		return drink;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}

	public List<String> getAdditions() {
		return additions;
	}

	public void setAdditions(List<String> additions) {
		this.additions = additions;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void calculateCost() {
		cost = 0;
		if(!drink.isEmpty()) {
			cost += 3.5;
		}
		cost += 0.3 * additions.size();
		
	}

	public void addAddition(String addition) {
		additions.add(addition);
		
	}

	@XmlTransient
	public String getAvaliableOptions() {
		// Can get at anytime.
		List<String> temp = new ArrayList<String>();
		
		temp.add("GET");
		
		// Can amend order only when it has just been placed.
		if(this.status.equals(Order.STATUS_PLACED) && !paid()) { 
			temp.add("PUT");
			temp.add("DELETE");
		}
		//TODO maybe check if it is barista cause they can always put?
		return temp.toString().substring(1, temp.toString().length()-1);
	}
	
	public void generateLinks(UriInfo aUriInfo) { //CALL before you want to display
		
		this.link.clear();
		
		// Get base URI and create needed links.
		URI myURI = aUriInfo.getBaseUri();
		
		// Always be able to get order until deleted.
		String selfURI = myURI.toString()+"orders/"+this.id;
		link.add(new Link("self", selfURI));
		
		if(!this.status.equals(Order.STATUS_CANCELLED)) {
		// Payment details can be retrieved at any time except when order canceled.
		String paymentURI = myURI.toString()+"payment/"+this.id;
		link.add(new Link("payment", paymentURI));
		}
		
	}
	
	public boolean paid() {
		return paymentStatus.equals(PAID);
	}
	
	public List<Link> getLinks() {
		return link;
	}

	public void setLinks(List<Link> links) {
		this.link= links;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
}