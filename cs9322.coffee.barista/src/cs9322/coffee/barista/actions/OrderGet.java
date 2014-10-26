package cs9322.coffee.barista.actions;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

import cs9322.coffee.rest.models.Order;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;


public class OrderGet extends Action {
	static Logger logger = Logger.getLogger(OrderGet.class.getName());

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String id = request.getParameter("id");
		
		ClientConfig config = new DefaultClientConfig();
		Client client = new Client(new URLConnectionClientHandler(
		        new HttpURLConnectionFactory() {
		    @Override
		    public HttpURLConnection getHttpURLConnection(URL url)
		            throws IOException {
		        return (HttpURLConnection) url.openConnection(java.net.Proxy.NO_PROXY);
		    }
		}), config);
		
		WebResource service = client.resource(getBaseURI());

		ClientResponse cresponse = service.path("rest").path("orders").path(id).accept(MediaType.APPLICATION_XML).header("key", "barista").get(ClientResponse.class);
		logger.info("Status = " + cresponse.getStatus());

		Order o = null;
		if(cresponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
			o = cresponse.getEntity(Order.class);
			logger.info("Drink = " + o.getDrink());

		}
		request.setAttribute("response", cresponse.getStatusInfo());

		request.setAttribute("order", o);
		return "/WEB-INF/orderGet.jsp";
	} 

}
