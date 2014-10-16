package cs9322.coffee.rest.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CardDetail {
	private String name;
	private String number;
	private String cvc;
	
	public CardDetail() {
		this.name  = "";
		this.number = "";
		this.cvc = "";
	}
	
	public CardDetail(String card_name, String card_number, String card_cvc) {
		this.name  = card_name;
		this.number = card_number;
		this.cvc = card_cvc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCvc() {
		return cvc;
	}
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}
	
}
