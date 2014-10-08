package cs9322.coffee.rest.resource;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
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
	public List<Order> getOrders() {
		List<Order> orders = new ArrayList<Order>();
		//TODO: Actually get from DB/DAO
		return orders; 
	}
	

	
    // Client should set Content Type accordingly
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Order newOrder(
			@FormParam("title") String title,
			@FormParam("detail") String detail,
			@Context HttpServletResponse servletResponse
	) throws IOException {
		//TODO: Actually populate the data in order from form?
		Order o = new Order();
		
		//Also calculate Cost?

		int id = OrdersDAO.instance.insertOrder(o);	
		o.setId(id);
		return o;
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