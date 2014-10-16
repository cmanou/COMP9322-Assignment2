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
import javax.xml.bind.JAXBElement;

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
	public Response putPayment(JAXBElement<Payment> p) throws URISyntaxException {
		
		// Check if payment should exist.
		Order relatedOrder = DatabaseDAO.instance.getOrder(this.id, uriInfo);
		if(relatedOrder != null) {
			
			// Check that payment record doesnt exist .i.e. payment hasnt been made.
			boolean exists = DatabaseDAO.instance.paymentExits(this.id);
			if(!exists) {
				
				Payment putP = p.getValue();
				
				// Make sure same fields have same values and Insert payment.
				putP.setAmount(relatedOrder.getCost());
				putP.setId(relatedOrder.getId());
				DatabaseDAO.instance.insertPayment(putP);
				
				// Update order status.
				relatedOrder.setStatus(Order.STATUS_PAID);
				DatabaseDAO.instance.updateOrder(this.id, relatedOrder);
				
				// Get links.
				Payment pRec = DatabaseDAO.instance.getPayment(this.id, uriInfo);
				pRec.generateLinks(uriInfo);
				URI uri = new URI(pRec.getLinks().get(0).getHref());
				
				return Response.ok(pRec).location(uri).status(Response.Status.CREATED).build();
			} else {
				return Response.status(Response.Status.PRECONDITION_FAILED).build();
			}
			
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	
	@OPTIONS
	public Response optionsOrder() {
		
		// Check if order exists.
		if(DatabaseDAO.instance.validOrder(id))
		{
			//Payment aPayment = PaymentsDAO.instance.getPayment(id, uriInfo);
			Order aOrder = DatabaseDAO.instance.getOrder(id, uriInfo);
			
			StringBuilder myStringBuilder = new StringBuilder();
			
			if(aOrder.getStatus().equals(Order.STATUS_PLACED)) {
				myStringBuilder.append("PUT");
			} else if(aOrder.getStatus().equals(Order.STATUS_PAID)) {
				myStringBuilder.append("GET");
			} else if(aOrder.getStatus().equals(Order.STATUS_PREPARING)) {
				myStringBuilder.append("GET");
			} else if(aOrder.getStatus().equals(Order.STATUS_SERVED)) {
				myStringBuilder.append("GET");
			}
			
			return Response.ok().header("Allow", myStringBuilder.toString()).build();
		}
		else
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	
}