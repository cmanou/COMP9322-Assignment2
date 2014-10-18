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
		Order o = DatabaseDAO.instance.getOrder(id, uriInfo);
		if(o != null) {
			return Response.ok(o).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response putOrder(Order o) {
		
		Response res;
		
		// Get corresponding order if exists.
		Order dbOrder = DatabaseDAO.instance.getOrder(this.id, uriInfo);
		if(dbOrder != null) {
	
			if(dbOrder.getStatus().equals(Order.STATUS_PLACED)
					/* && clientKey? */) {			
				// Safety value manipulation.
				o.calculateCost();
				o.setStatus(Order.STATUS_PLACED);
			} else if(dbOrder.getStatus().equals(Order.STATUS_PLACED) 
					&& o.getStatus().equals(Order.STATUS_PREPARING)
					/* && baristaKey? */) {			
				o.setAdditions(dbOrder.getAdditions());
				o.setDrink(dbOrder.getDrink());
				o.calculateCost();
			} else if(dbOrder.getStatus().equals(Order.STATUS_PREPARING) 
					&& o.getPaymentStatus().equals(Order.PAID)
					&& o.getStatus().equals(Order.STATUS_SERVED)
					/* && baristaKey? */) {			
				o.setAdditions(dbOrder.getAdditions());
				o.setDrink(dbOrder.getDrink());
				o.calculateCost();
			} else {
				return Response.status(Response.Status.PRECONDITION_FAILED).build();
			}
			o.setId(this.id);
	
			DatabaseDAO.instance.updateOrder(this.id, o);
			o = DatabaseDAO.instance.getOrder(this.id, uriInfo);
			res = Response.ok(o).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return res;
	}
	
	@DELETE
	public Response deleteOrder() {
		
		// Check if order exists.
		Order aOrder = DatabaseDAO.instance.getOrder(this.id, uriInfo);
		if(aOrder != null)
		{
			// Check that order can be deleted.
			if(aOrder.getStatus().equals(Order.STATUS_PLACED)) {
				aOrder.setStatus(Order.STATUS_CANCELLED);
				DatabaseDAO.instance.updateOrder(this.id, aOrder);
				
				return Response.ok().build();
			} else {
				return Response.status(Response.Status.PRECONDITION_FAILED).build();
			}
		}
		else
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@OPTIONS
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getOptions() {
		
		// Check if order exists.
		if(DatabaseDAO.instance.validOrder(id))
		{
			Order aOrder = DatabaseDAO.instance.getOrder(id, uriInfo);
			
			return Response.ok().header("Allow", aOrder.getAvaliableOptions()).build();
		}
		else
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	


}