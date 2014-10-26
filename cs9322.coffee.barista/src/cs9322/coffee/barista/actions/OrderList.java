package cs9322.coffee.barista.actions;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

import cs9322.coffee.rest.models.*;



public class OrderList extends Action {
	static Logger logger = Logger.getLogger(OrderList.class.getName());

	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Called OrderList");
		
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

		ClientResponse cresponse = service.path("rest").path("orders").accept(MediaType.APPLICATION_XML).header("key", "barista").get(ClientResponse.class);
		OrdersList o = null;
		logger.info("Status: " + cresponse.getStatus());
		if(cresponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
			o = cresponse.getEntity(OrdersList.class);
			request.setAttribute("orders", o.getOrdersList());
//			logger.info("Orders COunt" + o.getOrdersList().toString());
		}
		request.setAttribute("response", cresponse.getStatusInfo());

		return "/WEB-INF/orderList.jsp";
	} 

}
