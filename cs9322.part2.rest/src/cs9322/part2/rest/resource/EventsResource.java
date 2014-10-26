package cs9322.part2.rest.resource;

import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


// will map xxx.xxx.xxx/events
@Path("/events")
public class EventsResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	
	@Context
	Request request;
	
	@Context
	HttpHeaders requestHeaders;
	
	@Context
	ServletContext servletContext;
	
	// Important to note that this Path annotation define.
	// This will match xxx.xxx.xxx/rest/books/{book}
	// It says 'the thing that comes after books/ is a parameter
	// and it is passed to the BookResource class for processing
	// e.g., http://localhost:8080/cs9322.simple.rest.books/rest/books/3
        // This matches this method which returns BookResource.
	@Path("{eventSetId}")
	public EventResource getEvent(
			@PathParam("eventSetId") String aEventSetId) {
		return new EventResource(this.uriInfo, this.request, this.servletContext, aEventSetId, this.requestHeaders);
	}
	
}
