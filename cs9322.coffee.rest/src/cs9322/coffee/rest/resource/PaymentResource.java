package cs9322.coffee.rest.resource;

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
		Payment p = PaymentsDAO.instance.getPayment(id);
		if(p != null) {
			return Response.ok(p).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putOrder(JAXBElement<Payment> p) {
		Payment putP = p.getValue();

		if(PaymentsDAO.instance.paymentExits(putP.getId())) {
			PaymentsDAO.instance.updatePayment(putP);
		} else {
			PaymentsDAO.instance.insertPayment(putP);
		}
		
		putP = PaymentsDAO.instance.getPayment(putP.getId());
		return Response.ok(putP).build();

	}
	
	/*
	@OPTIONS
	public Response optionsOrder() {
		Payment p = PaymentsDAO.instance.getPayment(id);
		if(p != null) {
			return Response.ok().header("Allow", p.getAvaliableOptions()).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	*/
	
}