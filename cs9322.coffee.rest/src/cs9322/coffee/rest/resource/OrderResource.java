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
		Order o = DatabaseDAO.instance.getOrder(id, uriInfo);
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
		if(DatabaseDAO.instance.validOrder(newb.getId())) {
			
			// Can only update order if status is placed.
			if(newb.getStatus().equals(Order.STATUS_PLACED)) {
				DatabaseDAO.instance.updateOrder(newb.getId(), newb);
				newb = DatabaseDAO.instance.getOrder(newb.getId(), uriInfo);
				res = Response.ok(newb).build();
			}
			else
			{
				return Response.status(Response.Status.PRECONDITION_FAILED).build();
			}
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return res;
	}
	
	@DELETE
	public Response deleteOrder() {
		
		// Check if order exists.
		if(DatabaseDAO.instance.validOrder(id))
		{
			Order aOrder = DatabaseDAO.instance.getOrder(id, uriInfo);
			
			// Check that order can be deleted.
			if(aOrder.getStatus().equals(Order.STATUS_PLACED)) {
				aOrder.setStatus(Order.STATUS_CANCELLED);
				DatabaseDAO.instance.updateOrder(id, aOrder);
				
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