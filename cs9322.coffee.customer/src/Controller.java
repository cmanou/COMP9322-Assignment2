

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

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
			String myBaseURI = request.getScheme()+"://"+request.getLocalAddr()+":"+request.getLocalPort()+"/cs9322.coffee.rest/rest";
									
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
						}
					}
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
		Client myClient = Client.create(defaultConfig);
		
		WebResource ordersResource = myClient.resource(myBaseURI);
		
	    // Specify request attributes and invoke 
		ClientResponse reply = ordersResource.
	    	path("orders").
	    	accept(MediaType.APPLICATION_XML_TYPE).
	        header("Key", "password1").
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
			String myBaseURI = request.getScheme()+"://"+request.getLocalAddr()+":"+request.getLocalPort()+"/cs9322.coffee.rest/rest";
			
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
					Client myClient = Client.create(defaultConfig);
					
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
				        header("Key", "password1").
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
					
					
				}
			}
			
			// Dispatch.
			RequestDispatcher myRequestDispatcher = request.getRequestDispatcher("/"+nextPage);
			myRequestDispatcher.forward(request, response);
	}

}
