package cs9322.coffee.barista.actions;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class OrderPaymentGet extends Action {
	static Logger logger = Logger.getLogger(OrderPaymentGet.class.getName());

	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String id = request.getParameter("id");
		
		Client client = Client.create();
		WebResource service = client.resource(getBaseURI());

		ClientResponse cresponse = service.path("rest").path("payments").path(id).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		logger.info("Status = " + cresponse.getStatus());

		
		logger.info("XML = " + cresponse.getEntity(String.class));

		return "/WEB-INF/orderGet.jsp";
	} 

}
