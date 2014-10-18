<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Details</title>
</head>
<body>
	<h1>Order Details</h1>
		
	<p><a href="Controller?aAction=navigate">Home</a></p>	
	<table border='1' width="800px">
		<tr>
		    <td>Id: </td>
		    <td>${myOrder.id}</td>
		</tr>
		<tr>
		    <td>Drink: </td>
		    <td>${myOrder.drink}</td>
		</tr>
		<c:forEach items="${myOrder.additions}" var="addition"> 
			<tr>
			    <td>Addition: </td>
			    <td>${addition}</td>
			</tr>
		</c:forEach>
		<tr>
		    <td>Cost: </td>
		    <td>${myOrder.cost}</td>
		</tr>
		<tr>
		    <td>Status: </td>
		    <td>${myOrder.status}</td>
		</tr>
		<c:forEach items="${myOrder.links}" var="link"> 
			<tr>
			    <td>Link: </td>
			    <td>${link.rel} -- ${link.href}</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>