package cs9322.coffee.rest.resource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
	
	@Context
	HttpHeaders requestHeaders;
	
	private boolean isAuthorizedCustomer = false;
	private boolean isAuthorizedBarista = false;
	
	// Return the list of orders for client applications/programs
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getOrders() {
		
		this.checkClient();
		if(!this.isAuthorizedBarista && !this.isAuthorizedCustomer)
		{	
			// Do not have authorization.
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		OrdersList myList = DatabaseDAO.instance.getOrders(uriInfo);
		return Response.ok(myList).build(); 
	}
	
    // Client should set Content Type accordingly
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newOrder(
			@FormParam("drink") String drink,
			@FormParam("additions") List<String> additions,
			@Context HttpServletResponse servletResponse
	) throws IOException, URISyntaxException {
		
		this.checkClient();
		if(!this.isAuthorizedBarista && !this.isAuthorizedCustomer)
		{	
			// Do not have authorization.
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		if(drink != null){
			Order o = new Order();
			o.setDrink(drink);
			o.setAdditions(additions);
			o.calculateCost();
			o.setStatus(Order.STATUS_PLACED);
			o.setPaymentStatus(Order.UNPAID);
	
			int id = DatabaseDAO.instance.insertOrder(o);	
			o.setId(id);
			o.generateLinks(uriInfo);
			
			URI uri = new URI(o.getLinks().get(0).getHref());
			
			return Response.ok(o).location(uri).status(Response.Status.CREATED).type(MediaType.APPLICATION_XML_TYPE).build();
		}
		else
		{
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}
	
	@OPTIONS
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getOptions() {
		
		this.checkClient();
		if(!this.isAuthorizedBarista && !this.isAuthorizedCustomer)
		{	
			// Do not have authorization.
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		return Response.ok().header("Allow", "GET, POST").build(); 
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
		return new OrderResource(uriInfo, request, id, this.requestHeaders);
	}
	
	private void checkClient()
	{
		List<String> myList = this.requestHeaders.getRequestHeader("key");
		
		if(myList != null)
		{
			// Key has been found, check its number.
			if(myList.get(0).equals("barista")) {
				this.isAuthorizedBarista = true;
			} else if(myList.get(0).equals("customer")) {
				this.isAuthorizedCustomer = true;
			}
		}
	}
	
	
}
