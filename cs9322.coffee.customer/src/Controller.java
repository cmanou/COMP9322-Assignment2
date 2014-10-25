

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			// Set web resource base uri.
//			String myBaseURI = request.getScheme()+"://"+request.getLocalAddr()+":"+request.getLocalPort()+"/cs9322.coffee.rest/rest";
//			String myBaseURI = "http://cjze477.srvr:8080/cs9322.coffee.rest/rest";
			String myBaseURI = "http://localhost:8080/cs9322.coffee.rest/rest";
									
			// Get action.
			String aAction = request.getParameter("aAction");
			
			// Set default page.
			String nextPage = "index.jsp";
			
			if(aAction != null) {
				if(aAction.equals("navigate"))
				{
					// Get page.
					String page = request.getParameter("aPage");
					
					if(page != null){
						if(page.equals("createOrder"))
						{
							nextPage = "createOrder.jsp";
						} else {
							
							// Set up default landing page //
							this.setUpDefaultPage(request, myBaseURI);
						}
					} 
				} else if(aAction.equals("getOptions")) {
					
					// Get parameters.
					String aId = request.getParameter("aId");
					
					// Create client with default configurations.
					ClientConfig defaultConfig = new DefaultClientConfig();
					Client myClient = new Client(new URLConnectionClientHandler(
					        new HttpURLConnectionFactory() {
					    @Override
					    public HttpURLConnection getHttpURLConnection(URL url)
					            throws IOException {
					        return (HttpURLConnection) url.openConnection(java.net.Proxy.NO_PROXY);
					    }
					}), defaultConfig);

					WebResource ordersResource = myClient.resource(myBaseURI);
					
				    // Specify request attributes and invoke 
					ClientResponse reply = ordersResource.
				    	path("orders").
				    	path(aId).
				    	accept(MediaType.WILDCARD_TYPE).
				        header("key", "customer").
				        options(ClientResponse.class);
				    
					if(reply.getStatus() == ClientResponse.Status.OK.getStatusCode())
					{
						MultivaluedMap<String,String> myMap = reply.getHeaders();
						String options = myMap.getFirst("Allow");
						
						request.setAttribute("options", options);
						request.setAttribute("optionsId", aId);
					}
					else
					{
						System.out.println("Reply status not 200! : "+reply.getStatus()+": "+reply.getStatusInfo().getReasonPhrase());
						System.exit(1);
					}
					
					// Set up default landing page //
					this.setUpDefaultPage(request, myBaseURI);
					
				} else if(aAction.equals("getOrder")) {
					
					// Get parameters.
					String aId = request.getParameter("aId");
					
					// Create client with default configurations.
					ClientConfig defaultConfig = new DefaultClientConfig();
					Client myClient = new Client(new URLConnectionClientHandler(
					        new HttpURLConnectionFactory() {
					    @Override
					    public HttpURLConnection getHttpURLConnection(URL url)
					            throws IOException {
					        return (HttpURLConnection) url.openConnection(java.net.Proxy.NO_PROXY);
					    }
					}), defaultConfig);

					WebResource ordersResource = myClient.resource(myBaseURI);
					
				    // Specify request attributes and invoke 
					ClientResponse reply = ordersResource.
				    	path("orders").
				    	path(aId).
				    	accept(MediaType.WILDCARD_TYPE).
				        header("key", "customer").
				        get(ClientResponse.class);
				    
					if(reply.getStatus() == ClientResponse.Status.OK.getStatusCode())
					{
						Order receivedOrder = reply.getEntity(Order.class);
						
						request.setAttribute("myOrder", receivedOrder);
						
						nextPage = "orderDetails.jsp";
					}
					else
					{
						System.out.println("Reply status not 200!");
						System.exit(1);
					}
					
					// Set up default landing page //
					this.setUpDefaultPage(request, myBaseURI);
					
				} else if(aAction.equals("cancelOrder")) {
					
					// Get parameters.
					String aId = request.getParameter("aId");
					
					// Create client with default configurations.
					ClientConfig defaultConfig = new DefaultClientConfig();
					Client myClient = new Client(new URLConnectionClientHandler(
					        new HttpURLConnectionFactory() {
					    @Override
					    public HttpURLConnection getHttpURLConnection(URL url)
					            throws IOException {
					        return (HttpURLConnection) url.openConnection(java.net.Proxy.NO_PROXY);
					    }
					}), defaultConfig);

					WebResource ordersResource = myClient.resource(myBaseURI);
					
				    // Specify request attributes and invoke 
					ClientResponse reply = ordersResource.
				    	path("orders").
				    	path(aId).
				    	accept(MediaType.WILDCARD_TYPE).
				        header("key", "customer").
				        delete(ClientResponse.class);
				    
					if(reply.getStatus() == ClientResponse.Status.OK.getStatusCode())
					{
						request.setAttribute("result", "Order Cancelled!");
						request.setAttribute("resultId", aId);
					}
					else if(reply.getStatus() == ClientResponse.Status.PRECONDITION_FAILED.getStatusCode())
					{
						request.setAttribute("result", "Order already Canclled!");
						request.setAttribute("resultId", aId);
					} else {
						System.out.println("Unknown error: "+reply.getStatus());
						System.exit(1);
					}
					
					// Set up default landing page //
					this.setUpDefaultPage(request, myBaseURI);
					
				} else if(aAction.equals("updateOrder")) {
					
					// Get parameters.
					String aId = request.getParameter("aId");
					
					// Create client with default configurations.
					ClientConfig defaultConfig = new DefaultClientConfig();
					Client myClient = new Client(new URLConnectionClientHandler(
					        new HttpURLConnectionFactory() {
					    @Override
					    public HttpURLConnection getHttpURLConnection(URL url)
					            throws IOException {
					        return (HttpURLConnection) url.openConnection(java.net.Proxy.NO_PROXY);
					    }
					}), defaultConfig);

					WebResource ordersResource = myClient.resource(myBaseURI);
					
				    // Specify request attributes and invoke 
					ClientResponse reply = ordersResource.
				    	path("orders").
				    	path(aId).
				    	accept(MediaType.WILDCARD_TYPE).
				        header("key", "customer").
				        get(ClientResponse.class);
				    
					if(reply.getStatus() == ClientResponse.Status.OK.getStatusCode())
					{
						Order receivedOrder = reply.getEntity(Order.class);
						
						if(receivedOrder.getStatus().equals(Order.STATUS_PLACED))
						{
							request.setAttribute("myOrder", receivedOrder);
							
							nextPage="updateOrder.jsp";
						} else {
							
							request.setAttribute("updateResult", "Order cannot be updated!");
							request.setAttribute("updateResultId", aId);
						}
					}
					else
					{
						System.out.println("Reply status not 200!");
						System.exit(1);
					}
				
				} else if(aAction.equals("payOrder")) {
					
					// Get parameters.
					String aId = request.getParameter("aId");
					
					// Create client with default configurations.
					ClientConfig defaultConfig = new DefaultClientConfig();
					Client myClient = new Client(new URLConnectionClientHandler(
					        new HttpURLConnectionFactory() {
					    @Override
					    public HttpURLConnection getHttpURLConnection(URL url)
					            throws IOException {
					        return (HttpURLConnection) url.openConnection(java.net.Proxy.NO_PROXY);
					    }
					}), defaultConfig);

					WebResource ordersResource = myClient.resource(myBaseURI);
					
				    // Specify request attributes and invoke 
					ClientResponse reply = ordersResource.
				    	path("orders").
				    	path(aId).
				    	accept(MediaType.WILDCARD_TYPE).
				        header("key", "customer").
				        get(ClientResponse.class);
				    
					if(reply.getStatus() == ClientResponse.Status.OK.getStatusCode())
					{
						Order receivedOrder = reply.getEntity(Order.class);
						
						if(receivedOrder.getStatus().equals(Order.STATUS_PLACED) || receivedOrder.getStatus().equals(Order.STATUS_PREPARING))
						{
							request.setAttribute("myOrder", receivedOrder);
							
							nextPage="payOrder.jsp";
						} else {
							
							request.setAttribute("payResult", "Order cannot be paid for!");
							request.setAttribute("payResultId", aId);
						}
					}
					else
					{
						System.out.println("Reply status not 200!");
						System.exit(1);
					}
				
				} else if(aAction.equals("getPayment")) {
					
					// Get parameters.
					String aId = request.getParameter("aId");
					
					// Create client with default configurations.
					ClientConfig defaultConfig = new DefaultClientConfig();
					Client myClient = new Client(new URLConnectionClientHandler(
					        new HttpURLConnectionFactory() {
					    @Override
					    public HttpURLConnection getHttpURLConnection(URL url)
					            throws IOException {
					        return (HttpURLConnection) url.openConnection(java.net.Proxy.NO_PROXY);
					    }
					}), defaultConfig);

					WebResource ordersResource = myClient.resource(myBaseURI);
					
				    // Specify request attributes and invoke 
					ClientResponse reply = ordersResource.
				    	path("payment").
				    	path(aId).
				    	accept(MediaType.WILDCARD_TYPE).
				        header("key", "customer").
				        get(ClientResponse.class);
				    
					if(reply.getStatus() == ClientResponse.Status.OK.getStatusCode())
					{
						Payment receivedPayment = reply.getEntity(Payment.class);
						
						request.setAttribute("myPayment", receivedPayment);
						
						nextPage = "paymentDetails.jsp";
					}
					else
					{
						System.out.println("Reply status not 200!");
						System.exit(1);
					}
					
					// Set up default landing page //
					this.setUpDefaultPage(request, myBaseURI);
					
				}
				
				// Set up default landing page //
				this.setUpDefaultPage(request, myBaseURI);
				
			}
			else
			{
				// Set up default landing page //
				this.setUpDefaultPage(request, myBaseURI);

			}
			
			// Dispatch.
			RequestDispatcher myRequestDispatcher = request.getRequestDispatcher("/"+nextPage);
			myRequestDispatcher.forward(request, response);
	}
	
	private void setUpDefaultPage(HttpServletRequest request, String myBaseURI)
	{
		// Create client with default configurations.
		ClientConfig defaultConfig = new DefaultClientConfig();
		Client myClient = new Client(new URLConnectionClientHandler(
		        new HttpURLConnectionFactory() {
		    @Override
		    public HttpURLConnection getHttpURLConnection(URL url)
		            throws IOException {
		        return (HttpURLConnection) url.openConnection(java.net.Proxy.NO_PROXY);
		    }
		}), defaultConfig);

		
		WebResource ordersResource = myClient.resource(myBaseURI);
		
	    // Specify request attributes and invoke 
		ClientResponse reply = ordersResource.
	    	path("orders").
	    	accept(MediaType.APPLICATION_XML_TYPE).
	        header("key", "customer").
	        get(ClientResponse.class);

		if(reply.getStatus() == ClientResponse.Status.OK.getStatusCode())
		{
			OrdersList myOrdersList = reply.getEntity(OrdersList.class);
			
			request.setAttribute("myOrdersList", myOrdersList.getOrdersList());
		}
		else
		{
			System.out.println("Reply status not 200!");
			System.exit(1);
		}
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			// Set web resource base uri.
//		String myBaseURI = request.getScheme()+"://"+request.getLocalAddr()+":"+request.getLocalPort()+"/cs9322.coffee.rest/rest";
//		String myBaseURI = "http://cjze477.srvr:8080/cs9322.coffee.rest/rest";
		String myBaseURI = "http://localhost:8080/cs9322.coffee.rest/rest";			
			// Get action.
			String aAction = request.getParameter("aAction");
				
			// Set default page.
			String nextPage = "index.jsp";
				
			if(aAction != null) {
				if(aAction.equals("createOrder"))
				{
					// Create Order //
					
					// Get form params
					String aDrink = request.getParameter("aDrink");
					String aAdditions = request.getParameter("aAdditions");
					
					ArrayList<String> additionsList = new ArrayList<String>();
					// Check if additions is empty
					if(!aAdditions.isEmpty())
					{
						int offset = aAdditions.indexOf(",");
						if(offset < 0)
						{
							// One entry.
							additionsList.add(aAdditions);
						}
						else
						{
							// Multiple entries.
							String[] part = aAdditions.split(",");
							for(String item : part)
							{
								additionsList.add(item.trim());
							}
						}
					}
					
					// Create client with default configurations.
					ClientConfig defaultConfig = new DefaultClientConfig();
					Client myClient = new Client(new URLConnectionClientHandler(
					        new HttpURLConnectionFactory() {
					    @Override
					    public HttpURLConnection getHttpURLConnection(URL url)
					            throws IOException {
					        return (HttpURLConnection) url.openConnection(java.net.Proxy.NO_PROXY);
					    }
					}), defaultConfig);

					
					WebResource ordersResource = myClient.resource(myBaseURI);
					
					// Create post body content.
					StringBuilder postBody = new StringBuilder();
					
					postBody.append("drink="+aDrink);
					for(String item : additionsList)
					{
						postBody.append("&additions="+item);
					}
					
				    // Specify request attributes and invoke 
					ClientResponse reply = ordersResource.
				    	path("orders").
				    	accept(MediaType.APPLICATION_XML_TYPE).
				        header("key", "customer").
				        type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).
				        post(ClientResponse.class, postBody.toString());
				    	
					if(reply.getStatus() == ClientResponse.Status.CREATED.getStatusCode()) 
					{
						request.setAttribute("orderCreated", 1);
						
						// Set up default landing page //
						this.setUpDefaultPage(request, myBaseURI);
					}
					else
					{
						System.out.println("Reply status not 201!");
						System.exit(1);
					}
					
					
				} else if(aAction.equals("updateOrder")) {
					
					// Update Order //
					
					// Get form params
					String aDrink = request.getParameter("aDrink");
					String aAdditions = request.getParameter("aAdditions");
					int aOrderId = Integer.parseInt(request.getParameter("aOrderId"));
					
					ArrayList<String> additionsList = new ArrayList<String>();
					// Check if additions is empty
					if(!aAdditions.isEmpty())
					{
						int offset = aAdditions.indexOf(",");
						if(offset < 0)
						{
							// One entry.
							additionsList.add(aAdditions);
						}
						else
						{
							// Multiple entries.
							String[] part = aAdditions.split(",");
							for(String item : part)
							{
								additionsList.add(item.trim());
							}
						}
					}
					
					// Create client with default configurations.
					ClientConfig defaultConfig = new DefaultClientConfig();
					Client myClient = new Client(new URLConnectionClientHandler(
					        new HttpURLConnectionFactory() {
					    @Override
					    public HttpURLConnection getHttpURLConnection(URL url)
					            throws IOException {
					        return (HttpURLConnection) url.openConnection(java.net.Proxy.NO_PROXY);
					    }
					}), defaultConfig);
	
					WebResource ordersResource = myClient.resource(myBaseURI);
					
					// Create put body content.
					Order sendingOrder = new Order(aOrderId, aDrink, additionsList, -1.0, Order.STATUS_PLACED, Order.UNPAID);
					
				    // Specify request attributes and invoke 
					ClientResponse reply = ordersResource.
				    	path("orders").
				    	path(""+aOrderId).
				    	accept(MediaType.WILDCARD_TYPE).
				        header("key", "customer").
				        type(MediaType.APPLICATION_XML_TYPE).
				        put(ClientResponse.class, sendingOrder);
				    	
					if(reply.getStatus() == ClientResponse.Status.OK.getStatusCode()) 
					{
						request.setAttribute("orderUpdated", 1);
						
						// Set up default landing page //
						this.setUpDefaultPage(request, myBaseURI);
					}
					else
					{
						System.out.println("Reply status not 200! : "+reply.getStatus()+": "+reply.getStatusInfo().getReasonPhrase());
						System.exit(1);
					}
					
					
				} else if(aAction.equals("payOrder")) {
					
					// Update Order //
					
					// Get form params
					String aType = request.getParameter("aType");
					String aCardName = request.getParameter("aCardName");
					String aCardNumber = request.getParameter("aCardNumber");
					String aCardCVC = request.getParameter("aCardCVC");
					int aOrderId = Integer.parseInt(request.getParameter("aOrderId"));
					
					// Create client with default configurations.
					ClientConfig defaultConfig = new DefaultClientConfig();
					Client myClient = new Client(new URLConnectionClientHandler(
					        new HttpURLConnectionFactory() {
					    @Override
					    public HttpURLConnection getHttpURLConnection(URL url)
					            throws IOException {
					        return (HttpURLConnection) url.openConnection(java.net.Proxy.NO_PROXY);
					    }
					}), defaultConfig);

					WebResource ordersResource = myClient.resource(myBaseURI);
					
					// Create put body content.
					Payment sendingPayment = new Payment(aOrderId, aType, -1.0, aCardNumber, aCardName, aCardCVC);
					
				    // Specify request attributes and invoke 
					ClientResponse reply = ordersResource.
				    	path("payment").
				    	path(""+aOrderId).
				    	accept(MediaType.WILDCARD_TYPE).
				        header("key", "customer").
				        type(MediaType.APPLICATION_XML_TYPE).
				        put(ClientResponse.class, sendingPayment);
				    	
					if(reply.getStatus() == ClientResponse.Status.CREATED.getStatusCode()) 
					{
						request.setAttribute("orderPaid", 1);
						
						// Set up default landing page //
						this.setUpDefaultPage(request, myBaseURI);
					}
					else
					{
						System.out.println("Reply status not 201! : "+reply.getStatus()+": "+reply.getStatusInfo().getReasonPhrase());
						System.exit(1);
					}
					
					
				}
			}
			
			// Dispatch.
			RequestDispatcher myRequestDispatcher = request.getRequestDispatcher("/"+nextPage);
			myRequestDispatcher.forward(request, response);
	}

}
