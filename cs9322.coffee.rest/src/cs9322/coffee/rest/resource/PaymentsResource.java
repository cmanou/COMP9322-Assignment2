package cs9322.coffee.rest.resource;

import java.util.List;

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
		
		PaymentList myList = DatabaseDAO.instance.getPayments(uriInfo);
		return Response.ok(myList).build(); 
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
		
		return Response.ok().header("Allow", "GET").build(); 
	}
	
	@Path("{payment}")
	public PaymentResource getPayment(
			@PathParam("payment") int id) {
		return new PaymentResource(uriInfo, request, id, this.requestHeaders);
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