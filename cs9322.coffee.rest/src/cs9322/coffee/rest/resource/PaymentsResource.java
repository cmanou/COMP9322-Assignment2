package cs9322.coffee.rest.resource;

import java.util.Collection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import cs9322.coffee.rest.dao.*;
import cs9322.coffee.rest.models.*;


// will map xxx.xxx.xxx/rest/orders
@Path("/payment")
public class PaymentsResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// Return the list of orders for client applications/programs
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getOrders() {
		PaymentList myList = DatabaseDAO.instance.getPayments(uriInfo);
		return Response.ok(myList).build(); 
	}
	
	@OPTIONS
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getOptions() {
		return Response.ok().header("Allow", "GET").build(); 
	}
	
	@Path("{payment}")
	public PaymentResource getPayment(
			@PathParam("payment") int id) {
		return new PaymentResource(uriInfo, request, id);
	}
	
}