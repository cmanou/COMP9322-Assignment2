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

	public void addAddition(String addition) {
		additions.add(addition);
		
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
	
	public boolean isFinished() {
		return status.equals(STATUS_SERVED) || status.equals(STATUS_CANCELLED);
	}
}