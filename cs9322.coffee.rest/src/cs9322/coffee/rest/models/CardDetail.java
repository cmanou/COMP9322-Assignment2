package cs9322.coffee.rest.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CardDetail {
	private String name;
	private String number;
	private String cvc;
	
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
