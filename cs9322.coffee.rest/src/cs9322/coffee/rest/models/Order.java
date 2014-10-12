package cs9322.coffee.rest.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import cs9322.coffee.rest.dao.PaymentsDAO;

@XmlRootElement
public class Order {

	//TODO: Work out what we need in a status flag or something and how to link payment
	
	private int id;
    private String drink;
    private List<String> additions;
    private double cost;
    private String status; //PLACED, PAID, CANCELLED, SERVED
    private List<Link> link; //ie self and parent
    
  
    public static String STATUS_PLACED = "PLACED";
    public static String STATUS_PAID = "PAID";
    public static String STATUS_CANCELLED = "CANCELLED";
    public static String STATUS_SERVED = "SERVED";

    public Order(){
    	additions = new ArrayList<String>();
    	cost = 0;
    	link = new ArrayList<Link>();
    }
    
    public Order(int id, String drink, List<String> additions, double cost, String status){
    	this.id = id;
    	this.drink = drink;
    	this.additions = additions;
    	this.cost = cost;
    	this.status = status;
    	this.link = new ArrayList<Link>();

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

	@XmlTransient
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
		// TODO have a map from status to options
		List<String> temp = Arrays.asList("GET", "PUT");
		return temp.toString().substring(1, temp.toString().length()-1);
	}
	
	public void generateLinks() { //CALL before you want to display
		//TODO add depending on status
		link.add(new Link("self", "http://test.com"));
		link.add(new Link("payment", "http://test.com"));
		
	}
	
	public boolean paid() {
		return PaymentsDAO.instance.paymentExits(id);
	}
	
	public List<Link> getLinks() {
		return link;
	}

	public void setLinks(List<Link> links) {
		this.link= links;
	}
}