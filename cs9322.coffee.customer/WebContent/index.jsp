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
	
 
	<c:if test="${ !empty orderUpdated }">
		<p style="color:green;">Order Updated!<p>
	</c:if>
	<c:if test="${ !empty orderPaid }">
		<p style="color:green;">Order Paid!<p>
	</c:if>
	
	<p><a href="Controller?aAction=navigate&aPage=createOrder">Create New Order</a></p>	
	<table border='1' width="800px">
		<c:forEach items="${myOrdersList}" var="order"> 
		  <tr>
		    <td><a href="Controller?aAction=getOrder&aId=${order.id}">Coffee Order ${order.id}</a></td>
		    <td><a href="Controller?aAction=cancelOrder&aId=${order.id}">Cancel</a></td>
		    <td><a href="Controller?aAction=updateOrder&aId=${order.id}">Update</a></td>
		    <td><a href="Controller?aAction=payOrder&aId=${order.id}">Pay</a></td>
		    <td><a href="Controller?aAction=getOptions&aId=${order.id}">Options</a></td>
		    <c:choose>
			  <c:when test="${(order.status == 'PLACED') || (order.status == 'CANCELLED')}">
			    <td></td>
			  </c:when>
			  <c:otherwise>
			    <td><a href="Controller?aAction=getPayment&aId=${order.id}">Payment ${order.id}</a></td>
			  </c:otherwise>
			</c:choose>
			<c:choose>
			  <c:when test="${ !empty options && optionsId == order.id }">
			    <td><span style="color:orange;" >${options}</span></td>
			  </c:when>
			  <c:when test="${ !empty result && resultId == order.id }">
			    <td><span style="color:orange;" >${result}</span></td>
			  </c:when>
			  <c:when test="${ !empty updateResult && updateResultId == order.id }">
			    <td><span style="color:orange;" >${updateResult}</span></td>
			  </c:when>
			  <c:when test="${ !empty payResult && payResultId == order.id }">
			    <td><span style="color:orange;" >${payResult}</span></td>
			  </c:when>
			  <c:otherwise>
			    <td><span style="color:orange;" > - </span></td>
			  </c:otherwise>
			</c:choose>
		  </tr>
		</c:forEach>
	</table>

</body>
</html>