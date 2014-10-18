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
	<h1>Payment Details</h1>
		
	<p><a href="Controller?aAction=navigate">Home</a></p>	
	<table border='1' width="800px">
		<tr>
		    <td>Id: </td>
		    <td>${myPayment.id}</td>
		</tr>
		<tr>
		    <td>Type: </td>
		    <td>${myPayment.type}</td>
		</tr>
		<tr>
		    <td>Amount: </td>
		    <td>${myPayment.amount}</td>
		</tr>
		<tr>
		    <td>Card Name: </td>
		    <td>${myPayment.cardName}</td>
		</tr>
		<tr>
		    <td>Card Number: </td>
		    <td>${myPayment.cardNumber}</td>
		</tr>
		<tr>
		    <td>Card CVC: </td>
		    <td>${myPayment.cardCVC}</td>
		</tr>
		<c:forEach items="${myPayment.links}" var="link"> 
			<tr>
			    <td>Link: </td>
			    <td>${link.rel} -- ${link.href}</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>