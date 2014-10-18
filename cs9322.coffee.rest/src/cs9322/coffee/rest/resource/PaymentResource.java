package cs9322.coffee.rest.resource;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
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


public class PaymentResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int id;
	public PaymentResource(UriInfo uriInfo, Request request, int id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	// Produces XML or JSON output for a client 'program'			
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getPayment() {
		Payment p = DatabaseDAO.instance.getPayment(id, uriInfo);
		if(p != null) {
			return Response.ok(p).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putPayment(Payment putP) throws URISyntaxException {
		
		// Check if payment should exist.
		Order relatedOrder = DatabaseDAO.instance.getOrder(this.id, uriInfo);
		if(relatedOrder != null 
				&& (relatedOrder.getStatus().equals(Order.STATUS_CANCELLED) 
						|| relatedOrder.getStatus().equals(Order.STATUS_SERVED))) {
			
			// Check that payment record doesnt exist .i.e. payment hasnt been made.
			boolean exists = DatabaseDAO.instance.paymentExits(this.id);
			if(!exists) {
				

				// Make sure same fields have same values and Insert payment.
				putP.setAmount(relatedOrder.getCost());
				putP.setId(relatedOrder.getId());
				DatabaseDAO.instance.insertPayment(putP);
				
				// Update order status.
				relatedOrder.setPaymentStatus(Order.PAID);
				DatabaseDAO.instance.updateOrder(this.id, relatedOrder);
				
				// Get links.
				Payment pRec = DatabaseDAO.instance.getPayment(this.id, uriInfo);
				pRec.generateLinks(uriInfo);
				URI uri = new URI(pRec.getLinks().get(0).getHref());
				
				return Response.ok(pRec).location(uri).status(Response.Status.CREATED).build();
			} else {
				// Make sure same fields have same values and Insert payment.
				putP.setAmount(relatedOrder.getCost());
				putP.setId(relatedOrder.getId());
				DatabaseDAO.instance.updatePayment(putP);
				
				// Get links.
				Payment pRec = DatabaseDAO.instance.getPayment(this.id, uriInfo);
				pRec.generateLinks(uriInfo);
				URI uri = new URI(pRec.getLinks().get(0).getHref());
				
				return Response.ok(pRec).location(uri).status(Response.Status.CREATED).build();
			}
			
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	
	@OPTIONS
	public Response optionsPayment() {
		
		// Check if order exists.
		if(DatabaseDAO.instance.validOrder(id))
		{
			//Payment aPayment = PaymentsDAO.instance.getPayment(id, uriInfo);
			Order aOrder = DatabaseDAO.instance.getOrder(id, uriInfo);
			
			StringBuilder myStringBuilder = new StringBuilder();
			
			if(aOrder.getStatus().equals(Order.STATUS_PLACED)) {
				myStringBuilder.append("GET ");
				myStringBuilder.append("PUT ");
			} else if(aOrder.getStatus().equals(Order.STATUS_PREPARING)) {
				myStringBuilder.append("GET ");
				myStringBuilder.append("PUT ");
			} else if(aOrder.getStatus().equals(Order.STATUS_SERVED)) {
				myStringBuilder.append("GET ");
			}
			
			return Response.ok().header("Allow", myStringBuilder.toString()).build();
		}
		else
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	
}