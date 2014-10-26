package cs9322.part2.rest.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;


public class EventResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@Context
	ServletContext servletContext;
	
	private String eventSetId;
	
	@Context
	HttpHeaders requestHeaders;
	
	public EventResource(UriInfo uriInfo, Request request, ServletContext aServletContext, String aEventSetId, HttpHeaders aHeaders) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.eventSetId = aEventSetId;
		this.requestHeaders = aHeaders;
		this.servletContext = aServletContext;
	}
			
	@GET
	@Path("/xml")
	@Produces(MediaType.APPLICATION_XML)
	public Response getEventXML() {
		

		// Check to see if app variable exists
		List<String> myEventSetIdList = (List<String>) this.servletContext.getAttribute("eventSetIdList");	
		
		if(myEventSetIdList == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		// Check if event id already exists in list.
		boolean found = false;
		for(String item : myEventSetIdList)
		{
			if(item.equals(this.eventSetId))
			{
				found = true;
			}
		}
		
		if(found)
		{
			String result = this.generateData("csv.xsl");
			
			if(result.equals("ERROR"))
			{
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
			
			return Response.ok(result).build();
		} 
		else
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}

	}
	
	@GET
	@Path("/trade/xml")
	@Produces(MediaType.APPLICATION_XML)
	public Response getEventTradeXML() {
		
		// Check to see if app variable exists
		List<String> myEventSetIdList = (List<String>) this.servletContext.getAttribute("eventSetIdList");
		
		if(myEventSetIdList == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		// Check if event id already exists in list.
		boolean found = false;
		for(String item : myEventSetIdList)
		{
			if(item.equals(this.eventSetId))
			{
				found = true;
			}
		}
		
		if(found)
		{
			String result = this.generateData("csv-trades.xsl");
			
			if(result.equals("ERROR"))
			{
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
			
			return Response.ok(result).build();
		} 
		else
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}

	}
	
	@GET
	@Path("/trade/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEventTradeJSON() {
		
		// Check to see if app variable exists
		List<String> myEventSetIdList = (List<String>) this.servletContext.getAttribute("eventSetIdList");
		
		if(myEventSetIdList == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		// Check if event id already exists in list.
		boolean found = false;
		for(String item : myEventSetIdList)
		{
			if(item.equals(this.eventSetId))
			{
				found = true;
			}
		}
		
		if(found)
		{
			String result = this.generateData("csv-json-trades.xsl");
			
			if(result.equals("ERROR"))
			{
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
			
			return Response.ok(result).build();
		} 
		else
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}

	}
	
	@GET
	@Path("/quote/xml")
	@Produces(MediaType.APPLICATION_XML)
	public Response getEventQuoteXML() {
		
		// Check to see if app variable exists
		List<String> myEventSetIdList = (List<String>) this.servletContext.getAttribute("eventSetIdList");
		
		if(myEventSetIdList == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		// Check if event id already exists in list.
		boolean found = false;
		for(String item : myEventSetIdList)
		{
			if(item.equals(this.eventSetId))
			{
				found = true;
			}
		}
		
		if(found)
		{
			String result = this.generateData("csv-quotes.xsl");
			
			if(result.equals("ERROR"))
			{
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
			
			return Response.ok(result).build();
		} 
		else
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}

	}
	
	@GET
	@Path("/quote/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEventQuoteJSON() {
		
		// Check to see if app variable exists
		List<String> myEventSetIdList = (List<String>) this.servletContext.getAttribute("eventSetIdList");
		
		if(myEventSetIdList == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		// Check if event id already exists in list.
		boolean found = false;
		for(String item : myEventSetIdList)
		{
			if(item.equals(this.eventSetId))
			{
				found = true;
			}
		}
		
		if(found)
		{
			String result = this.generateData("csv-json-quotes.xsl");
			
			if(result.equals("ERROR"))
			{
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
			
			return Response.ok(result).build();
		} 
		else
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}

	}
	
	@GET
	@Path("/trade/totalprice")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getEventTradeTotalPrice() {
		
		// Check to see if app variable exists
		List<String> myEventSetIdList = (List<String>) this.servletContext.getAttribute("eventSetIdList");
		
		if(myEventSetIdList == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		// Check if event id already exists in list.
		boolean found = false;
		for(String item : myEventSetIdList)
		{
			if(item.equals(this.eventSetId))
			{
				found = true;
			}
		}
		
		if(found)
		{
			String result = this.generateData("csv.xsl");
			
			if(result.equals("ERROR"))
			{
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
			
			return Response.ok(result).build();
		} 
		else
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}

	}


	
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	public Response putEvent() {
		
		// Check to see if app variable exists, if not create it.
		List<String> myEventSetIdList = (List<String>) this.servletContext.getAttribute("eventSetIdList");
		
		if(myEventSetIdList == null){
			myEventSetIdList = new ArrayList<String>();
		}
		
		// Check if event id already exists in list.
		boolean found = false;
		for(String item : myEventSetIdList)
		{
			if(item.equals(this.eventSetId))
			{
				found = true;
			}
		}
		
		if(found)
		{
			return Response.status(Response.Status.OK).build();
		} 
		else
		{
			// Store id and create XML to send back //
			myEventSetIdList.add(eventSetId);
			

			String result = this.generateData("csv.xsl");

			// Download csv and convert to XML
			if(result.equals("ERROR"))
			{
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
			
			// Save List to context.
			this.servletContext.setAttribute("eventSetIdList", myEventSetIdList);
			
			return Response.ok(result).status(Response.Status.CREATED).build();
		}
		
	}
	
	private String generateData(String aXSLTFileName)
	{
		// Download csv and convert 
		try 
		{				
			URL myURL = new URL("http://vcas720.srvr.cse.unsw.edu.au/"+this.eventSetId+".csv");
			File myCSVFile = File.createTempFile("myTempCSV", ".csv"); 
			
			String fullPath = servletContext.getRealPath("/WEB-INF/xslts/"+aXSLTFileName);
			
			File myXSLT = new File(fullPath);
			
			
			FileUtils.copyURLToFile(myURL, myCSVFile);
			
			TransformerFactory factory = new net.sf.saxon.TransformerFactoryImpl();
	        Source xslt = new StreamSource(myXSLT);
	        Transformer transformer = factory.newTransformer(xslt);
	        
	        transformer.setParameter("pathToCSV", myCSVFile.getAbsolutePath());

	        Source text = new StreamSource(new File(servletContext.getRealPath("/WEB-INF/xslts/dummy.xml")));

	        StringWriter writer = new StringWriter();
	        StreamResult result = new StreamResult(writer);
	        
	        transformer.transform(text, result);
	    
			return writer.toString();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return "ERROR";
	}
	
}
