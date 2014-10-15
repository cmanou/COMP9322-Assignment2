package cs9322.coffee.rest.resource;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import cs9322.coffee.rest.dao.*;
import cs9322.coffee.rest.models.*;


// will map xxx.xxx.xxx/rest/orders
@Path("/orders")
public class OrdersResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// Return the list of orders for client applications/programs
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Order> getOrders() {
		Collection<Order> orders = OrdersDAO.instance.getOrders(uriInfo).values();
		return orders; 
	}
	

	
    // Client should set Content Type accordingly
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Order newOrder(
			@FormParam("drink") String drink,
			@FormParam("additions") List<String> additions,
			@Context HttpServletResponse servletResponse
	) throws IOException {
		Order o = new Order();
		o.setDrink(drink);
		o.setAdditions(additions);
		o.calculateCost();
		o.setStatus(Order.STATUS_PLACED);

		int id = OrdersDAO.instance.insertOrder(o);	
		o.setId(id);
		return o;
	}
	
	@OPTIONS
	@Produces({MediaType.APPLICATION_XML})
	public String getOptionsXML() {
		
		StringBuilder myStringBuilder = new StringBuilder();
		myStringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>");
		myStringBuilder.append("<options>");
		myStringBuilder.append("<option>GET</option>");
		myStringBuilder.append("<option>POST</option>");
		myStringBuilder.append("</options>");
		
		return myStringBuilder.toString(); 
	}
	
	@OPTIONS
	@Produces({MediaType.APPLICATION_JSON})
	public String getOptionsJSON() {
		
		StringBuilder myStringBuilder = new StringBuilder();
		myStringBuilder.append("{");
		myStringBuilder.append("\"options\":[");
		myStringBuilder.append("{\"option\":\"GET\"},");
		myStringBuilder.append("{\"option\":\"POST\"}");
		myStringBuilder.append("]");
		myStringBuilder.append("}");
				
		return myStringBuilder.toString(); 
	}
	
	// Important to note that this Path annotation define.
	// This will match xxx.xxx.xxx/rest/books/{book}
	// It says 'the thing that comes after books/ is a parameter
	// and it is passed to the BookResource class for processing
	// e.g., http://localhost:8080/cs9322.simple.rest.books/rest/books/3
        // This matches this method which returns BookResource.
	@Path("{order}")
	public OrderResource getOrder(
			@PathParam("order") int id) {
		return new OrderResource(uriInfo, request, id);
	}
	
}