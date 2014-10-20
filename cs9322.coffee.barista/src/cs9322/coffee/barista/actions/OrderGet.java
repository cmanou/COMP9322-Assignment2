package cs9322.coffee.barista.actions;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import cs9322.coffee.rest.models.Order;

import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;


public class OrderGet extends Action {
	static Logger logger = Logger.getLogger(OrderGet.class.getName());

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String id = request.getParameter("id");
		
		Client client = Client.create();
		WebResource service = client.resource(getBaseURI());

		ClientResponse cresponse = service.path("rest").path("orders").path(id).accept(MediaType.APPLICATION_XML).header("key", "barista").get(ClientResponse.class);
		logger.info("Status = " + cresponse.getStatus());

		Order o = null;
		if(cresponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
			o = cresponse.getEntity(Order.class);
			logger.info("Drink = " + o.getDrink());

		}
		
		request.setAttribute("order", o);
		return "/WEB-INF/orderGet.jsp";
	} 

}
