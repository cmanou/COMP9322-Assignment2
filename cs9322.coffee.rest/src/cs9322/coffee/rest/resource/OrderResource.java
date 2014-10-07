package cs9322.coffee.rest.resource;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import cs9322.coffee.rest.dao.*;
import cs9322.coffee.rest.models.*;

//TODO: Proper errors

public class OrderResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	public OrderResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	// Produces XML or JSON output for a client 'program'			
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Order getOrder() {
		Order o = OrdersDAO.instance.getOrder(id);
		if(o==null)
			throw new RuntimeException("GET: Order with" + id +  " not found");
		return o;
	}
	
	//TODO: MAYBE allow json
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putOrder(JAXBElement<Order> o) {
		Order newb = o.getValue();
		return putAndGetResponse(newb);
	}
	
	@DELETE
	public void deleteOrder() {
		boolean delb = OrdersDAO.instance.removeOrder(id);
		if(!delb)
			throw new RuntimeException("DELETE: Order with " + id +  " not found or could not delete");
	}
	
	
	@OPTIONS
	public void optionsOrder() {
		Order o = OrdersDAO.instance.getOrder(id);
		// TODO: Get it to return something meaningful? Probably need status
	}
	
	//TODO: DO this properly
	private Response putAndGetResponse(Order b) {
		Response res;
		if(OrdersDAO.instance.validOrder(b.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		OrdersDAO.instance.updateOrder(b.getId(), b);
		return res;
	}
}