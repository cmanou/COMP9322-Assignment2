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
	
	// Produces XML or JSON output for a client 'program'			
	@GET
	//@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getEvent() {
		
		try 
		{				
			URL myURL = new URL("http://vcas720.srvr.cse.unsw.edu.au/"+eventSetId+".csv");
			File myCSVFile = File.createTempFile("myTempCSV", ".csv"); 
			FileUtils.copyURLToFile(myURL, myCSVFile);

			String fullPath = servletContext.getRealPath("/WEB-INF/xslts/csv-json.xsl");

			StringWriter writer = csvXSLTConvert(myCSVFile, fullPath);
	    
			// Save List to context.
			//this.servletContext.setAttribute("eventSetIdList", myEventSetIdList);
			

			return Response.ok(writer.toString()).build();
		
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
		
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		} 
		else
		{
			// Store id and create XML to send back //
			//myEventSetIdList.add(eventSetId);
			
			// Download csv and convert to XML
			try 
			{				
				URL myURL = new URL("http://vcas720.srvr.cse.unsw.edu.au/"+eventSetId+".csv");
				File myCSVFile = File.createTempFile("myTempCSV", ".csv"); 
				FileUtils.copyURLToFile(myURL, myCSVFile);
				
				String fullPath = servletContext.getRealPath("/WEB-INF/xslts/csv.xsl");
				
				StringWriter writer = csvXSLTConvert(myCSVFile, fullPath);
		    
				// Save List to context.
				//this.servletContext.setAttribute("eventSetIdList", myEventSetIdList);
				

				return Response.ok(writer.toString()).build();
			
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
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	private StringWriter csvXSLTConvert(File myCSVFile, String xsltPath) throws IOException, TransformerConfigurationException, TransformerException {
		File myXSLT = new File(xsltPath);
		
		System.out.println("here: "+myXSLT.getAbsolutePath());
		
		TransformerFactory factory = new net.sf.saxon.TransformerFactoryImpl();
		Source xslt = new StreamSource(myXSLT);
		Transformer transformer = factory.newTransformer(xslt);
		System.out.println("here: "+myCSVFile.getAbsolutePath());
		transformer.setParameter("pathToCSV", myCSVFile.getAbsolutePath());

		Source text = new StreamSource(new File(servletContext.getRealPath("/WEB-INF/xslts/dummy.xml")));

		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		
		transformer.transform(text, result);
		return writer;
	}
	
}