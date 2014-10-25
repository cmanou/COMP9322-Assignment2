package cs9322.coffee.rest.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
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
	private int id;
	
	@Context
	HttpHeaders requestHeaders;
	
	private boolean isAuthorizedCustomer = false;
	private boolean isAuthorizedBarista = false;
	
	public OrderResource(UriInfo uriInfo, Request request, int id, HttpHeaders aHeaders) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
		this.requestHeaders = aHeaders;
	}
	
	// Produces XML or JSON output for a client 'program'			
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getOrder() {
		
		this.checkClient();
		if(!this.isAuthorizedBarista && !this.isAuthorizedCustomer)
		{	
			// Do not have authorization.
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
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
		
		this.checkClient();
		if(!this.isAuthorizedBarista && !this.isAuthorizedCustomer)
		{	
			// Do not have authorization.
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		Response res;
		
		// Get corresponding order if exists.
		Order dbOrder = DatabaseDAO.instance.getOrder(this.id, uriInfo);
		if(dbOrder != null) {
			
			if(dbOrder.getStatus().equals(Order.STATUS_PLACED) && this.isAuthorizedCustomer) {		
				// Safety value manipulation.
				o.calculateCost();
				o.setStatus(Order.STATUS_PLACED);
			} else if(dbOrder.getStatus().equals(Order.STATUS_PLACED) 
					&& o.getStatus().equals(Order.STATUS_PREPARING) 
					&& this.isAuthorizedBarista) {		
				o.setAdditions(dbOrder.getAdditions());
				o.setDrink(dbOrder.getDrink());
				o.calculateCost();
			} else if(dbOrder.getStatus().equals(Order.STATUS_PREPARING) 
					&& o.getPaymentStatus().equals(Order.PAID)
					&& o.getStatus().equals(Order.STATUS_SERVED)
					&& this.isAuthorizedBarista) {			
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
		
		this.checkClient();
		if(!this.isAuthorizedCustomer)
		{	
			// Do not have authorization.
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		// Check if order exists.
		Order aOrder = DatabaseDAO.instance.getOrder(this.id, uriInfo);
		if(aOrder != null)
		{
			// Check that order can be deleted.
			if(aOrder.getStatus().equals(Order.STATUS_PLACED) || aOrder.getStatus().equals(Order.STATUS_PREPARING)) {
				
				// Delete payment if exists.
				if(aOrder.getPaymentStatus().equals(Order.PAID))
				{
					DatabaseDAO.instance.removePayment(this.id);
				}
				
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
		
		this.checkClient();
		if(!this.isAuthorizedBarista && !this.isAuthorizedCustomer)
		{	
			// Do not have authorization.
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		// Check if order exists.
		if(DatabaseDAO.instance.validOrder(id))
		{
			Order aOrder = DatabaseDAO.instance.getOrder(id, uriInfo);
			
			return Response.ok().header("Allow", aOrder.getAvaliableOptions(this.isAuthorizedBarista, this.isAuthorizedCustomer)).build();
		}
		else
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}
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