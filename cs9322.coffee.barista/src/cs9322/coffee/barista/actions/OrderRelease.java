package cs9322.coffee.barista.actions;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import cs9322.coffee.rest.models.Order;

public class OrderRelease extends Action {
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
			
			o.setStatus(Order.STATUS_SERVED);
			ClientResponse presponse = service.path("rest").path("orders").path(id)
					.type(MediaType.APPLICATION_XML).header("key", "barista").put(ClientResponse.class, o);

			logger.info("PUT Status = " + presponse.getStatus());

		}
		Action redirect = new OrderList();
		return redirect.execute(request, response);
	}

}
