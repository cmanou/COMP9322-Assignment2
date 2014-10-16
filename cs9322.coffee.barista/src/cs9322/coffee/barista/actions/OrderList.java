package cs9322.coffee.barista.actions;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;



public class OrderList extends Action {
	static Logger logger = Logger.getLogger(OrderList.class.getName());

	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Called OrderList");
		Client client = Client.create();
		WebResource service = client.resource(getBaseURI());

		ClientResponse cresponse = service.path("rest").path("orders").accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		logger.info("Status = " + cresponse.getStatus());

		
		logger.info("XML = " + cresponse.getEntity(String.class));

		return "/WEB-INF/orderList.jsp";
	} 

}
