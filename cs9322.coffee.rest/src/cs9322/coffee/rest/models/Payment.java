package cs9322.coffee.rest.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Payment {
	
	private int id;
    private String type; //CASH or CARD
    private double amount;
    private List<Link> link; //ie self and parent
    private CardDetail card;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public List<Link> getLink() {
		return link;
	}
	public void setLink(List<Link> link) {
		this.link = link;
	}
	public CardDetail getCard() {
		return card;
	}
	public void setCard(CardDetail card) {
		this.card = card;
	}
	
	public void generateLinks() { //CALL before you want to display
		//TODO add depending on status
		link.add(new Link("self", "http://test.com"));
		link.add(new Link("payment", "http://test.com"));
		
	}
    

 
   
}