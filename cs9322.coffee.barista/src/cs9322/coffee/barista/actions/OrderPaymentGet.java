package cs9322.coffee.barista.actions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

import cs9322.coffee.rest.models.Order;
import cs9322.coffee.rest.models.Payment;

public class OrderPaymentGet extends Action {
	static Logger logger = Logger.getLogger(OrderPaymentGet.class.getName());

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

		ClientResponse cresponse = service.path("rest").path("payments").path(id).accept(MediaType.APPLICATION_XML).header("key", "barista").get(ClientResponse.class);
		logger.info("Status = " + cresponse.getStatus());

		Payment p = null;
		if(cresponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
			p = cresponse.getEntity(Payment.class);
			logger.info("Payment = " + p.getAmount());

		}

		request.setAttribute("response", cresponse.getStatusInfo());
		request.setAttribute("id", id);
		request.setAttribute("payment", p);
		return "/WEB-INF/paymentGet.jsp";
	} 

}
