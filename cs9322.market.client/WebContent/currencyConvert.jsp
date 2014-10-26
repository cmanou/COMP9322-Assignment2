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
          <li><a href="importDownload.jsp">Import/Download</a></li>
          <li><a href="summary.jsp">Summary MarketData</a></li>
          <li class="selected"><a href="currencyConvert.jsp">Currency Convert</a></li>
        </ul>
      </div>
    </div>
    <div id="site_content">
      
      <c:if test= "${not empty currencyResponse}">
           <p style="color:orange;"><b>Scroll down to see the <span style="color:green;">'Currency Convert'</span> service response!</b></p>
      </c:if>
      
      <div id="content">
        <!-- insert the page content here -->
        
        <h1>Currency Convert Data Service</h1>

		<p><em>Consists of One operations</em></p>

		<h4>Currency Convert Market Data operation</h4>
		
		<p>
			A Market Data file may contain a mixture of trades or quotes relating to a 
			financial instrument. 
		</p>
		<p>
			Reads a Market Data file that exists inside of our system given by Event Set ID, 
			It converts the unprocessed price to the target currency requested if it existed.
			 This only applies if it is a valid target currency and there was a valid 
			 conversion rate as of 20th August 2014
		</p>
		<h5>Parameters</h5>
        <table>
        	<tr>
           		<th>Name</th>
           		<th>Type</th>
           	</tr>
           	<tr>
           		<td>eventSetId*</td>
           		<td>String</td>

			</tr>
			<tr>
           		<td>targetCurrency*</td>
           		<td>String</td>
			</tr>
        </table>
		
		<h5>Return Value/s</h5>

        <table>
        	<tr>
           		<th>Name</th>
           		<th>Type</th>
           	</tr>
           	<tr>
           		<td>eventSetId</td>
           		<td>String</td>
			</tr>
			
        </table>
        
		<h5>Fault Responses</h5>

        <table>
        	<tr>
           		<th>Fault Type</th>
           	</tr>
           	<tr>
           		<td>InvalidEventSetId</td>
			</tr>
			<tr>
           		<td>InvalidTargetCurrency</td>
			</tr>
			<tr>
           		<td>InvalidMarketData</td>
			</tr>
			<tr>
           		<td>ProgramError</td>
			</tr>  
			<tr>
           		<td>ProgramError</td>
			</tr>      
		</table>
        
        <h5>Try-it!</h5>
        
        <form method="post" action="Controller">
        	<table>
        		<tr>
        			<th colspan="2" >Parameters</th>
        			<th id="responseFieldImport" >Response</th>
        		</tr>
        		<tr>
        			<td>eventSetId</td>
        			<td><input type="text" name="aEventSetId" value="${aEventSetId}"/></td>
        			<td rowspan="5">
        				${currencyResponse}
        			 </td>
        		</tr>
        		<tr>
        			<td>targetCurrency</td>
        			<td><input type="text" name="aTargetCurrency" value="${aTargetCurrency}"/></td>
        		</tr>
        		<tr>
        			<td></td>
        			<td>
        				<input type="hidden" name="action" value="requestCurrency" />
        				<input type="submit" value="Go!"/>
        			</td>
        		</tr>
        	</table>
        </form>
        
       
       	<hr />
       	<br />

      </div> <!--  Content Div End -->
      
    </div>
    <div id="footer">

    </div>
  </div>
</body>
</html>
