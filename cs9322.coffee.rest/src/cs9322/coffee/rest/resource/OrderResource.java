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


public class OrderResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int id;
	public OrderResource(UriInfo uriInfo, Request request, int id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	// Produces XML or JSON output for a client 'program'			
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getOrder() {
		Order o = OrdersDAO.instance.getOrder(id, uriInfo);
		if(o != null) {
			return Response.ok(o).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putOrder(JAXBElement<Order> o) {
		Order newb = o.getValue();
		newb.calculateCost();
		Response res;
		if(OrdersDAO.instance.validOrder(newb.getId())) {
			OrdersDAO.instance.updateOrder(newb.getId(), newb);
			newb = OrdersDAO.instance.getOrder(newb.getId(), uriInfo);
			res = Response.ok(newb).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return res;
	}
	
	@DELETE
	public Response deleteOrder() {
		//TODO: Should change status not delete;
		boolean delb = OrdersDAO.instance.removeOrder(id);
		if(delb) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	
	@OPTIONS
	@Produces({MediaType.APPLICATION_XML})
	public String optionsOrder() {
		
		// Check if order exists.
		Order o = OrdersDAO.instance.getOrder(id, uriInfo);
		
		StringBuilder myStringBuilder = new StringBuilder();
		myStringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>");
		myStringBuilder.append("<options>");
		
		if(o != null) {
			
			// Can get at anytime.
			myStringBuilder.append("<option>GET</option>");
			
			// Can amend order only when it has just been placed.
			if(o.getStatus().equals(Order.STATUS_PLACED)) {
				myStringBuilder.append("<option>PUT</option>");
				myStringBuilder.append("<option>DELETE</option>");
			}
		} 
		myStringBuilder.append("</options>");
		
		return myStringBuilder.toString();
	}

}