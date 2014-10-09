package cs9322.coffee.rest.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Order {

	//TODO: Work out what we need in a status flag or something and how to link payment
	
	private int id;
    private String drink;
    private List<String> additions;
    private double cost;
    private String status;

    public Order(){
    	additions = new ArrayList<String>();
    }
    
    public Order(int id, String drink, List<String> additions, double cost, String status){
    	this.id = id;
    	this.drink = drink;
    	this.additions = additions;
    	this.cost = cost;
    	this.status = status;
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
		// TODO Auto-generated method stub
		this.cost = 9999;
		
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
}