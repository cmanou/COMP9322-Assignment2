package cs9322.coffee.barista.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

import java.net.URI;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import cs9322.coffee.rest.models.*;

public class OrderGet implements Action {
	static Logger logger = Logger.getLogger(OrderGet.class.getName());

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String id = request.getParameter("id");
		
		Client client = Client.create();
		WebResource service = client.resource(getBaseURI());
		// Create one book
		ClientResponse cresponse = service.path("rest").path("orders").path(id).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);

		logger.info("Status = " + cresponse.getStatus());
		Order o = cresponse.getEntity(Order.class);
		
		logger.info("Cost = " + o.getCost());

		return "/WEB-INF/orderGet.jsp";
	} 

	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/cs9322.coffee.rest").build();
	}

}
