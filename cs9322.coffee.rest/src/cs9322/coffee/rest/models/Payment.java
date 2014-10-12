package cs9322.coffee.rest.models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Payment {
	
	private int id; //this is same as order id to make it easy for us
    private String type; //CASH or CARD
    private double amount;
    private List<Link> link; //ie self and parent
    private CardDetail card;
    
    public static String CASH = "CASH";
    public static String CARD = "CARD";
    
	public Payment(int id, String type, double amount, String card_number,
			String card_name, String card_cvc) {
		this.id = id;
		this.type = type;
		this.amount = amount;
		if(type.equals(CARD)) {
			this.card = new CardDetail(card_name, card_number, card_cvc);
		}
	}
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
	public Object getAvaliableOptions() {
		// TODO Auto-generated method stub
		return null;
	}
    

 
   
}