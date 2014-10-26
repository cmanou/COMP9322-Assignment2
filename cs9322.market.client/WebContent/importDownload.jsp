<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <title>COMP9322 Web Services</title>
  <meta name="description" content="website description" />
  <meta name="keywords" content="website keywords, website keywords" />
  <meta http-equiv="content-type" content="text/html; charset=windows-1252" />
  <link rel="stylesheet" type="text/css" href="style/style.css" title="style" />
</head>

<body>
  <div id="main">
    <div id="header">
      <div id="logo">
        <div id="logo_text">
          <!-- class="logo_colour", allows you to change the colour of the text -->
          <h1><a href="index.html">COMP9322 <span class="logo_colour">Web Services</span></a></h1>
          <h2>Assignment 1</h2>
        </div>
      </div>
      <div id="menubar">
        <ul id="menu">
          <!-- put class="selected" in the li tag for the selected page - to highlight which page you're on -->
          <li><a href="index.jsp">Home</a></li>
          <li class="selected"><a href="importDownload.jsp">Import/Download</a></li>
          <li><a href="summary.jsp">Summary MarketData</a></li>
          <li><a href="currencyConvert.jsp">Currency Convert</a></li>
        </ul>
      </div>
    </div>
    <div id="site_content">
      
      <c:if test= "${not empty importResponse}">
           <p style="color:orange;"><b>Scroll down to see the <span style="color:green;">'Import'</span> service response!</b></p>
      </c:if>
      <c:if test= "${not empty downloadResponse}">
           <p style="color:orange;"><b>Scroll down to see the <span style="color:green;">'Download'</span> service response!</b></p>
      </c:if>
      
      <div id="content">
        <!-- insert the page content here -->
        
        <h1>Import/Download Service</h1>

		<p><em>Consists of two operations:</em></p>

		<h4>Import Market Data operation</h4>
		
		<p>
			A Market Data file may contain a mixture of trades or quotes relating to several 
			financial instruments. An example of financial instrument is BHP shares traded 
			on the Australian Stock Exchange. The Import Market Data operation is designed 
			to filter a Market Data file, so a new Market Data file is created with only 
			those events from instruments that a user is interested in. For example, a user
			 can use the Import Market Data operation to extract the last 3 months of BHP trades
			  given an input Market Data file having trades and quotes relating to the top 200 
			  companies on the Australian Stock Exchange for the last 3 years.
		</p>
		<p>
			Reads a Market Data file that exists outside of the system, filters the content according 
			to the given parameters, and produces another Market Data file to store it inside the system. 
				The filter conditions are specified by the input parameters.
		</p>
		<div class="cf">
		<div style="width:50%;float:left;">
			<h5>Parameters</h5>
	        <table>
	        	<tr>
	           		<th>Name</th>
	           		<th>Type</th>
	           		<th>Description</th>
	           	</tr>
	           	<tr>
	           		<td>sec*</td>
	           		<td>String</td>
	           		<td>The security code of the financial instrument (i.e., stock) to be matched. <br />- Should be in upper case, alphabetics only and 3-4 characters in length.</td>
				</tr>
				<tr>
					<td>startDate*</td>
					<td>String</td>
					<td>Starting date/time to match (inclusive). 
						<br />- In Format "yyyy-MM-dd'T'HH:mm:ss.SSS".
						<br />- xsd:dateTime. 
					</td>
				</tr>
				<tr>
					<td>endDate*</td>
					<td>String</td>
					<td>Ending date/time to match (inclusive). 
						<br />- In Format "yyyy-MM-dd'T'HH:mm:ss.SSS".
						<br />- xsd:dateTime. 
					</td>
				</tr>
				<tr>
					<td>dataSourceURL*</td>
					<td>String</td>
					<td>This is a URL that points to the input Market Data file.<br />- Normal URL format.</td>
	        	</tr>
	        </table>
		</div>
		
			<div style="width:50%;float:left;">
		
		<h5>Return Value/s</h5>

        <table style="width:100%;">
        	<tr>
           		<th>Name</th>
           		<th>Type</th>
           		<th>Description</th>
           	</tr>
           	<tr>
           		<td>eventSetId</td>
           		<td>String</td>
           		<td>A handle that refers to a (output) filtered Market Data file. <br />- Should be in the pattern "^9322-[0-9]+$"</td>
			</tr>
			
        </table>
		<h5>Fault Responses</h5>

        <table style="width:100%;">
        	<tr>
           		<th>Fault Type</th>
           	</tr>
           	<tr>
           		<td>InvalidSECCode</td>
			</tr>
			<tr>
           		<td>InvalidURL</td>
			</tr>
			<tr>
           		<td>ProgramError</td>
			</tr>
        </table>
                </div>
                </div>
        
        <h5>Try-it!</h5>
        
        <form method="post" action="Controller">
        <table style="width:100%;">
        		<tr>
        			<th colspan="2" >Parameters</th>
        			<th id="responseFieldImport" >Response</th>
        		</tr>
        		<tr>
        			<td style="width:100px;">sec</td>
        			<td style="width:300px;padding-right:10px;"><input type="text" name="aSec" style="width:100%;" /></td>
        			<td rowspan="5">
        				${importResponse}
        			 </td>
        		</tr>
        		<tr>
        			<td>startDate</td>
        			<td style="width:300px;padding-right:10px;"><input type="text" name="aStartDate" style="width:100%;" /></td>
        		</tr>
        		<tr>
        			<td>endDate</td>
        			<td style="width:300px;padding-right:10px;"><input type="text" name="aEndDate" style="width:100%;" /></td>
        		</tr>
        		<tr>
        			<td>dataSourceURL</td>
        			<td style="width:300px;padding-right:10px;"><input type="text" name="aDataSourceURI" style="width:100%;" /></td>
        		</tr>
        		<tr>
        			<td></td>
        			<td>
        				<input type="hidden" name="action" value="requestImport" />
        				<input type="submit" value="Go!" style="width:100%;"/>
        			</td>
        		</tr>
        	</table>
        </form>
		<hr />
		<br />
		
		<!-- ////////////////////////////////////////////////////////////////////////////////////////////// -->
		
		<h4>Download File operation</h4>
		
		<p>
			The downloadFile operation makes Market Data files that are outputs of 'importMarketData' operation
			 accessible to users over the internet via URL download.
		</p>
				<div class="cf">
		<div style="width:50%;float:left;">
		<h5>Parameters</h5>
        <table style="width:100%;">
        	<tr>
           		<th>Name</th>
           		<th>Type</th>
           		<th>Description</th>
           	</tr>
           	<tr>
           		<td>eventSetId*</td>
           		<td>String</td>
           		<td>An opaque "handle" or reference that is the output of the 'importMarketData' operation.<br />
           			- Of the pattern "^9322-[0-9]+$".
           		</td>
			</tr>
        </table>
		</div>
		<div style="width:50%;float:left;">
		<h5>Return Value/s</h5>

        <table style="width:100%;">
        	<tr>
           		<th>Name</th>
           		<th>Type</th>
           		<th>Description</th>
           	</tr>
           	<tr>
           		<td>dataURL</td>
           		<td>String</td>
           		<td>This is a http:// URL that points to the Market Data file referenced by the 'eventSetId' input parameter. A user can download the Market Data file contents using this URL (e.g. by navigating to this URL using a Web browser).</td>
			</tr>
			
        </table>
        </div>
		<div style="width:100%;float:left;">
		<h5>Fault Responses</h5>

        <table style="width:100%;">
        	<tr>
           		<th>Fault Type</th>
           	</tr>
			<tr>
           		<td>InvalidEventSetId</td>
			</tr>
			<tr>
           		<td>ProgramError</td>
			</tr>
        </table>
        </div>
        </div>
        <h5>Try-it!</h5>
        
        <form method="post" action="Controller">
        <table style="width:100%;">
        		<tr>
        			<th colspan="2" >Parameters</th>
        			<th id="responseFieldDownload" >Response</th>
        			
        		</tr>
        		<tr>
        			<td style="width:100px;">eventSetId</td>
        			<td style="width:300px;padding-right:10px;"><input type="text" name="aEventSetId" style="width:100%;" /></td>
        			<td rowspan="5">
        				${downloadResponse}
        			 </td>
        		</tr>
        		<tr>
        			<td></td>
        			<td>
        				<input type="hidden" name="action" value="requestDownload" />
        				<input type="submit" value="Go!" style="width:100%;"/>
        			</td>
        		</tr>
        	</table>
        </form>
       	
	<hr>
        
      </div> <!--  Content Div End -->
      
    </div>
    <div id="footer">

    </div>
  </div>
</body>
</html>
