<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home - Overview</title>
</head>
<body>
	<h1>Overview</h1>
	
	<!--  
	<c:if test="${ !empty orderCreated }">
		<p style="color:green;">Order Created!<p>
	</c:if>
	-->
	
	<p><a href="Controller?aAction=navigate&aPage=createOrder">Create New Order</a></p>	
	<table border='1' width="600px">
		<c:forEach items="${myOrdersList}" var="order"> 
		  <tr>
		    <td><a href="">Coffee Order ${order.id}</a></td>
		    <td><a href="">Cancel</a></td>
		    <td><a href="">Update</a></td>
		    <td><a href="">Pay</a></td>
		    <td><a href="">Options</a></td>
		    <c:choose>
			  <c:when test="${(order.status == 'PLACED') || (order.status == 'CANCELLED')}">
			    <td></td>
			  </c:when>
			  <c:otherwise>
			    <td><a href="">Payment ${order.id}</a></td>
			  </c:otherwise>
			</c:choose>
		  </tr>
		</c:forEach>
	</table>

</body>
</html>