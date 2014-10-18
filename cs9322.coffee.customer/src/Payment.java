

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Payment {
	
	private int id; //this is same as order id to make it easy for us
    private String type; //CASH or CARD
    private double amount;
    private List<Link> link; //ie self and parent
    
    private String cardName;
	private String cardNumber;
	private String cardCVC;
    
    public static String CASH = "CASH";
    public static String CARD = "CARD";
    
    public Payment()
    {
    	this.id = -1;
		this.type = "";
		this.amount = -1;
		this.cardName = "";
		this.cardNumber = "";
		this.cardCVC = "";
		this.link = new ArrayList<Link>();
    }
    
	public Payment(int id, String type, double amount, String card_number,
			String card_name, String card_cvc) {
		this.id = id;
		this.type = type;
		this.amount = amount;
		if(type.equals(CARD)) {
			this.cardName = card_name;
			this.cardNumber = card_number;
			this.cardCVC = card_cvc;
		}
		
		this.link = new ArrayList<Link>();
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
	public List<Link> getLinks() {
		return link;
	}
	public void setLinks(List<Link> link) {
		this.link = link;
	}
	
	public String getCardName() {
		return this.cardName;
	}
	
	public void setCardName(String aCardName) {
		this.cardName = aCardName;
	}
	
	public String getCardNumber() {
		return this.cardNumber;
	}
	
	public void setCardNumber(String aCardNumber) {
		this.cardNumber = aCardNumber;
	}
	
	public String getCardCVC() {
		return this.cardCVC;
	}
	
	public void setCardCVC(String aCardCVC) {
		this.cardCVC = aCardCVC;
	}
	
	public void generateLinks(UriInfo aUriInfo) { //CALL before you want to display
		
		this.link.clear();
		
		// Get base URI and create needed links.
		URI myURI = aUriInfo.getBaseUri();
				
		// Always be able to get order until deleted.
		String selfURI = myURI.toString()+"payment/"+this.id;
		link.add(new Link("self", selfURI));
		
	}
   
}